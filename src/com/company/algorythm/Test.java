package com.company.algorythm;

public class Test {

    public static void main(String[] args) {

        String text = "qwertyasdfghzxcv";
        String key = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";
        String encrypted = Gost.encryptFile(text, key);
        System.out.println(encrypted);
        String decrypted = Gost.decryptFile(encrypted, key);
        System.out.println(decrypted);
    }
}
