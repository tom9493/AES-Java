import KeySchedule.KeySchedule;
import Transformations.AddRoundKey;
import Transformations.MixColumns;
import Transformations.ShiftRows;
import Transformations.SubBytes;

public class AES {
    private int Nb;
    private int Nk;
    private int Nr;
    private State state;
    private final char[][] keySchedule;

    public AES(int Nk, char[] cipherKey) {
        state = new State();
        this.Nk = Nk;
        this.Nb = 4;
        this.Nr = 0;

        if (Nk == 4) {
            Nr = 10;
            if (cipherKey.length != 16) { System.out.println("cipher key must be 16 bytes with Nk 4"); }
        }
        else if (Nk == 6) {
            Nr = 12;
            if (cipherKey.length != 24) { System.out.println("cipher key must be 24 bytes with Nk 6"); }
        }
        else if (Nk == 8) {
            Nr = 14;
            if (cipherKey.length != 32) { System.out.println("cipher key must be 32 bytes with Nk 8"); }
        }

        KeySchedule ks = new KeySchedule();
        keySchedule = ks.getKeySchedule(new State(cipherKey, Nb).getMatrix(), Nr);
    }

    public char[] encrypt(char[] bytes, boolean verbose) {
        state = new State(bytes, Nb);
        SubBytes subBytes = new SubBytes();
        ShiftRows shiftRows = new ShiftRows();
        MixColumns mixColumns = new MixColumns();
        AddRoundKey addRoundKey = new AddRoundKey();

        addRoundKey.addRoundKey(state.getMatrix(), keySchedule, 0, verbose, 0);

        for (int i = 1; i < Nr; ++i) {
            if (verbose) { System.out.print("round[" + i + "].start\t\t"); printBytes(); }
            subBytes.subBytes(state.getMatrix());
            if (verbose) { System.out.print("round[" + i + "].s_box\t\t"); printBytes(); }
            shiftRows.shiftRows(state.getMatrix());
            if (verbose) { System.out.print("round[" + i + "].s_row\t\t"); printBytes(); }
            mixColumns.mixColumns(state.getMatrix());
            if (verbose) { System.out.print("round[" + i + "].m_col\t\t"); printBytes(); }
            addRoundKey.addRoundKey(state.getMatrix(), keySchedule, i, verbose, 0);
        }

        if (verbose) { System.out.print("round[" + Nr + "].start\t\t"); printBytes(); }
        subBytes.subBytes(state.getMatrix());
        if (verbose) { System.out.print("round[" + Nr + "].s_box\t\t"); printBytes(); }
        shiftRows.shiftRows(state.getMatrix());
        if (verbose) { System.out.print("round[" + Nr + "].s_row\t\t"); printBytes(); }
        addRoundKey.addRoundKey(state.getMatrix(), keySchedule, Nr, verbose, 0);
        if (verbose) { System.out.print("round[" + Nr + "].output\t"); printBytes(); }

        return state.stateToArray();
    }

    public char[] decrypt(char[] bytes, boolean verbose) {
        state = new State(bytes, Nb);
        SubBytes subBytes = new SubBytes();
        ShiftRows shiftRows = new ShiftRows();
        MixColumns mixColumns = new MixColumns();
        AddRoundKey addRoundKey = new AddRoundKey();
        int j = 1;

        addRoundKey.addRoundKey(state.getMatrix(), keySchedule, Nr, verbose, 1);

        for (int i = Nr-1; i > 0; --i) {
            if (verbose) { System.out.print("round[" + j + "].start\t\t"); printBytes(); }
            shiftRows.invShiftRows(state.getMatrix());
            if (verbose) { System.out.print("round[" + j + "].is_row\t\t"); printBytes(); }
            subBytes.invSubBytes(state.getMatrix());
            if (verbose) { System.out.print("round[" + j + "].is_box\t\t"); printBytes(); }
            addRoundKey.addRoundKey(state.getMatrix(), keySchedule, i, verbose, 1);
            if (verbose) { System.out.print("round[" + j + "].ik_add\t\t"); printBytes(); }
            mixColumns.invMixColumns(state.getMatrix());
            ++j;
        }

        if (verbose) { System.out.print("round[" + Nr + "].start\t\t"); printBytes(); }
        shiftRows.invShiftRows(state.getMatrix());
        if (verbose) { System.out.print("round[" + Nr + "].is_row\t"); printBytes(); }
        subBytes.invSubBytes(state.getMatrix());
        if (verbose) { System.out.print("round[" + j + "].is_box\t"); printBytes(); }
        addRoundKey.addRoundKey(state.getMatrix(), keySchedule, 0, verbose , 1);
        if (verbose) { System.out.print("round[" + Nr + "].output\t"); printBytes(); }

        return state.stateToArray();
    }

    public void printState() {
        char[][] matrix = state.getMatrix();
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

    public void printBytes() {
        char[][] matrix = state.getMatrix();
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                if (matrix[j][i] <= 0x0f) { System.out.print("0"); }
                System.out.print(Integer.toHexString(matrix[j][i]));
            }
        }
        System.out.println();
    }
}
