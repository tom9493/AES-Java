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

    public char[] encrypt(char[] bytes, int verbose) {
        state = new State(bytes, Nb);
        SubBytes subBytes = new SubBytes();
        ShiftRows shiftRows = new ShiftRows();
        MixColumns mixColumns = new MixColumns();
        AddRoundKey addRoundKey = new AddRoundKey();

        addRoundKey.addRoundKey(state.getMatrix(), keySchedule, 0);

        for (int i = 1; i < Nr; ++i) {
            subBytes.subBytes(state.getMatrix());
            shiftRows.shiftRows(state.getMatrix());
            mixColumns.mixColumns(state.getMatrix());
            addRoundKey.addRoundKey(state.getMatrix(), keySchedule, i);
        }

        subBytes.subBytes(state.getMatrix());
        shiftRows.shiftRows(state.getMatrix());
        addRoundKey.addRoundKey(state.getMatrix(), keySchedule, Nr);

        return state.stateToArray();
    }

    public char[] decrypt(char[] bytes, int verbose) {
        state = new State(bytes, Nb);
        SubBytes subBytes = new SubBytes();
        ShiftRows shiftRows = new ShiftRows();
        MixColumns mixColumns = new MixColumns();
        AddRoundKey addRoundKey = new AddRoundKey();

        addRoundKey.addRoundKey(state.getMatrix(), keySchedule, Nr);
        shiftRows.invShiftRows(state.getMatrix());
        subBytes.invSubBytes(state.getMatrix());

        for (int i = Nr-1; i > 0; --i) {
            addRoundKey.addRoundKey(state.getMatrix(), keySchedule, i);
            mixColumns.invMixColumns(state.getMatrix());
            shiftRows.invShiftRows(state.getMatrix());
            subBytes.invSubBytes(state.getMatrix());
        }

        addRoundKey.addRoundKey(state.getMatrix(), keySchedule, 0);

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

    public static void printBytes(char[] bytes) {
        for (int i = 0; i < bytes.length; ++i) {
            System.out.print(Integer.toHexString(bytes[i]) + " ");
        }
        System.out.println();
    }
}
