package swe.kne.chip08.chip8;

import swe.kne.chip08.chip8.types.InstructionTypes;

public class Instruction {
    public Short opCode = null;
    public InstructionTypes instructionType = null;
    public Short address = null;
    public Byte constant = null;
    public Byte register = null;
    public Integer programCounter = null;
    public Short register16bit = null;
    public Byte variable = null;
    public Boolean carryFlag = null;
    private Cpu cpu;

    public Instruction(Short opCode, Cpu cpu) {
        this.opCode = opCode;
        this.cpu = cpu;
    }
    public void executeInstruction() throws Exception {
        if (instructionType == null) {
            throw new NullPointerException ("instructionType is null, that's bad");
        }
        switch (instructionType) {
            case SET:

        }
    }

    public void setInstructionType(InstructionTypes instructionType) {
        this.instructionType = instructionType;
    }
    public void setAddress(short address) {
        this.address = address;
    }
    public void setConstant(byte constant) {
        this.constant = constant;
    }
    public void setRegister(byte register) {
        this.register = register;
    }
    public void setProgramCounter(int programCounter) {
        this.programCounter = programCounter;
    }
    public void setRegister16bit(short register16bit) {
        this.register16bit = register16bit;
    }
    public void setVariable(byte variable) {
        this.variable = variable;
    }
    public void setCarryFlag(boolean carryFlag) {
        this.carryFlag = carryFlag;
    }
}
