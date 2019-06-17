package swe.kne.chip08.chip8;

/**
 * this isn't exactly written in an object-oriented way, but it's a simpler way. I hope.
 */
public class Decoder {

    public static void decodeAndExecuteInstruction(long curInstr, Messages messages) throws IllegalArgumentException {
        String opCode = Integer.toHexString(BinConv.getOpcode(curInstr));
        switch (opCode) {
            /**
             * Every opcode starting with a 0 - of course - breaks stuff, thereby the internal switch.
             * This is ugly as bananas.
             */
            case "0": {
                switch (Integer.toHexString((int)curInstr)) {
                    /**
                     * 00EE, return from subroutine via pointer found in the stack.
                     */
                    case "ee": {
                        messages.cpuSetProgramCounter(messages.cpuPopStack());
                        break;
                    }
                    default: {
                        System.out.println("DEFAULT");
                        break;
                    }
                }
                break;
            }
            /**
             * 2NNN, pushes the next instruction on the program counter to the stack, sets the program counter
             * to NNN, and continues its merry way in a subroutine. When the subroutine is finished with a 0x00EE
             * instruction, it pops the stack and sets the program counter to that value.
             */
            case "2": {
                messages.cpuPushStack((short) messages.cpuGetProgramCounter());
                messages.cpuSetProgramCounter(BinConv.getUnsignedSlab(curInstr & 0x0FFF));
                break;
            }
            /**
             * 6XNN, SET vX to NN
             */
            case "6": {
                messages.cpuSetGpRegisters((byte) BinConv.getUnsignedNibble((int) curInstr & 0x0F00),
                        ((byte) BinConv.getUnsignedByte(curInstr & 0x00FF)));
                break;
            }
            /**
             * ANNN, SET Index Register to NNN
             */
            case "a": {
                messages.cpuSetIndexRegister((short) BinConv.getUnsignedSlab( curInstr & 0x0FFF));
                break;
            }
            /**
             * 7RNN, adds the value NN to register R.
             */
            case "7": {
                short value = (short) (curInstr & 0x00FF);
                value+= messages.cpuGetFromGpRegisters(BinConv.getUnsignedNibble((int) curInstr & 0x0F00));
                byte reg = (byte) BinConv.getUnsignedNibble((int) curInstr & 0x0F00);
                messages.cpuSetGpRegisters(reg, value);
                break;
            }
            /**
             * DXYN, DRAW sprite. Read n bytes from memory, starting from the memory location found in the index
             * register. Draw this at vX and vY (width & height--pos). The drawing is done via XORing, and if any pixel is
             * erased: set vF to collision (i.e. true). If sprite is positioned outside screen (so, if it's
             * negative or goes beyond the 64 * 32), it wraps around.
             */
            case "d": {
                boolean[] toDraw = new boolean[BinConv.getUnsignedNibble((int)curInstr & 0x0F) * 8];
                for (int i = 0; i < toDraw.length / 8 - 1; i++) {
                    short s = (short) (BinConv.getUnsignedByte(messages
                            .memoryGetByte(messages.cpuGetIndexRegister() + i / 8)));
                    String str = Integer.toBinaryString(Short.toUnsignedInt(s));
                    str = BinConv.get8bitString(str);
                    for (int k = 0; k < str.length() - 1; k++ ) {
                        if (str.charAt(k) == '1') {
                            toDraw[i * 8 + k] = true;
                        }
                        else if (str.charAt(k) == '0') {
                            toDraw[i * 8 + k] = false;
                        }
                    }
                }
                int x = messages.cpuGetFromGpRegisters(BinConv.getUnsignedNibble((int) curInstr & 0x00000F00));
                int y = messages.cpuGetFromGpRegisters(BinConv.getUnsignedNibble((int) curInstr & 0x000000F0));
                messages.graphicsSendSprite(x + y * 64, toDraw);

                break;
            }
            /**
             * FX##, precise instruction depends on ##
             */
            case "f": {
                int endChars = BinConv.getUnsignedByte(curInstr & 0x00FF);
                String choice = Integer.toHexString(endChars);
                switch (choice) {

                    /**
                     * FX15, set the delay timer to the value of gpRegister X.
                     */
                    case "15": {
                        messages.cpuSetDelayTimer((byte)(messages.cpuGetFromGpRegisters(
                                BinConv.getUnsignedNibble((int)curInstr & 0x0F00))));
                        break;
                    }
                    /**
                     * FX29, set the Index register to the memory address of the hexadecimal digit stored in gpReg X.
                     * TODO: check if this works when getting a digit that's not 0!
                     */
                    case "29": {
                        int register = BinConv.getUnsignedNibble((int)curInstr & 0x0F00);
                        short digit = (short) BinConv.getUnsignedNibble(messages.cpuGetFromGpRegisters(register));
                        digit = (short) (digit & 0x0F); //MIGHT be unneccessary.
                        messages.cpuSetIndexRegister((short) (digit * 5));
                    }
                    /**
                     * Store the binary-coded decimal from vX in the indexregister[0 to 2].
                     * I[0] stores 100's, I[1] stores 10's, i[2] stores 1's.
                     *
                     * This seems to be a commonly hard aspect of chip-8 emulation, so i'm
                     * stealing the solution from (it's at the bottom):
                     * http://www.multigesture.net/wp-content/uploads/mirror/goldroad/chip8.shtml
                     */
                    case "33": {
                        short index = (short) BinConv.getUnsignedByte(messages.cpuGetIndexRegister());
                        short gprv = messages.cpuGetFromGpRegisters((int)curInstr & 0x0F00 >> 8);
                        int v1 = gprv / 100;
                        int v2 = (gprv / 10) % 10;
                        int v3 = ((gprv % 100) % 10);
                        messages.memorySetByte(index, v1);
                        messages.memorySetByte(index + 1, v2);
                        messages.memorySetByte(index + 2, v3);
                        break;
                    }
                    /**
                     * FX65, fill registers 0 to X with the values stored in the adresses pointed to
                     * in the IR, from IR to IR + X. IR is then set to IR + X + 1.
                     */
                    case "65": {
                        for (int i = 0; i < BinConv.getUnsignedNibble(0x0F00); i++) {
                            messages.cpuSetGpRegisters((byte) i, messages.memoryGetByte(messages.cpuGetIndexRegister() + i));
                        }
                        messages.cpuSetIndexRegister((short) (messages.cpuGetIndexRegister() + 1));
                        break;
                    }

                    default: {
                        System.err.println("ERROR: unknown ending for 0xFV## instruction");
                        System.out.println("case f, and: " + choice);
                        break;
                    }
                }
                break;
            }

            default: {
                System.out.println("something went wrong: ");
                System.out.println("opcode: " + opCode);
                System.out.println("currentInstr: " + Integer.toHexString((int)curInstr));
                throw new IllegalArgumentException("WRONG OPCODE");
            }
        }
    }

}
