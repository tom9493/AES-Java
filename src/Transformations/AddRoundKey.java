package Transformations;

public class AddRoundKey {
    private int r = 0;

    public void addRoundKey(char[][] state, char[][] keySchedule, int round, boolean verbose, int mode) {
        int k = 0;

        if (verbose) {
            if (mode == 0) { System.out.print("round[" + round + "].k_sch\t\t"); }
            else { System.out.print("round[" + r + "].ik_sch\t\t"); }
        }

        for (int i = round * 4; i < (round * 4) + 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                state[j][k] ^= keySchedule[j][i];
                if (verbose) {
                    if (keySchedule[j][i] <= 0x0f) { System.out.print("0"); }
                    System.out.print(Integer.toHexString(keySchedule[j][i]));
                }
            }
            ++k;
        }
        if (verbose) { System.out.println(); }
        ++r;
    }

    private void printKeyScheduleSoFar(char[][] keySchedule, int round) {
        for (int i = 0; i < 4; ++i) {
            System.out.print("[");
            for (int j = 0; j < (round+1)*4; ++j) {
                System.out.print(Integer.toHexString(keySchedule[i][j]));
                if (j != (round+1)*4-1) { System.out.print(", "); }
            }
            System.out.println("]");
        }
        System.out.println();
    }

    public void printState(char[][] matrix) {
        for (int i = 0; i < 4; ++i) {
            System.out.print("[");
            for (int j = 0; j < 4; ++j) {
                System.out.print(Integer.toHexString(matrix[i][j]));
                if (j != 3) { System.out.print(", "); }
            }
            System.out.println("]");
        }
        System.out.println();
    }
}
