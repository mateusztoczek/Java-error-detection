package pl.wieik.ti.ti2023lab5.model;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Arrays;

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


    // create divideDataWithDivisor() method to get CRC
    public static int[] divideDataWithDivisor(int oldData[], int divisor[]) {
        // declare rem[] array
        int rem[] = new int[divisor.length];
        int i;
        int data[] = new int[oldData.length + divisor.length];
        // use system's arraycopy() method for copying data into rem and data arrays
        System.arraycopy(oldData, 0, data, 0, oldData.length);
        System.arraycopy(data, 0, rem, 0, divisor.length);
        // iterate the oldData and exor the bits of the remainder and the divisor
        for(i = 0; i < oldData.length; i++) {
            if(rem[0] == 1) {
                // We have to exor the remainder bits with divisor bits
                for(int j = 1; j < divisor.length; j++) {
                    rem[j-1] = exorOperation(rem[j], divisor[j]);
                }
            }
            else {
                // We have to exor the remainder bits with 0
                for(int j = 1; j < divisor.length; j++) {
                    rem[j-1] = exorOperation(rem[j], 0);
                }
            }
            // The last bit of the remainder will be taken from the data
            // This is the 'carry' taken from the dividend after every step
            // of division
            rem[divisor.length-1] = data[i+divisor.length];
        }
        return rem;
    }

    // create exorOperation() method to perform exor data
    static int exorOperation(int x, int y) {
        // This simple function returns the exor of two bits
        if(x == y) {
            return 0;
        }
        return 1;
    }
    public static boolean receiveData(int data[], int divisor[],int oldCRC[]) {
        int rem[] = getCRC(data, divisor);
        return areArraysEqual(rem,oldCRC);
    }

    public static boolean areArraysEqual(int[] array1, int[] array2) {
        // If the arrays have different lengths, they are not equal
        if (array1.length != array2.length) {
            return false;
        }

        // Compare corresponding elements of the arrays
        for (int i = 0; i < array1.length; i++) {
            if (array1[i] != array2[i]) {
                return false;
            }
        }

        // All elements are equal
        return true;
    }

}
