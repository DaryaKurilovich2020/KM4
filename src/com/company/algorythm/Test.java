package com.company.algorythm;

public class Test {

    public static void main(String[] args) {

        String text = "qwertyasdfghzxcv";
        String key = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";
        String encrypted = Gost.encryptFile(Gost.getBytesFromString(text), Gost.getBytesFromString(key));
        System.out.println(encrypted);
        String decrypted = Gost.decryptFile(Gost.getBytesFromString(encrypted), Gost.getBytesFromString(key));
        System.out.println(decrypted);
    }
}
