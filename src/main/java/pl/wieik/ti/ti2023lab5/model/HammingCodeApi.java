package pl.wieik.ti.ti2023lab5.model;

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

        return (char) decimalValue;
    }

    public static boolean contains(int[] array, int value) {
        for (int j : array) {
            if (j == value) {
                return true;
            }
        }
        return false;
    }

    public static int[] charToBinaryIntArray(char c) {
        // Zamiana znaku na wartość liczbową (kod ASCII)
        // Zamiana wartości liczbowej na kod binarny ASCII
        String binaryAscii = Integer.toBinaryString(c);
        int[] binaryArray = new int[binaryAscii.length()];

        for (int i = 0; i < binaryAscii.length(); i++) {
            // Konwersja znaku na wartość int (0 lub 1)
            binaryArray[i] = binaryAscii.charAt(i) - '0';
        }

        return binaryArray;
    }

    public static int[] getHammingCode(int[] data) {
        // deklaracja zmiennych
        int[] returnData;
        int size, i = 0, parityBits = 0 ,j = 0, k = 0;
        size = data.length;
        while(i < size) {
            // wartosc potegi musi byc zgodna z obecna pozycja
            if(Math.pow(2, parityBits) == (i + parityBits + 1)) {
                parityBits++;
            } else {
                i++;
            }
        }
        returnData = new int[size + parityBits];

        // dla wskazania nieustawionej wartości w lokalizacji bitu parzystości inicjujemy tablicę returnData z wartoscia 2
        for(i = 1; i <= returnData.length; i++) {
            // warunek znalezienia bitu parzystosci
            if(Math.pow(2, j) == i) {
                returnData[(i - 1)] = 2;
                j++;
            } else {
                returnData[(k + j)] = data[k++];
            }
        }
        // przypisanie parzystych bitów
        for(i = 0; i < parityBits; i++) {
            returnData[((int) Math.pow(2, i)) - 1] = getParityBit(returnData, i);
        }
        int partiyAllCheck = 0;
        for (int returnDatum : returnData) {
            if (returnDatum == 1) {
                if (partiyAllCheck == 0) {
                    partiyAllCheck = 1;
                } else {
                    partiyAllCheck = 0;
                }
            }
        }

        int[] returnDataFinal = new int[size + parityBits + 1];
        System.arraycopy(returnData, 0, returnDataFinal, 0, returnData.length);
        returnDataFinal[returnDataFinal.length - 1] = partiyAllCheck;

        return returnDataFinal;
    }

    public static int getParityBit(int[] returnData, int pow) {
        int parityBit = 0;
        int size = returnData.length;

        for(int i = 0; i < size; i++) {
            // dla kazdego bitu roznego od 2
            if(returnData[i] != 2) {
                int k = (i + 1);
                String str = Integer.toBinaryString(k);
                // wyznaczamy bit parzystosci
                int temp = ((Integer.parseInt(str)) / ((int) Math.pow(10, pow))) % 10;
                if(temp == 1) {
                    // zmieniamy wrtosc bitu
                    if(returnData[i] == 1) {
                        parityBit = (parityBit + 1) % 2;
                    }
                }
            }
        }
        return parityBit;
    }

    public static HammingResponse receiveData(int[] data, int parityBits) {
        HammingResponse hammingResponse = new HammingResponse();
        int pow;
        // ponieważ ostatni bit jest nieznaczący
        int size = data.length - 1;
        int[] parityArray = new int[parityBits];
        StringBuilder errorLoc = new StringBuilder();
        for(pow = 0; pow < parityBits; pow++) {
            for(int i = 0; i < size; i++) {
                // konwersja na wartosc binarna
                String str = Integer.toBinaryString(i + 1);
                // znajdz bit
                int bit = ((Integer.parseInt(str)) / ((int) Math.pow(10, pow))) % 10;
                if(bit == 1) {
                    if(data[i] == 1) {
                        parityArray[pow] = (parityArray[pow] + 1) % 2;
                    }
                }
            }
            errorLoc.insert(0, parityArray[pow]);
        }

        int finalLoc = Integer.parseInt(errorLoc.toString(), 2);
        // ustaw wartość parityChech
        int parityChechAll = 0;
        for (int datum : data) {
            if (datum == 1) {
                if (parityChechAll == 0) {
                    parityChechAll = 1;
                } else {
                    parityChechAll = 0;
                }
            }
        }

        if(finalLoc != 0 && parityChechAll == 1) {
            hammingResponse.setMessage("Znaleziono błąd w indexie ["+ finalLoc + "]");
            data[finalLoc - 1] = (data[finalLoc - 1] + 1) % 2;
            hammingResponse.setError_loc(finalLoc);
            hammingResponse.setResponse(data);

            return hammingResponse;
        } else if (finalLoc != 0) {
            hammingResponse.setMessage("Znaleziono dwa błędy.");
            hammingResponse.setError_loc(-1);
            hammingResponse.setResponse(data);

            return hammingResponse;
        } else {
            hammingResponse.setMessage("Nie znaleziono błędu.");
            hammingResponse.setError_loc(-1);
            hammingResponse.setResponse(data);

            return hammingResponse;
        }
    }

}
