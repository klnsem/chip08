package swe.kne.chip08.chip8;

import java.io.File;

public class Cpu {
    private byte[] gpRegisters = new byte[16];
    private short indexRegister;
    private int programCounter;
    private byte delayTimer;
    private byte soundTimer;
    private short[] stack = new short[16];
    private short stackPointer;

    private long currentInstruction;
    private Character[] pressedKey = new Character[16];

    private Graphics graphics = new Graphics();
    private Memory memory = new Memory();

    private boolean running;

    public Cpu(File rom) {
        resetMachine();
        memory.loadRom(rom, programCounter);
        while (running) {
            tick();
            debugLoggingOutput();
        }
        // TODO: implement the complete cycle and the main loop.
        // TODO: set correct data and whatnot.
        //debugCpu();
    }

    public void tick() {

        System.out.println("tick");
        fetch();
        programCounter = programCounter + 2;
        decodeAndExecute();
    }

    private void fetch() {
        try {
            currentInstruction = (long) (memory.getInstruction(programCounter));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void decodeAndExecute() {
        try {
            Decoder.decodeAndExecuteInstruction(currentInstruction, this);
        }
        catch (Exception e) {
            e.printStackTrace();
            running = false;
        }
    }

    public void resetMachine() {
        programCounter = 0x200;
        currentInstruction = 0;
        indexRegister = 0;
        stackPointer = 0;
        stack = new short[16];
        gpRegisters = new byte[16];
        memory.resetMemory();
        graphics.resetGraphics();
        running = true;
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
    public void setDelayTimer(byte delayTimer) {
        this.delayTimer = delayTimer;
    }
    public void setSoundTimer(byte soundTimer) {
        this.soundTimer = soundTimer;
    }
    public void setStack(short[] stack) {
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

    /**
     * this should be called every fetch-decode-execute-cycle - preferably at the end of it - while
     * making and debugging the emulator.
     */
    public void debugLoggingOutput() {
        System.out.print("CSO: "); // CSO as in CPU STATUS OUTPUT
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
        System.out.print(" CI: " + currentInstruction + "\n");
    }
}
