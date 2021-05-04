package com.company.algorythm;

public  class Gost {
    static public String encryptFile(String text, String skey) {
        byte[] file = Gost.getBytesFromString(text);
        byte[] key = Gost.getBytesFromString(skey);
        int[] intKey = getintKeyArray(key);
        long[] longFile = getlongDataArray(file);

        long[] longEncrFile = new long[longFile.length];

        for (int k = 0; k < longFile.length; k++) {
            longEncrFile[k] = longFile[k];

            for (int j = 0; j < 3; j++) {
                for (int i = 0; i < 8; i++) {
                    longEncrFile[k] = BasicStep.basicEncrypt(longEncrFile[k], intKey[i], false);
                }
            }

            for (int i = 7; i >= 0; i--) {

                if (i != 0)
                    longEncrFile[k] = BasicStep.basicEncrypt(longEncrFile[k], intKey[i], false);
                else      //Если шаг последний, передать true
                    longEncrFile[k] = BasicStep.basicEncrypt(longEncrFile[k], intKey[i], true);
            }


        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < longEncrFile.length; i++) {
            for (int j = 0; j < 8; j++){
                stringBuilder.append((char) ((longEncrFile[i] << j * 8) >>> 56));
            }
        }
        return stringBuilder.toString();
    }
    
    static public String decryptFile(String text, String skey) {
        byte[] file = Gost.getBytesFromString(text);
        byte[] key = Gost.getBytesFromString(skey);
        int[] intKey = getintKeyArray(key);
        long[] longFile = getlongDataArray(file);
        long[] longDecrFile = new long[longFile.length];

        for (int k = 0; k < longFile.length; k++) {
            longDecrFile[k] = longFile[k];

            for (int i = 0; i < 8; i++) {
                longDecrFile[k] = BasicStep.basicEncrypt(longDecrFile[k], intKey[i],false);
            }

            for (int j = 0; j < 3; j++) {
                for (int i = 7; i >= 0; i--) {
                    if ((j == 2) && (i == 0))
                        longDecrFile[k] = BasicStep.basicEncrypt(longDecrFile[k], intKey[i], true);
                    else
                        longDecrFile[k] = BasicStep.basicEncrypt(longDecrFile[k], intKey[i], false);
                }
            }
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < longDecrFile.length; i++) {
            for (int j = 0; j < 8; j++){
                stringBuilder.append((char) ((longDecrFile[i] <<  j * 8) >>> 56));
            }
        }
        return stringBuilder.toString();
    }


    static public byte[] getBytesFromString(String fl) {
        byte[] encrByteFile = new byte[fl.length()];

        for (int i = 0; i < fl.length(); i++) {
            encrByteFile[i] = (byte)(((int)fl.charAt(i)));
        }

        return encrByteFile;
    }

    static public String getStringFromBytes(byte[] fl) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < fl.length; i++) {
            stringBuilder.append((char) (fl[i]));
        }

        return stringBuilder.toString();
    }

    static private byte[] convertToByte(long[] fl) {
        byte[] temp = new byte[8];
        byte[] encrByteFile = new byte[fl.length * 8];

        for (int i = 0; i < fl.length; i++) {
            for(int j = 0; j < 8; j++)
                temp[j] = (byte)((fl[i] << j * 8) >>> 56);

            for (int j = 0; j < temp.length; j++)
                encrByteFile[j + i * 8] = temp[j];
        }

        return encrByteFile;
    }

    static private int[] getintKeyArray(byte[] byteKey) {
        int[] key = new int[8];

        for (int i = 0; i < key.length; i++) {
            key[i] = (byteKey[i*4] << 24) + (byteKey[i*4+1] << 16) + (byteKey[i*4+2] << 8) + (byteKey[i*4+3]);
        }

        return key;
    }

    static private long[] getlongDataArray(byte[] byteData) {
        long[] data = new long[byteData.length / 8];

        for (int i = 0; i < data.length; i++) {
            data[i] = 0;
            for (int  j = 0; j < 8; j++){
                data[i] += (long)byteData[i * 8 + j] << (56L - j * 8L);
            }
        }

        return data;
    }
}