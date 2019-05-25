package swe.kne.chip08.chip8;

import java.io.File;

public class Cpu {
    private byte[] gpRegisters = new byte[16];
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

    public Cpu(File rom) {
        resetMachine();
        memory.loadRom(rom, programCounter);

        // TODO: implement the complete cycle and the main loop.
        // TODO: set correct data and whatnot.
        //debugCpu();
    }

    public void tick() {
        //System.out.println(currentInstruction);
        fetch();
        programCounter+=2;
        decodeAndExecute();
    }

    private void fetch() {
        try {
            currentInstruction = memory.getOpcode(programCounter);
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
        gpRegisters = new byte[16];
        memory.resetMemory();
        graphics.resetGraphics();
        // TODO: set these two to something correct
        delayTimer = 60;
        soundTimer = 60;
    }
    public void setFontset() {
        ;
        // starts at 0x200!
        // or maybe at 0x000??
    }
    public void debugCpu() {
        debugLoggingOutput();
        tick();
        debugLoggingOutput();
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

    public void debugLoggingOutput() {
        System.out.print("\nCSO: "); // SO as in CPU STATUS OUTPUT
        int i = 0;
        for (byte b : gpRegisters) {
            System.out.print(Integer.toHexString(i) + "=" + Integer.toHexString(b) + " ");
            i++;
        }
        System.out.print(" IR: " + Integer.toHexString(indexRegister));
        System.out.print(" PC: " + Integer.toHexString(programCounter));
        System.out.print(" DT: " + Integer.toHexString(delayTimer));
        System.out.print(" ST: " + Integer.toHexString(soundTimer));
        System.out.print(" SP: " + Integer.toHexString(stackPointer));
        System.out.print(" CI: " + Integer.toHexString(currentInstruction));

    }
}
