package swe.kne.chip08.chip8;

import java.io.File;

public class Memory {
    private byte[] memory = new byte[4096];

    public Memory() {
        resetMemory();
        System.out.println("Memory started");
    }

    public void resetMemory() {
        memory = new byte[4096];
    }
    public void loadRom(File rom, Short programCounter) {
        byte[] b = LoadGame.loadRom(new File("roms/pong.rom"));
        int i = 0;
        for (byte bytet : b) {
            memory[programCounter + i] = bytet;
            i++;
        }
        System.out.println("loaded");
    }
    public Short getOpcode(Short programCounter) {
        byte first = (byte) Byte.toUnsignedInt(memory[programCounter]);
        byte second = (byte) Byte.toUnsignedInt(memory[programCounter + 1]);
        short output = (short) 0x00;
        output = (short) (first | output);
        output = (short) (output << 8);
        output = (short) (second | output);
        //System.out.println(Integer.toHexString(Short.toUnsignedInt(output)));
        return output;
    }
}
