package swe.kne.chip08.chip8;

import java.io.File;

public class Memory {
    private int[] memory = new int[4096];

    private Messages messages;

    public Memory(Messages messages) {
        this.messages = messages;
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
        short firstHalf = (short) (memory[programCounter] & 0xFF);
        short secondHalf = (short) (memory[programCounter + 1] & 0xFF);
        int whole = firstHalf << 8;
        whole = whole | secondHalf;
        return whole;
    }

    public int getByte(int location) {
        int i = memory[location];
        if (i < 0) {
            return (i & 0x000000FF);
        }
        else {
            return i;
        }
    }
}
