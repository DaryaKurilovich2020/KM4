package com.company;

import com.company.algorythm.Gost;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class TestingScenarios {
    private final int N = 131072;

    public String generateFirstScenario() {
        StringBuilder output = new StringBuilder();
        String key = fulfillVector(32);
        for (int i = 0; i < N; i++) {
            output.append(Gost.encryptFile(fulfillVector(8), key));
        }
        return output.toString();
    }

    private static int round(double x) {
        if (x >= 0.5) {
            return (int) x + 1;
        } else {
            return (int) x;
        }
    }

    public String generateWithSmallOpenTextWeight() {
        StringBuilder output = new StringBuilder();
        String openText;
        Random rand = new Random();
        String key = fulfillVector(32);

        int ones = rand.nextInt(3);
        do {
            openText = fulfillBlockWithWeight(ones, "one", 8);
        }
        while (!isLight(openText));
        for (int i = 0; i < N; i++) {
            output.append(Gost.encryptFile(openText, key));
        }
        return output.toString();

    }

    public String generateWithHeavyOpenTextWeight() {
        StringBuilder output = new StringBuilder();
        String openText;
        Random rand = new Random();
        String key = fulfillVector(32);

        int zeros = rand.nextInt(3);
        do {
            openText = fulfillBlockWithWeight(zeros, " ", 8);
        }
        while (!isHeavy(openText));
        for (int i = 0; i < N; i++) {
            output.append(Gost.encryptFile(openText, key));
        }
        return output.toString();
    }

    public String generateWithHeavyKeyWeight() {
        StringBuilder output = new StringBuilder();
        String key;
        Random rand = new Random();
        int zeros = rand.nextInt(3);
        do {
            key = fulfillBlockWithWeight(zeros, " ", 3);
        }
        while (!isHeavy(key));
        for (int i = 0; i < N; i++) {
            output.append(Gost.encryptFile(fulfillVector(8), key));
        }
        return output.toString();

    }

    public String generateWithSmallKeyWeight() {
        StringBuilder output = new StringBuilder();
        String key;
        Random rand = new Random();
        int ones = rand.nextInt(3);
        do {
            key = fulfillBlockWithWeight(ones, "one", 32);
        }
        while (!isLight(key));
        for (int i = 0; i < N; i++) {
            output.append(Gost.encryptFile(fulfillVector(8), key));
        }
        return output.toString();
    }

    public String errorIncreaseByKey() {
        StringBuilder output = new StringBuilder();
        String openText = "0".repeat(8);
        StringBuilder keyJ;
        String key;
        for (int i = 0; i < 32; i++) {
            for (int j = 0; j < 32; j++) {
                key = fulfillVector(32);
                keyJ = new StringBuilder("0".repeat(32));
                keyJ.setCharAt(j, '1');
                String Fki = Gost.encryptFile(openText, key);
                String FkiFkj = Gost.encryptFile(openText, XOR(key, keyJ.toString()));
                output.append(XOR(Fki, FkiFkj));
            }
        }
        return output.toString();
    }

    public String errorIncreaseByOpenText() {
        StringBuilder output = new StringBuilder();
        String key = fulfillVector(32);
        StringBuilder textXi;
        String textX;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                textX = fulfillVector(8);
                textXi = new StringBuilder("0".repeat(8));
                textXi.setCharAt(j, '1');
                String FkXi = Gost.encryptFile(textX, key);
                String FkXiXj = Gost.encryptFile(XOR(textXi.toString(), textX), key);
                output.append(XOR(FkXi, FkXiXj));
            }
        }
        return output.toString();
    }

    private static String XOR(String a, String b) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < a.length(); i++) {
            builder.append(((int) a.charAt(i) + (int) b.charAt(i)) % 2);
        }
        return builder.toString();
    }

    public String correlation() {
        StringBuilder output = new StringBuilder();
        String key = fulfillVector(32);
        for (int i = 0; i < N; i++) {
            String openText = fulfillVector(8);
            String function = Gost.encryptFile(openText, key);
            output.append(XOR(openText, function));
        }
        return output.toString();
    }

    public String chainProcessing() {
        StringBuilder output = new StringBuilder();
        StringBuilder tempOutput = new StringBuilder();
        output.append(tempOutput);
        String key = fulfillVector(32);
        for (int i = 0; i < N; i++) {
            String temp = Gost.encryptFile(tempOutput.toString(), key);
            output.append(temp);
            tempOutput = new StringBuilder(temp);
        }
        return output.toString();
    }

    private static boolean isLight(String vector) {
        String vectorStr = vector.replaceAll("0", "");
        return vectorStr.length() <= 2;
    }

    private static boolean isHeavy(String vector) {
        String vectorStr = vector.replaceAll("0", "");
        return vectorStr.length() > 5;
    }

    private static String fulfillVector(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            byte tmp = 0;
            for (int j = 0; j < 8; j++){
                tmp += Math.round(Math.random()) << j;
            }
            stringBuilder.append(tmp);
        }
        return stringBuilder.toString();
    }

    private static String fulfillBlockWithWeight(int number, String type, int size) {
        ArrayList<Byte> vector = new ArrayList<>();
        HashSet<Integer> hash = new HashSet<>();
        for (int i = 0; i < number; ++i) {
            Random rand = new Random();
            int index = rand.nextInt(size);
            if (hash.contains(index))
                --i;
            else
                hash.add(index);
        }
        if (type.equals("one")) {
            for (int i = 0; i < size; ++i) {
                if (hash.contains(i)) {
                    vector.add((byte) 1);
                } else {
                    vector.add((byte) 0);
                }
            }
        } else {
            for (int i = 0; i < size; ++i) {
                if (hash.contains(i)) {
                    vector.add((byte)0);
                } else {
                    vector.add((byte)1);
                }
            }
        }

        for(int i = 0; i < size/8; i++){
            byte tmp
            for ( int j = 0; j < 8 ; j++){

            }
        }
        return vector;
    }
}
