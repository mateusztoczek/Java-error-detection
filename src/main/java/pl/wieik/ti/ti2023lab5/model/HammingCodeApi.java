package pl.wieik.ti.ti2023lab5.model;
// import java.util.*;

public class HammingCodeApi {

    public static char convertBinaryToAscii(int[] inputArray) {
        int[] indexesToRemove = new int[]{0, 1, 3, 7, inputArray.length - 1};

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < inputArray.length; i++) {
            if (!contains(indexesToRemove, i)) {
                stringBuilder.append(inputArray[i]);
            }
        }

        String binaryString = stringBuilder.toString();
        int decimalValue = Integer.parseInt(binaryString, 2);
        char asciiChar = (char) decimalValue;

        return asciiChar;
    }

    public static boolean contains(int[] array, int value) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == value) {
                return true;
            }
        }
        return false;
    }

    public static boolean isPowerOfTwo(int number) {
        return (number & (number - 1)) == 0;
    }
    public static int[] charToBinaryIntArray(char c) {
        int asciiValue = (int) c;  // Zamiana znaku na wartość liczbową (kod ASCII)
        String binaryAscii = Integer.toBinaryString(asciiValue);  // Zamiana wartości liczbowej na kod binarny ASCII

        int[] binaryArray = new int[binaryAscii.length()];
        for (int i = 0; i < binaryAscii.length(); i++) {
            binaryArray[i] = binaryAscii.charAt(i) - '0';  // Konwersja znaku na wartość int (0 lub 1)
        }

        return binaryArray;
    }
    public static int[] getHammingCode(int data[]) {
        // declare an array that will store the hamming code for the data
        int returnData[];
        int size;
        // code to get the required number of parity bits
        int i = 0, parityBits = 0 ,j = 0, k = 0;
        size = data.length;
        while(i < size) {
            // 2 power of parity bits must equal to the current position(number of bits traversed + number of parity bits + 1).
            if(Math.pow(2, parityBits) == (i + parityBits + 1)) {
                parityBits++;
            }
            else {
                i++;
            }
        }

        // the size of the returnData is equal to the size of the original data + the number of parity bits.
        returnData = new int[size + parityBits];

        // for indicating an unset value in parity bit location, we initialize returnData array with '2'

        for(i = 1; i <= returnData.length; i++) {
            // condition to find parity bit location
            if(Math.pow(2, j) == i) {

                returnData[(i - 1)] = 2;
                j++;
            }
            else {
                returnData[(k + j)] = data[k++];
            }
        }
        // use for loop to set even parity bits at parity bit locations
        for(i = 0; i < parityBits; i++) {

            returnData[((int) Math.pow(2, i)) - 1] = getParityBit(returnData, i);
        }

        int partiyAllCheck = 0;
        for (int z = 0 ; z < returnData.length ; z++){
            if(returnData[z] == 1){
                if (partiyAllCheck == 0) {
                    partiyAllCheck = 1;
                } else {
                    partiyAllCheck = 0;
                }
            }
        }

        int returnDataFinal[] = new int[size + parityBits + 1];
        for (int x = 0 ; x < returnData.length ; x++){
            returnDataFinal[x] = returnData[x];
        }
        returnDataFinal[returnDataFinal.length - 1] = partiyAllCheck;

        return returnDataFinal;
    }

    public static int getParityBit(int returnData[], int pow) {
        int parityBit = 0;
        int size = returnData.length;

        for(int i = 0; i < size; i++) {

            // check whether returnData[i] contains an unset value or not
            if(returnData[i] != 2) {

                // if not, we save the index in k by increasing 1 in its value

                int k = (i + 1);

                // convert the value of k into binary
                String str = Integer.toBinaryString(k);

                //Now, if the bit at the 2^(power) location of the binary value of index is 1,
                // we check the value stored at that location. If the value is 1 or 0,
                // we will calculate the parity value.

                int temp = ((Integer.parseInt(str)) / ((int) Math.pow(10, pow))) % 10;
                if(temp == 1) {
                    if(returnData[i] == 1) {
                        parityBit = (parityBit + 1) % 2;
                    }
                }
            }
        }
        return parityBit;
    }

    public static HammingResponse receiveData(int data[], int parityBits) {

        HammingResponse response = new HammingResponse();
        // declare variable pow, which we use to get the correct bits to check for parity.
        int pow;
        int size = data.length - 1; // -1 beacause last bit is additional bit
        // declare parityArray to store the value of parity check
        int parityArray[] = new int[parityBits];
        // we use errorLoc string for storing the integer value of the error location.
        String errorLoc = new String();
        // use for loop to check the parities
        for(pow = 0; pow < parityBits; pow++) {
            // use for loop to extract the bit from 2^(power)
            for(int i = 0; i < size; i++) {
                // convert the value of j into binary
                String str = Integer.toBinaryString(i + 1);
                // find bit by using str
                int bit = ((Integer.parseInt(str)) / ((int) Math.pow(10, pow))) % 10;
                if(bit == 1) {
                    if(data[i] == 1) {
                        parityArray[pow] = (parityArray[pow] + 1) % 2;
                    }
                }
            }
            errorLoc = parityArray[pow] + errorLoc;
        }
        // This gives us the parity check equation values.
        // Using these values, we will now check if there is a single bit error and then correct it.
        // errorLoc provides parity check eq. values which we use to check whether a single bit error is there or not
        // if present, we correct it
        int finalLoc = Integer.parseInt(errorLoc, 2);
        // check whether the finalLoc value is 0 or not
        int parityChechAll = 0;
        for (int i = 0 ; i < data.length ; i++) {
            if(data[i] == 1){
                if (parityChechAll == 0) {
                    parityChechAll = 1;
                } else {
                    parityChechAll = 0;
                }
            }
        }

        if(finalLoc != 0 && parityChechAll == 1) {

            response.setMessage("Znaleziono błąd w indexie ["+ finalLoc + "]");
            data[finalLoc - 1] = (data[finalLoc - 1] + 1) % 2;
            response.setError_loc(finalLoc);
            response.setResponse(data);

            return response;
        } else if (finalLoc != 0 && parityChechAll == 0) {

            response.setMessage("Znaleziono dwa błędy.");
            response.setError_loc(-1);
            response.setResponse(data);

            return response;
        } else {

            response.setMessage("Nie znaleziono błędu.");
            response.setError_loc(-1);
            response.setResponse(data);

            return response;
        }
    }

}
