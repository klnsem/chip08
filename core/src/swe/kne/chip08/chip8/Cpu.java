package swe.kne.chip08.chip8;


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

    private boolean running;
    private Messages messages;

    public Cpu(Messages messages) {
        this.messages = messages;
        // TODO: set correct data and whatnot.
    }

    public void tick() {

        fetch();
        programCounter = programCounter + 2;
        decodeAndExecute();
            // TODO: kolla upp vad som händer med vF efter läsning
    }

    private void fetch() {
        try {
            currentInstruction = (long) messages.memoryGetInstruction(programCounter);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void decodeAndExecute() {
        try {
            Decoder.decodeAndExecuteInstruction(currentInstruction, this, messages);
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
        messages.memoryReset();
        messages.graphicsReset();
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
    public void debugCpu() {
        ;
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

    public void resetCarryFlag() {
        this.gpRegisters[15] = 0x00;
    }
    public boolean getCarryFlag() {
        return ((this.gpRegisters[15] & 0xFF) != 0);
    }
    public void setCarryFlag(byte b) {
        this.gpRegisters[15] = (byte) BinConv.getUnsignedNibble(b);
    }
    public int getProgramCounter() {
        return programCounter;
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
