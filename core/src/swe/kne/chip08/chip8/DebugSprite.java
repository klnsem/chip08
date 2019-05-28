package swe.kne.chip08.chip8;

public class DebugSprite {
    public int x;
    public int y;
    public char[] pixels;

    public DebugSprite(int x, int y, char[] pixels) {
        this.x = x;
        this.y = y;
        this.pixels = pixels;
    }
}
