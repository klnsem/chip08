package swe.kne.chip08.chip8;

public class Cpu {
    private Byte[] gpRegisters = new Byte[16];
    private Short indexRegister;
    private int programCounter;
    private Character delayTimer;

    private Character soundTimer;
    private Short[] stack = new Short[16];
    private Short stackPointer;

    private Short currentInstruction;
    private Character[] pressedKey = new Character[16];

    private Graphics graphics = new Graphics();
    private Memory memory = new Memory();

    public Cpu() {
        resetMachine();
        // TODO: set correct data and whatnot.
        //debugCpu();
    }

    public void tick() {
        fetch();
        programCounter+=2;
        decodeAndExecute();
    }

    private void fetch() {
        try {
            Decoder.decodeAndExecuteInstruction(currentInstruction, this);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void decodeAndExecute() {
        Decoder.decodeAndExecuteInstruction(currentInstruction, this);
    }

    public void resetMachine() {
        programCounter = 0x200;
        currentInstruction = 0;
        indexRegister = 0;
        stackPointer = 0;
        stack = new Short[16];
        gpRegisters = new Byte[16];
        memory.resetMemory();
        graphics.resetGraphics();
    }
    public void setFontset() {
        ;
        // starts at 0x200!
        // or maybe at 0x000??
    }
    public void debugCpu() {
        /**
         * this somewhat surprisingly gives you the correct opcode
         */
        memory.loadRom(null, programCounter);
        Short hello = memory.getOpcode(programCounter);
        tick();
        //System.out.println(Integer.toHexString(hello));
        //System.out.println(Integer.toHexString(hello >> 12));
    }
    public void setGpRegisters(byte register, byte content) {
        this.gpRegisters[register] = content;
    }
    public void setIndexRegister(Short indexRegister) {
        this.indexRegister = indexRegister;
    }
    public void setDelayTimer(Character delayTimer) {
        this.delayTimer = delayTimer;
    }
    public void setSoundTimer(Character soundTimer) {
        this.soundTimer = soundTimer;
    }
    public void setStack(Short[] stack) {
        this.stack = stack;
    }
    public void setStackPointer(Short stackPointer) {
        this.stackPointer = stackPointer;
    }
    public void setPressedKey(Character[] pressedKey) {
        this.pressedKey = pressedKey;
    }
    public void setGraphics(Graphics graphics) {
        this.graphics = graphics;
    }
    public void setMemory(Memory memory) {
        this.memory = memory;
    }
}
