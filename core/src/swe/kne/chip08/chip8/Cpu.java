package swe.kne.chip08.chip8;

import swe.kne.chip08.chip08mainwindow;

import java.io.File;
import java.util.ArrayList;

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

    private Graphics graphics = new Graphics(resMulti);
    private Memory memory = new Memory();

    private boolean running;
    public static final int resMulti = chip08mainwindow.resolutionMultiplicity;


    public boolean checkIfRenderScreen = false;
    public boolean renderWholeScreen = false;

    public char[] debugShape;
    public int debugX;
    public int debugY;
    public boolean debugDraw = false;
    public ArrayList<DebugSprite> dsal = new ArrayList<>();

    public Cpu(File rom) {
        resetMachine();
        memory.loadRom(rom, programCounter);

        // TODO: implement the complete cycle and the main loop.
        // TODO: set correct data and whatnot.
        //debugCpu();
    }

    public void tick() {

        fetch();
        programCounter = programCounter + 2;
        decodeAndExecute();

        if (graphics.isRenderScreen() && (gpRegisters[15] != 0X00 )) {
            renderWholeScreen = true;
            checkIfRenderScreen = true;
            // TODO: kolla upp vad som händer med vF efter läsning
        }
        if (graphics.isRenderScreen() && (gpRegisters[15] == 0x00)) {
            // Rendera bara den nya spriten.
            checkIfRenderScreen = true;
            renderWholeScreen = false;
        }
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
            Decoder.decodeAndExecuteInstruction(currentInstruction, this, memory);
        }
        catch (Exception e) {
            e.printStackTrace();
            running = false;
        }
    }
    public boolean getRenderWholeScreen() {
        return this.renderWholeScreen;
    }
    public void setRenderWholeScreen(boolean b) {
        this.renderWholeScreen = b;
    }
    public boolean getRenderScreen() {
        return this.checkIfRenderScreen;
    }
    public void setRenderScreen(boolean b) {
        this.renderWholeScreen = b;
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
    public boolean getRunning() {
        return this.running;
    }
    public void setFontset() {
        ;
        // starts at 0x200!
        // or maybe at 0x000??
    }
    public void debugGraphics(short[] pixel, int x, int y) {
        debugShape = graphics.debugGetShape(y, pixel);
        debugX = x;
        debugY = y;
        debugDraw = true;
        DebugSprite ds = new DebugSprite(x, y, debugShape);
        dsal.add(ds);
    }
    public void debugCpu() {
        debugLoggingOutput();
        tick();
        debugLoggingOutput();
    }
    public void setGpRegisters(byte register, byte content) {
        this.gpRegisters[register] = content;
    }
    public byte getFromGpRegisters(int pos) {
        return this.gpRegisters[pos];
    }
    public void setIndexRegister(Short indexRegister) {
        this.indexRegister = indexRegister;
    }
    public short getIndexRegister() {
        return this.indexRegister;
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
    public void createSprite(short[] pixel, int x, int y) {
        // TODO: test with xor, returns a boolean. if true, collision exists and vF = 1;

        /**
         * Create a sprite-object, after checking for duplicate-pixels. If that's the case,
         */

        /**
         */
        if (getFlag()) {
            // specialare
            ;
        }
        else {
            ;
        }
        resetFlag();
    }
    public void setMemory(Memory memory) {
        this.memory = memory;
    }
    public void resetFlag() {
        this.gpRegisters[15] = 0x00;
    }
    public boolean getFlag() {
        if ((this.gpRegisters[15] & 0xFF) != 0) {
            return true;
        }
        else {
            return false;
        }
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
