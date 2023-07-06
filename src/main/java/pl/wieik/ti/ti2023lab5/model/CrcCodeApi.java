package pl.wieik.ti.ti2023lab5.model;

public class CrcCodeApi {

    public static int[] convertStrToIntArr(String input) {
        if (input.isEmpty()) {
            return new int[0];
        }

        String[] strArr = input.split("");
        int[] intArr = new int[strArr.length];

        for (int i = 0; i < strArr.length; i++) {
            intArr[i] = Integer.parseInt(strArr[i]);
        }

        return intArr;
    }

    public static int[] getCRC(int[] data, int[] divisor) {
        //copy data
        int[] rem = new int[divisor.length];
        int[] tempData = new int[data.length + divisor.length];
        System.arraycopy(data, 0, tempData, 0, data.length);
        System.arraycopy(tempData, 0, rem, 0, divisor.length);

        // division to get rem
        for(int i = 0; i < data.length; i++) {
            if(rem[0] == 1) {
                for(int j = 1; j < divisor.length; j++) {
                    rem[j-1] = getXOR(rem[j], divisor[j]) ? 1 : 0;
                }
            }
            else {
                for(int j = 1; j < divisor.length; j++) {
                    rem[j-1] = getXOR(rem[j], 0) ? 1 :0;
                }
            }
            rem[divisor.length-1] = tempData[i+divisor.length];
        }
        return rem;
    }

    public static boolean getXOR(int x, int y) {
        return !(x==y);
    }

    public static String convertIntArrayToStr(int[] actualOutput) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int num : actualOutput) {
            stringBuilder.append(num);
        }
        return stringBuilder.toString();
    }

    public static boolean receiveData(int[] data, int[] divisor, int[] oldCRC) {
        //wyznacza crc dla nowych danych
        int[] rem = getCRC(data, divisor);
        return areArraysEqual(rem,oldCRC);
    }

    public static boolean areArraysEqual(int[] array1, int[] array2) {
        // jesli tablice sa roznej dlugosci, nie sa rowne
        if (array1.length != array2.length) {
            return false;
        }

        // Porownaj z sasiednimi elemntami
        for (int i = 0; i < array1.length; i++) {
            if (array1[i] != array2[i]) {
                return false;
            }
        }
        // Wszystkie dane są poprawne
        return true;
    }
}
