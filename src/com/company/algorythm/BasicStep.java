package com.company.algorythm;

public class BasicStep
{
    public static byte[] sTable = new byte[]{
            13, 0, 6, 2, 14, 5, 12, 9, 3, 10, 1, 8, 15, 11, 7, 4,
            5, 4, 3, 11, 15, 13, 8, 12, 6, 14, 2, 0, 9, 1, 10, 7,
            10, 4, 9, 7, 6, 5, 3, 8, 1, 13, 0, 14, 12, 11, 2, 15,
            8, 11, 13, 9, 2, 10, 0, 3, 6, 12, 4, 15, 14, 5, 7, 1,
            1, 10, 0, 8, 14, 2, 9, 6, 11, 7, 12, 15, 3, 5, 13, 4,
            3, 7, 12, 4, 15, 13, 11, 8, 9, 6, 2, 5, 10, 0, 1, 14,
            12, 15, 11, 2, 8, 10, 13, 7, 5, 1, 4, 14, 6, 0, 3, 9,
            5, 12, 3, 14, 0, 11, 15, 4, 13, 9, 8, 2, 10, 7, 1, 6,
    };

    public static long basicEncrypt(long dateFragment, int keyFragment, boolean isLastStep) {
        int N2 = (int)(dateFragment >>> 32);
        int N1 = (int)((dateFragment << 32) >>> 32);
        int X = keyFragment;

        int S = (int)(((long)X + (long)N1) % (4294967296L));

        int S0, S1, S2, S3, S4, S5, S6, S7;

        S0 = S >>> 28;
        S1 = (S << 4) >>> 28;
        S2 = (S << 8) >>> 28;
        S3 = (S << 12) >>> 28;
        S4 = (S << 16) >>> 28;
        S5 = (S << 20) >>> 28;
        S6 = (S << 24) >>> 28;
        S7 = (S << 28) >>> 28;

        S0 = sTable[S0];
        S1 = sTable[0x10 + S1];
        S2 = sTable[0x20 + S2];
        S3 = sTable[0x30 + S3];
        S4 = sTable[0x40 + S4];
        S5 = sTable[0x50 + S5];
        S6 = sTable[0x60 + S6];
        S7 = sTable[0x70 + S7];

        S = S7 + (S6 << 4) + (S5 << 8) + (S4 << 12) + (S3 << 16) +
                (S2 << 20) + (S1 << 24) + (S0 << 28);

        S = ((S << 11) | (S >> 21));


        S = (S ^ N2);

        //Если шаг последний - сдвиг по цепочке не производится
        if (!isLastStep)
        {
            N2 = N1;
            N1 = S;
        }
        else
            N2 = S;


        return ((long) N2) | (((long) N1) << 32);
    }
}