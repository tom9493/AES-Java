package Transformations;

import Helper.Sbox;

public class SubBytes {
    private Sbox sbox;

    public SubBytes() { sbox = new Sbox(); }

    public void subBytes(char[][] matrix) {
        for (int i = 0; i < matrix.length; ++i) {
            for (int j = 0; j < matrix[0].length; ++j) {
                char bite = matrix[j][i];
                matrix[j][i] = sbox.getSbox()[bite / 16][bite % 16];
            }
        }
    }

    public void invSubBytes(char[][] matrix) {
        for (int i = 0; i < matrix.length; ++i) {
            for (int j = 0; j < matrix[0].length; ++j) {
                char bite = matrix[j][i];
                matrix[j][i] = sbox.getInvsbox()[bite / 16][bite % 16];
            }
        }
    }
}
