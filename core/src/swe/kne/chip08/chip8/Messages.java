package swe.kne.chip08.chip8;

import java.io.File;

/**
 * Used to send messages between different objects.
 */
public class Messages {

    private Cpu cpu;
    private Graphics graphics;
    private Memory memory;

    public Messages() {
        ;
    }

    /**
    public boolean[] graphicsGetScreen() {
        return graphics.getAllPixels();
    }
     **/

    public void addComponents(Cpu cpu, Graphics graphics, Memory memory) {
        this.cpu = cpu;
        this.graphics = graphics;
        this.memory = memory;
    }

    /**
     * MEMORY
     */
    public void memoryReset() {
        memory.resetMemory();
    }
    public long memoryGetInstruction(int pc) {
        return memory.getInstruction(pc);
    }
    public void memoryLoadRom(File rom, int programCounter) {
        memory.loadRom(rom, programCounter);
    }
    public short memoryGetByte(int location) {
        return (short) memory.getByte(location);
    }

    /**
     * CPU
     */
    public void cpuSetGpRegisters(byte register, byte content) {
        cpu.setGpRegisters(register, content);
    }
    public short cpuGetFromGpRegisters(int pos) {
        return cpu.getFromGpRegisters(pos);
    }
    public void cpuSetIndexRegister(short ir) {
        cpu.setIndexRegister(ir);
    }
    public short cpuGetIndexRegister() {
        return cpu.getIndexRegister();
    }
    public void cpuSetCarryFlag(byte b) {
        cpu.setCarryFlag(b);
    }
    public void cpuResetCarryFlag() {
        cpu.resetCarryFlag();
    }

    /**
     * GRAPHICS
     */
    public void graphicsReset() {
        graphics.resetGraphics();
    }
    public void graphicsSendSprite(int pos, boolean[] b) {
        graphics.xorSprite(pos, b);
    }
    public boolean[] graphicsGetAllPixels() {
        return graphics.getAllPixels();
    }
}
