package com.company;

import com.company.algorythm.Gost;

import java.util.ArrayList;
import java.util.List;

public class TestingScenarios {
    private final int N = 32;

    public String generateFirstScenario() {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < N; i++) {
            output.append(Gost.encryptFile(vectorToString(fulfillVector()), vectorToString(fulfillVector())));
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
        do {
            openText = vectorToString(fulfillVector());
        }
        while (!isLight(openText));
        for (int i = 0; i < N; i++) {
            output.append(Gost.encryptFile(openText, vectorToString(fulfillVector())));
        }
        return output.toString();

    }

    public String generateWithHeavyOpenTextWeight() {
        StringBuilder output = new StringBuilder();
        String openText;
        do {
            openText = vectorToString(fulfillVector());
        }
        while (!isHeavy(openText));
        for (int i = 0; i < N; i++) {
            output.append(Gost.encryptFile(openText, vectorToString(fulfillVector())));
        }
        return output.toString();
    }

    public String generateWithHeavyKeyWeight() {
        StringBuilder output = new StringBuilder();
        String key;
        do {
            key = vectorToString(fulfillVector());
        }
        while (!isHeavy(key));
        for (int i = 0; i < N; i++) {
            output.append(Gost.encryptFile(vectorToString(fulfillVector()), key));
        }
        return output.toString();

    }

    public String generateWithSmallKeyWeight() {
        StringBuilder output = new StringBuilder();
        String key;
        do {
            key = vectorToString(fulfillVector());
        }
        while (!isLight(key));
        for (int i = 0; i < N; i++) {
            output.append(Gost.encryptFile(vectorToString(fulfillVector()), key));
        }
        return output.toString();
    }

    public String errorIncreaseByKey() {
        StringBuilder output = new StringBuilder();
        String openText = "0".repeat(32);
        StringBuilder keyJ;
        String key;
        for (int i = 0; i < 32; i++) {
            for (int j = 0; j < 32; j++) {
                key = vectorToString(fulfillVector());
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
        String key = "0".repeat(32);
        StringBuilder textXi;
        String textX;
        for (int i = 0; i < 32; i++) {
            for (int j = 0; j < 32; j++) {
                textX = vectorToString(fulfillVector());
                textXi = new StringBuilder("0".repeat(32));
                textXi.setCharAt(j, '1');
                String FkXi = Gost.encryptFile(textX, vectorToString(fulfillVector()));
                String FkXiXj = XOR(textXi.toString(), textX);
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
        String openText = vectorToString(fulfillVector());
        String function = Gost.encryptFile(openText, vectorToString(fulfillVector()));
        for (int i = 0; i < N; i++) {
            output.append(XOR(openText, function));
        }
        return output.toString();
    }

    public String chainProcessing() {
        StringBuilder output = new StringBuilder();
        StringBuilder tempOutput = new StringBuilder();
        tempOutput.append("0".repeat(32));
        for (int i = 0; i < N; i++) {
            String temp = Gost.encryptFile(tempOutput.toString(), vectorToString(fulfillVector()));
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
        return vectorStr.length() > 29;
    }

    private static List<Integer> fulfillVector() {
        List<Integer> vector = new ArrayList<>();
        for (int i = 0; i < 32; i++) {
            vector.add(round(Math.random()));
        }
        return vector;
    }

    private static String vectorToString(List<Integer> vector) {
        StringBuilder builder = new StringBuilder();
        for (Integer value : vector) {
            builder.append(value);
        }
        return builder.toString();
    }
}
