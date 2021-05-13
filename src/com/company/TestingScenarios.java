package com.company;

import com.company.algorythm.Gost;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class TestingScenarios {
    private final int N = 1024*32;

    public byte[] generateFirstScenario() {
        return Gost.encryptFile(fulfillVector(N), fulfillVector(32));
    }

    public byte[] generateWithSmallOpenTextWeight() {
        byte[] openText;
        Random rand = new Random();
        byte[] key = fulfillVector(32);

        int ones = rand.nextInt(3*N/32);
        openText = fulfillBlockWithWeight(ones, "one", N);
        return Gost.encryptFile(openText, key);

    }

    public byte[] generateWithHeavyOpenTextWeight() {
        byte[] openText;
        Random rand = new Random();
        byte[] key = fulfillVector(32);

        int zeros = rand.nextInt(3*N/32);
        openText = fulfillBlockWithWeight(zeros, " ", N);
        return Gost.encryptFile(openText, key);
    }

    public byte[] generateWithHeavyKeyWeight() {
        byte[] key;
        Random rand = new Random();
        int zeros = rand.nextInt(2)+1;
        key = fulfillBlockWithWeight(zeros, " ", 32);
        return Gost.encryptFile(fulfillVector(N), key);
    }

    public byte[] generateWithSmallKeyWeight() {
        byte[] key;
        Random rand = new Random();
        int ones = rand.nextInt(2)+1;
        key = fulfillBlockWithWeight(ones, "one", 32);
        return Gost.encryptFile(fulfillVector(N), key);
    }

    public byte[] errorIncreaseByKey() {
        byte[] openText = fulfillVector(64);
        byte[][] output = new byte[N/openText.length][];
        byte[] keyJ;
        byte[] key;

        for (int i = 0; i < N / (openText.length*256); i++) {

            for (int j = 0; j < 256; j++) {
                key = fulfillVector(32);
                keyJ = new byte[]{0, 0, 0, 0, 0, 0, 0, 0,0, 0, 0, 0, 0, 0, 0, 0,0, 0, 0, 0, 0, 0, 0, 0,0, 0, 0, 0, 0, 0, 0, 0,};
                keyJ[j / 8] += 1 << (j % 8);
                byte[] Fki = Gost.encryptFile(openText, key);
                byte[] FkiFkj = Gost.encryptFile(openText, XOR(key, keyJ));
                output[i*256 + j] = XOR(Fki, FkiFkj);
            }
        }
        byte[] normalOutput = new byte[N];
        for (int i = 0; i < N/openText.length; i++) {
            for (int j = 0; j < openText.length; j++) {
                normalOutput[i*openText.length + j] = output[i][j];
            }
        }

        return normalOutput;
    }

    public byte[] errorIncreaseByOpenText() {
        byte[] key = fulfillVector(32);
        byte[][] output = new byte[N / 8][];
        byte[] textXi;
        byte[] textX;

        for (int i = 0; i < N / (64 * 8); i++) {

            for (int j = 0; j < 64; j++) {
                textX = fulfillVector(8);
                textXi = new byte[]{0, 0, 0, 0, 0, 0, 0, 0};
                textXi[j / 8] += 1 << (j % 8);
                byte[] FkXi = Gost.encryptFile(textX, key);
                byte[] FkXiXj = Gost.encryptFile(XOR(textXi, textX), key);
                output[i * 64 + j] = XOR(FkXi, FkXiXj);
            }

        }

        byte[] normalOutput = new byte[N];
        for (int i = 0; i < N/8; i++) {
            for (int j = 0; j < 8; j++) {
                normalOutput[i*8 + j] = output[i][j];
            }
        }

        return normalOutput;
    }

    private static byte[] XOR(byte[] a, byte[] b) {
        byte[] builder = new byte[a.length];
        for (int i = 0; i < a.length; i++) {
            builder[i] = (byte) ((int) a[i] ^ (int) b[i]);
        }
        return builder;
    }

    public byte[] correlation() {
        byte[][] output = new byte[N/8][];
        byte[] key = fulfillVector(32);
        for (int i = 0; i < N/8; i++) {
            byte[] openText = fulfillVector(8);
            byte[] function = Gost.encryptFile(openText, key);
            output[i] = XOR(openText, function);
        }

        byte[] normalOutput = new byte[N];
        for (int i = 0; i < N/8; i++) {
            for (int j = 0; j < 8; j++) {
                normalOutput[i*8 + j] = output[i][j];
            }
        }
        return normalOutput;
    }

    public byte[] chainProcessing() {
        byte[][] output = new byte[N/64][];
        byte[] tempOutput = new byte[64];
        byte[] key = fulfillVector(32);
        for (int i = 0; i < N/64; i++) {
            byte[] temp = Gost.encryptFile(tempOutput, key);
            output[i] = temp;
            tempOutput = temp.clone();
        }
        byte[] normalOutput = new byte[N];
        for (int i = 0; i < N/64; i++) {
            for (int j = 0; j < 64; j++) {
                normalOutput[i*64 + j] = output[i][j];
            }
        }
        return normalOutput;
    }

    private static byte[] fulfillVector(int length) {
        byte[] stringBuilder = new byte[length];
        for (int i = 0; i < length; i++) {
            byte tmp = 0;
            for (int j = 0; j < 8; j++) {
                tmp += Math.round(Math.random()) << j;
            }
            stringBuilder[i] = tmp;
        }
        return stringBuilder;
    }

    private static byte[] fulfillBlockWithWeight(int number, String type, int size) {
        ArrayList<Byte> vector = new ArrayList<>();
        HashSet<Integer> hash = new HashSet<>();
        for (int i = 0; i < number; ++i) {
            Random rand = new Random();
            int index = rand.nextInt(size * 8);
            if (hash.contains(index))
                --i;
            else
                hash.add(index);
        }
        if (type.equals("one")) {
            for (int i = 0; i < size * 8; ++i) {
                if (hash.contains(i)) {
                    vector.add((byte) 1);
                } else {
                    vector.add((byte) 0);
                }
            }
        } else {
            for (int i = 0; i < size * 8; ++i) {
                if (hash.contains(i)) {
                    vector.add((byte) 0);
                } else {
                    vector.add((byte) 1);
                }
            }
        }

        byte[] stringBuilder = new byte[size];
        for (int i = 0; i < size; i++) {
            byte tmp = 0;
            for (int j = 0; j < 8; j++) {
                tmp += (vector.get(i * 8 + j) << j);
            }
            stringBuilder[i] = tmp;
        }

        return stringBuilder;
    }
}
