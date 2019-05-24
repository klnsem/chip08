package swe.kne.chip08.chip8;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class LoadGame {
    public static byte[] loadRom(File rom) {
        byte[] content = null;
        try {
            content = Files.readAllBytes(Paths.get(rom.getPath()));
        }
        catch (Exception e) {
            System.err.println("Exception in LoadGame > loadRom");
            e.printStackTrace();
        }
        return content;
    }
}
