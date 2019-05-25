package swe.kne.chip08.chip8;

import java.io.File;

public class Memory {
    private int[] memory = new int[4096];

    public Memory() {
        resetMemory();
    }

    public void resetMemory() {
        memory = new int[4096];
    }
    public void loadRom(File rom, int programCounter) {
        byte[] b = LoadGame.loadRom(rom);
        int i = 0;
        for (byte bytet : b) {
            memory[programCounter + i] = (int) bytet;
            i++;
        }
    }
    public long getInstruction(int programCounter) {
        // TODO: clean this mess up.
        short ett = (short) (memory[programCounter] & 0xFF);
        short tva = (short) ((memory[programCounter + 1]) & 0xFF);
        int hela = (ett << 8);
        hela = hela | tva;
        return hela;
    }
}
