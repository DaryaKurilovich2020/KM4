package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TestingScenarios {
    private int N = 256;

    private static String function(String openText, String key) {
        return "1";
    }

    public String generateFirstScenario() {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < N; i++) {
            output.append(function(vectorToString(fulfillVector()), vectorToString(fulfillVector())));
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
            output.append(function(openText, vectorToString(fulfillVector())));
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
            output.append(function(openText, vectorToString(fulfillVector())));
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
            output.append(function(vectorToString(fulfillVector()), key));
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
            output.append(function(vectorToString(fulfillVector()), key));
        }
        return output.toString();

    }

    public String errorIncreaseByKey() {
        StringBuilder output = new StringBuilder();
        return output.toString();
    }

    public String errorIncreaseByOpenText() {
        StringBuilder output = new StringBuilder();
        return output.toString();
    }

    public String correlation() {
        StringBuilder output = new StringBuilder();
        String openText = vectorToString(fulfillVector());
        String function = function(openText, vectorToString(fulfillVector()));
        for (int i = 0; i < 32; i++) {
            output.append(((int) openText.charAt(i) + (int) function.charAt(i)) % 2);
        }
        return output.toString();
    }

    public String chainProcessing() {
        StringBuilder output = new StringBuilder();
        StringBuilder tempOutput = new StringBuilder();
        tempOutput.append("0".repeat(32));
        for (int i = 0; i < N; i++) {
            String temp = function(tempOutput.toString(), vectorToString(fulfillVector()));
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
