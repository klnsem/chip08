package swe.kne.chip08.chip8;

import swe.kne.chip08.chip8.types.InstructionTypes;

public class Decoder {
    /**
     * TODO: implement carry-flags and whatnot.
     */

    public static void decodeAndExecuteInstruction(short currentInstruction, Cpu cpu) throws IllegalArgumentException {
        String opCode = getOpcode(currentInstruction);
        switch (opCode) {
            case ("6"):
                cpu.setRegister((byte) ((currentInstruction & 0x0F00) >> 8));
                setConstant((byte) (currentInstruction & 0x00FF));
                break;
            default:
                //TODO: also print what the currentInstruction looks like as hex, binary etc.
                throw new IllegalArgumentException("WRONG OPCODE");
        }
    }
/*
    public static Instruction getDecodedInstruction(Short currentInstruction, Cpu cpu) throws Exception {
        Instruction instr = new Instruction(currentInstruction, cpu);
        String opCode = getOpcode(currentInstruction);
        switch (opCode) {
            case ("6"):
                instr.setInstructionType(InstructionTypes.SET);
                instr.setCarryFlag(false);
                instr.setRegister((byte) ((currentInstruction & 0x0F00) >> 8));
                instr.setConstant((byte) (currentInstruction & 0x00FF));
                break;
            default:
                //TODO: also print what the currentInstruction looks like as hex, binary etc.
                throw new Exception("WRONG OPCODE");
        }
        return instr;
    }*/

    public static String getOpcode(Short instruction) {
        int intInstr = (int) Short.toUnsignedInt(instruction);
        intInstr = intInstr >> 12;
        return String.valueOf(intInstr);
    }

    public static byte getRegister(short currentInstruction) {
        currentInstruction = (short) (currentInstruction & 0x0F00);
        return ((byte) (currentInstruction >> 8));
    }
}
