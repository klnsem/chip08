package swe.kne.chip08.chip8;

public class DebugThings {
    public static void printSprite(boolean[] sprite) {

        for (int i = 0; i < 32; i++) {
            for (int x = 0; x < 64; x++) {
                if (sprite[i * 32 + x]) {
                    System.out.print("-");
                }
                else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }

    }
    public static void printSingleSprite(boolean[] sprite) {
        System.out.println("\n\n");
        for (int i = 0; i < sprite.length / 8; i++) {
            for (int x = 0; x < 8; x++) {
                if (sprite[i * 8 + x]) {
                    System.out.print("x");
                }
                else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    public static void testStackPush(Cpu cpu) {
        cpu.pushStack((short) 0x0F0F);
        cpu.pushStack((short) 0x0FF8);
        cpu.pushStack((short) 0x0AE1);
        cpu.pushStack((short) 0x00CD);
    }
    public static void testStackPop(Cpu cpu) {
        System.out.println(cpu.popStack());
    }
}
