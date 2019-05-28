package swe.kne.chip08.chip8;

/**
 * this isn't exactly written in an object-oriented way, but it's a simpler way. I hope.
 */
public class Decoder {

    public static void decodeAndExecuteInstruction(long currentInstruction, Cpu cpu, Memory memory) throws IllegalArgumentException {
        String opCode = Integer.toHexString(getOpcode(currentInstruction));
        switch (opCode) {
            case "6": {
                /**
                 * 6XNN, SET vX to NN
                 */
                cpu.setGpRegisters(((byte) ((currentInstruction & 0x0F00) >> 8)), ((byte) (getUnsignedByte((currentInstruction & 0x00FF)))));
                break;
            }
            case "7": {
                break;
            }
            case "a": {
                /**
                 * ANNN, SET Index Register to NNN
                 */
                cpu.setIndexRegister((short) (getUnsignedSlab( (currentInstruction & 0x0FFF))));
                break;
            }
            case "d": {
                /**
                 * DXYN, DRAW sprite. Read n bytes from memory, starting from the memory location found in the index
                 * register. Draw this at vX and vY (width & height--pos). The drawing is done via XORing, and if any pixel is
                 * erased: set vF to collision (i.e. true). If sprite is positioned outside screen (so, if it's
                 * negative or goes beyond the 64 * 32), it wraps around.
                 */
                short[] toDraw = new short[(getUnsignedNibble(currentInstruction & 0x0F))];
                for (int i = 0; i < toDraw.length; i++) {
                    toDraw[i] = (short) memory.getUnsignedByte(cpu.getIndexRegister() + i);
                }

                int x = cpu.getFromGpRegisters(getUnsignedNibble(currentInstruction & 0x0F00) >>> 8);
                int y = cpu.getFromGpRegisters(getUnsignedNibble(currentInstruction & 0x00F0) >>> 4);

                cpu.debugGraphics(toDraw, x, y);
                break;
            }
            case "f": {
                break;
            }
            default: {
                //TODO: also print what the currentInstruction looks like as hex, binary etc.
                System.out.println("something went wrong: ");
                System.out.println("opcode: " + opCode);
                System.out.println("currentInstr: " + currentInstruction);
                throw new IllegalArgumentException("WRONG OPCODE");
            }
        }
    }

    /**
     *
     * @param instruction
     * @return if negative: return last byte only. if positive: shift it 12 steps to the right and remove the zero
     * that makes a signed number negative.
     */
    public static int getOpcode(long instruction) {
        int i = (int) ((instruction));
        if (i < 0) {
            return ((i & 0x0000000F));
        }
        else {
            return (int) (instruction >>> 12);
        }
    }

    // 4 bitar
    public static int getUnsignedNibble(long instruction) {
        int i = (int) (instruction);
        if (i < 0) {
            return (int) (i & 0x0000000F);
        }
        else {
            return i;
        }
    }

    // 8 bitar
    public static int getUnsignedByte(long instruction) {
        int i = (int) (instruction);
        if (i < 0) {
            return (int) (i & 0x000000FF);
        }
        else {
            return i;
        }
    }

    // 12 bitar
    public static int getUnsignedSlab(long i) {
        if (i < 0) {
            return (int) i & 0x00000FFF;
        }
        else {
            return (int) i;
        }
    }
}
