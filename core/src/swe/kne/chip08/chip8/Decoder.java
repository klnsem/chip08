package swe.kne.chip08.chip8;

/**
 * this isn't exactly written in an object-oriented way, but it's a simpler way. I hope.
 */
public class Decoder {

    public static void decodeAndExecuteInstruction(long curInstr, Cpu cpa, Messages messages) throws IllegalArgumentException {
        String opCode = Integer.toHexString(BinConv.getOpcode(curInstr));
        switch (opCode) {
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
            default: {
                System.out.println("something went wrong: ");
                System.out.println("opcode: " + opCode);
                System.out.println("currentInstr: " + Integer.toHexString((int)curInstr));
                throw new IllegalArgumentException("WRONG OPCODE");
            }
        }
    }

}
