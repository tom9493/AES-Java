package Transformations;

public class ShiftRows {

    public void shiftRows(char[][] state) { // Shifts rows to the left
        char[][] result = new char[4][4];
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                result[j][i] = state[j][(i + j) % 4];
            }
        }
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                state[j][i] = result[j][i];
            }
        }
    }
    
    public void invShiftRows(char[][] state) { // Shifts rows to the right
        char[][] result = new char[4][4];
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                result[j][i] = state[j][(i + j + 2*j) % 4];
            }
        }
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                state[j][i] = result[j][i];
            }
        }
    }
}
