package swe.kne.chip08.chip8;

import java.io.File;

public class Memory {
    private byte[] memory = new byte[4096];

    public Memory() {
        resetMemory();
    }

    public void resetMemory() {
        memory = new byte[4096];
    }
    public void loadRom(File rom, int programCounter) {
        byte[] b = LoadGame.loadRom(rom);
        int i = 0;
        for (byte bytet : b) {
            memory[programCounter + i] = bytet;
            i++;
        }
    }
    public Short getOpcode(int programCounter) {
        // TODO: clean this mess up.
        byte first = (byte) Byte.toUnsignedInt(memory[programCounter]);
        byte second = (byte) Byte.toUnsignedInt(memory[programCounter + 1]);
        short output = (short) 0x00;
        output = (short) (first | output);
        output = (short) (output << 8);
        output = (short) (second | output);
        return output;
    }
}
