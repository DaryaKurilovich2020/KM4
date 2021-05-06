package com.company.tests;

import org.apache.commons.math3.special.Erf;
import org.apache.commons.math3.special.Gamma;

public class Tests {

    public static double frequencyTest(String bitSequence) {
        int counter = 0;
        for (int j = 0; j < bitSequence.length(); j++) {
            char c = bitSequence.charAt(j);

            counter += 2 * (c % 2) - 1;
        }
        return Erf.erfc(Math.abs(counter) / Math.sqrt(2 * bitSequence.length()));
    }

    public static double frequencyTestWithinABlock(String bitSequence) {
        double[] proportion = new double[bitSequence.length() / 8];

        double hi = 0;

        for (int i = 0; i < bitSequence.length() / 8; i++) {
            int counter = 0;
            for (int j = 0; j < 8; j++) {
                char c = bitSequence.charAt(i * 8 + j);

                for (int z = 0; z < 8; z++) {
                    counter += (c % 2);
                    c >>>= 1;
                }
            }
            proportion[i] = (double) counter / 64;
            hi += (proportion[i] - 0.5) * (proportion[i] - 0.5);
        }
        hi *= 4 * 64;
        return Gamma.regularizedGammaQ((double) (bitSequence.length() / 8) / 2, hi / 2);
    }

    public static double runsTest(String bitSequence) {
        int counter = 0;
        for (int j = 0; j < bitSequence.length(); j++) {
            char c = bitSequence.charAt(j);
            counter += c % 2;
        }
        double proportion = (double) counter / bitSequence.length();
        if (proportion - 0.5 >= 2 / Math.sqrt(bitSequence.length())) {
            return 0;
        }

        counter = 0;
        int last = -1;
        for (int i = 0; i < bitSequence.length(); i++) {
            char c = bitSequence.charAt(i);
            for (int j = 0; j < 8; j++) {
                if (c % 2 != last) {
                    counter++;
                }

                last = c % 2;
            }
        }
        return Erf.erfc((Math.abs(counter - 2 * proportion * bitSequence.length() * (1 - proportion))) /
                (2 * Math.sqrt(2 * bitSequence.length()) * proportion * (1 - proportion)));
    }
}
