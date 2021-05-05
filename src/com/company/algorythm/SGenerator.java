package com.company.algorythm;

public class SGenerator {

    public static void main(String[] args) {
        byte[] sTable = new byte[8 * 16];

        for (byte i = 0; i < 8; i++) {
            for (byte j = 0; j < 16; j++) {
                sTable[i * 16 + j] = j;
            }
            for (int j = 0; j < 100; j++) {
                byte first = (byte) (Math.random() * 16);
                byte second = (byte) (Math.random() * 16);
                byte tmp = sTable[i * 16 + first];
                sTable[i * 16 + first] = sTable[i * 16 + second];
                sTable[i * 16 + second] = tmp;
            }
        }
        System.out.println("[");
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 16; j++)
                System.out.print(sTable[i * 16 + j] + ", ");
            System.out.println();
        }
        System.out.print("]");
    }
}
