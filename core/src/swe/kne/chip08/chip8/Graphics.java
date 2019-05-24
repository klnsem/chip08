package swe.kne.chip08.chip8;

public class Graphics {
    private Character[] pixels = new Character[64 * 32];

    public Graphics() {
        resetGraphics();
        System.out.println("Graphics started");
    }

    public void resetGraphics() {
        pixels = new Character[64 * 32];
    }
}
