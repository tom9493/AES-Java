public class State {
    char[][] matrix;

    public State() {
        matrix = new char[][] { {(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00},
                                {(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00},
                                {(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00},
                                {(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00} };
    }

    public State(char[] bytes, int Nb) {
       // System.out.println("bytes in state constructor: ");
        matrix = new char[4][Nb];
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < Nb; ++j) {
                matrix[i][j] = bytes[i + (4*j)];
                //System.out.print(Integer.toHexString(bytes[i+(4*j)]) + " ");
            }
        }
        System.out.println();
    }

    public char[] stateToArray() {
        char[] bytes = new char[16];
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                bytes[i + (4*j)] = matrix[i][j];
            }
        }
        return bytes;
    }

    public char[][] getMatrix() { return matrix; }
}
