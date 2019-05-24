package swe.kne.chip08.chip8;

public class Cpu {
    private Short currentOpcode;
    private Character[] gpRegisters = new Character[16];
    private Short indexRegister;
    private Short programCounter;
    private Integer intar;
    private Character delayTimer;
    private Character soundTimer;
    private Short[] stack = new Short[16];
    private Short stackPointer;
    private Character[] pressedKey = new Character[16];

    private Graphics graphics = new Graphics();
    private Memory memory = new Memory();

    public Cpu() {
        resetMachine();
        System.out.println("Cpu started");
        // TODO: set correct data and whatnot.
        debugCpu();
    }

    private Short fetch() {
        return null;
    }

    public void resetMachine() {
        programCounter = 0x200;
        currentOpcode = 0;
        indexRegister = 0;
        stackPointer = 0;
        stack = new Short[16];
        gpRegisters = new Character[16];
        memory.resetMemory();
        graphics.resetGraphics();
    }
    public void setFontset() {
        ;
        // starts 0x200!
    }
    public void debugCpu() {
        /**
         * this somewhat surprisingly gives you the correct opcode!
         */
        memory.loadRom(null, programCounter);
        Short hello = memory.getOpcode(programCounter);
        System.out.println(Integer.toBinaryString(hello));
    }
}
