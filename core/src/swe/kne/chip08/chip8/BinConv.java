package swe.kne.chip08.chip8;


/**
 * Used as a helper-class wherein static methods for fiddling around with bits and bytes exists.
 * Short for Binary Conversion.
 */
public class BinConv {

    public static String get8bitString(String sprite) {
        StringBuilder stringBuilder = new StringBuilder(8);
        String s = null;
        if (sprite.length() < 8) {
            for (int temp = 0; temp < 8 - sprite.length(); temp++) {
                s = stringBuilder.append("0").toString();
            }
            s = stringBuilder.append(sprite).toString();
        }
        else {
            s = sprite;
        }
        return s;
    }

    /**
     *
     * @param i, an already masked short, i.e. only includes the relevant nibble
     * @return an int with the right-most nibble being the 4 bits from the param.
     */
    public static int getUnsignedNibble(int i) {
        if (i < 0) {
            i = i & 0x00FFFFFF;
        }
        if (i <= 15) {
        return i;
        }
        if (i <= 255) {
            return i >>> 4;
        }
        if (i <= 4095) {
            return i >>> 8;
        }
        if (i <= 65535) {
            return i >>> 12;
        }
        return i;
    }
    /**
     *
     * @param ubyte, an already masked byte, i.e. only includes the relevant byte
     * @return an int with the right-most byte being the 8 bits from the param.
     */
    public static int getUnsignedByte(long ubyte) {
        int i = (int) ubyte;
        if (i < 0) {
            i = i & 0x0000FFFF;
        }
        if (i <= 15) {
            return i;
        }
        if (i <= 255) {
            return i;
        }
        if (i <= 4095) {
            return i >> 4;
        }
        if (i <= 65535) {
            return i >> 8;
        }
        return i;
    }
    /**
     *
     * @param l an already masked slab (12-bits), i.e. only the relevant 12 bits.
     * @return an int with the right-most slab being the 12 bits from the param.
     */
    public static int getUnsignedSlab(long l) {
        int i = (int) l;
        if (i < 0) {
            i = (int) (l & 0x0FFFFFFF);
        }
        if (i <= 4095) {
            return i;
        }
        if (i <= 65535) {
            return i >>> 4;
        }
        if (i <= 1048575) {
            return i >>> 8;
        }
        if (i <= 16777215) {
            return i >>> 12;
        }
        if (i <= 268435455 ) {
            return i >>> 16;
        }
        return i;
    }
    /**
     *
     * @param instruction the instruction, 4 bytes.
     * @return if negative: return last byte only. if positive: shift it 12 steps to the right and remove the zero
     * that makes a signed number negative.
     */
    public static int getOpcode(long instruction) {
        int i = (int) instruction;
        if (i < 0) {
            return i & 0x0000000F;
        }
        else {
            return (int) (instruction >>> 12);
        }
    }
}
