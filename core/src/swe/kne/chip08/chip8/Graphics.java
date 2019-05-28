package swe.kne.chip08.chip8;


public class Graphics {
    private char[] pixels = new char[64 * 32];
    private char[] debugshapes;
    private int resMulti;
    private StringBuilder stringBuilder = new StringBuilder(8);
    private boolean renderScreen = false;

    public Graphics(int resMulti) {
        resetGraphics();
        this.resMulti = resMulti;
    }

    public void resetGraphics() {
        pixels = new char[64 * 32];
    }
    public char[] getAllPixels() {
        return this.pixels;
    }

    // TODO: rewrite, refactor, clean up.
    public boolean xorSprite(int location, short[] fullSprite) {
        boolean setFFlag = false;
        int y = getRow(location);
        int x = getColumn(location);
        for (int i = 0; i < fullSprite.length; i++) { // amount of rows/x
            String sprite = Integer.toBinaryString(fullSprite[i]);
            sprite = get8bitString(sprite); // Make the string exactly 8 bits long, i.e. add 0's to the left if neccessary.

            for (int iter = 0; iter < 8; iter++) { // amount of columns/y
                if (Character.getNumericValue(sprite.charAt(iter)) + pixels[(x * 64) + (y + iter)] > 0 ) {
                    setFFlag = true;
                    pixels[(x * 64) + (y + iter)] = '0';
                }
                else {
                    pixels[(x * 64) + (y + iter)] = '1';
                    renderScreen = true;
                }
            }
        }
        debugGetShape(fullSprite.length, fullSprite);
        return true;
    }
    public int getRow (int i) {
        return i % 64;
    }
    public int getColumn(int i) {
        int row = getRow(i);
        return (i - (64 * row));
    }
    public String get8bitString(String sprite) {
        String s = null;
        if (sprite.length() < 8) {
            for (int temp = 0; temp < (8 - sprite.length()); temp++) {
                s = stringBuilder.append("0").toString();
            }
            s = stringBuilder.append(sprite).toString();
        }
        else {
            s = sprite;
        }
        return s;
    }

    public boolean isRenderScreen() {
        if (renderScreen) {
            renderScreen = false;
            return true;
        }
        else {
            return false;
        }
    }

    //a better version of debugGetShape hereunder.
    public char[] createSpriteShape(int height, short[] pixels) {
        char[] spriteShape = new char[8 * height];
        for (int x = 0; x < pixels.length; x++) {
            String strRow = get8bitString(Integer.toBinaryString(pixels[x]));
            for (int y = 0; y < 8; y++) {
                if (strRow.charAt(y) == '1') {
                    spriteShape[(x * 8) + y] = '1';
                }
                else {
                    spriteShape[(x * 8) + y] = '0';
                }
            }
        }
        return spriteShape;
    }
    public char[] debugGetShape(int y, short[] pixels) {
        debugshapes = new char[8 * y];
        for (int iter = 0; iter < pixels.length; iter++) {
            String str = Integer.toBinaryString(pixels[iter]);
            str = get8bitString(str);
            for (int inner = 0; inner < 8; inner++) {
                if (str.charAt(inner) == '1') {
                    debugshapes[(iter * 8) +(inner)] = '1';
                }
                else {
                    debugshapes[(iter * 8) +(inner)] = '0';
                }
            }
        }
        return debugshapes;
    }
}
