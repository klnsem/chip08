package swe.kne.chip08.chip8;


public class Graphics {
    private boolean[] pixels = new boolean[64 * 32];
    private boolean wantRenderScreen = false;

    private Messages messages;

    public Graphics(Messages messages) {
        this.messages = messages;
        resetGraphics();
    }

    public void resetGraphics() {
        pixels = new boolean[64 * 32];
    }
    public boolean[] getAllPixels() {
        return this.pixels;
    }

    public void xorSprite(int pos, boolean[] sprite) {
        int row = newgetRow(pos);
        int column = newgetColumn(pos);
        messages.cpuResetCarryFlag();
        wantRenderScreen = true;

        for (int i = 0; i < sprite.length / 8; i++) { //rows
            for (int k = 0; k < 8; k++) { //8 columns in a byte
                if (sprite[k + i * 8] && pixels[(row + i) * 64 + column + k]) {
                    messages.cpuSetCarryFlag((byte)1);
                    pixels[(row + i) * 64 + column + k] = false;
                }
                else if (sprite[k + i * 8]){
                    pixels[(row + i) * 64 + column + k] = true;
                }
            }
        }
    }

    public int newgetRow(int i) {
        int fix = i - (i % 64);
        fix = fix / 64;
        return fix;
    }
    public int newgetColumn(int i) {
        return i % 64;
    }

    public int getRow (int i) {
        return i % 64;
    }
    public int getColumn(int i) {
        int row = getRow(i);
        return i - 64 * row;
    }

    public boolean wantRenderScreen() {
        if (wantRenderScreen) {
            wantRenderScreen = false;
            return true;
        }
        else {
            return false;
        }
    }

}
