package swe.kne.chip08.chip8;

/**
 * this isn't exactly written in an object-oriented way, but it's a simpler way. I hope.
 */
public class Decoder {

    public static void decodeAndExecuteInstruction(long currentInstruction, Cpu cpu) throws IllegalArgumentException {
        String opCode = Integer.toHexString(getOpcode(currentInstruction));
        switch (opCode) {
            case "6": {
                /**
                 * 6XNN, SET vX to NN
                 */
                //System.out.println("getunsignedbyte: " + (byte) (getUnsignedByte(currentInstruction)));
                //cpu.setGpRegisters(((byte) ((currentInstruction & 0x0F00) >> 8)), ((byte) (currentInstruction & 0x00FF)));
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

    public static int getUnsignedNibble(long instruction) {
        int i = (int) (instruction);
        if (i < 0) {
            return (int) (i & 0x0000000F);
        }
        else {
            return (int) (i >>> 12);
        }
    }
    public static int getUnsignedByte(long instruction) {
        int i = (int) (instruction);
        if (i < 0) {
            return (int) (i & 0x000000FF);
        }
        else {
            return i;
        }
    }
    public static int getUnsignedSlab(long i) {
        System.out.println("GUS = " + i);
        if (i < 0) {
            return (int) i & 0x00000FFF;
        }
        else {
            return (int) i;
        }
    }
}
