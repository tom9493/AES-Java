package Transformations;

public class MixColumns {
    public final char[][] mixBox =
            {{0x02, 0x03, 0x01, 0x01},
            {0x01, 0x02, 0x03, 0x01},
            {0x01, 0x01, 0x02, 0x03},
            {0x03, 0x01, 0x01, 0x02}};

    public final char[][] invMixBox =
            {{0x0e, 0x0b, 0x0d, 0x09},
            {0x09, 0x0e, 0x0b, 0x0d},
            {0x0d, 0x09, 0x0e, 0x0b},
            {0x0b, 0x0d, 0x09, 0x0e}};

    public void mixColumns(char[][] state) {
        char[][] result = new char[4][4];
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                result[j][i] = fillCell2(mixBox, state, j, i);
            }
        }
        resultToState(result, state);
    }

    public void invMixColumns(char[][] state) {
        char[][] result = new char[4][4];
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                result[j][i] = fillCell2(invMixBox, state, j, i);
            }
        }
        resultToState(result, state);
    }

    private char fillCell2(char[][] first, char[][] second, int row, int col) {
        char total = 0x00;
        for (int i = 0; i < second.length; ++i) {
            char a = first[row][i];
            char b = second[i][col];

            //if (b == 0x40) { return 0xf2; } // 0x20, 0x40, and 0x80 all break on inverse mix columns
            if (a == 0x01) { total ^= second[i][col]; }
            else if (a == 0x02) { total ^= (char)xtime(b); }
            else if (a == 0x03) { total ^= (char) (xtime(b) ^ b); }
            else if (a == 0x09) { total ^= (char) (xtime(xtime(xtime(b))) ^  b); }
            else if (a == 0x0b) { total ^= (char) (xtime(xtime(xtime(b))) ^  xtime(b) ^ b); }
            else if (a == 0x0d) { total ^= (char) (xtime(xtime(xtime(b))) ^  xtime(xtime(b)) ^ b); }
            else if (a == 0x0e) { total ^= (char) (xtime(xtime(xtime(b))) ^  xtime(xtime(b)) ^ xtime(b)); }
            //if (col == 1) { System.out.println("Total so far: " + Integer.toHexString(total)); }
        }
        return total;
    }

    private int xtime(int c) {
        c = (c & 0x000000ff) * 2;
        return (c & 0x100) != 0 ? c ^ 0x11b : c;
    }
    
    private void resultToState(char[][] result, char[][] state) {
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                state[j][i] = result[j][i];
            }
        }
    }

    // Below is the finite field multiplication implementation, using polynomials converted to bytes.
    private char fillCell1(char[][] first, char[][] second, int row, int col) {
        char total = 0x00;
        for (int i = 0; i < second.length; ++i) {
            total ^= FFMultiply(first[row][i], second[i][col]);
        }
        if (total >= 0x11b) { total ^= 0x11b; }
        return total;
    }

    private char FFMultiply(char a, char b) {
        char[] first = getCharArrayWithBits(a);
        char[] second = getCharArrayWithBits(b);
        char[] binary = {'0','0','0','0','0','0','0','0','0','0','0','0'};
        for (int i = 0; i < 8; ++i) {
            if (first[getIndex(i)] == '1') {
                for (int j = 0; j < 8; ++j) {
                    if (second[getIndex(j)] == '1'){ addToBinary(binary, getIndex(i + j) + 4); }
                }
            }
        }
        String result = String.valueOf(binary);
        return (char) Integer.parseInt(result, 2);
    }

    private void addToBinary(char[] binary, int index) {
        if (binary[index] == '1') { binary[index] = '0'; }
        else { binary[index] = '1'; }
    }

    private int getIndex(int i) { return (i - 7) * -1; }

    private char[] getCharArrayWithBits(char c) {
        char[] bits = new char[8];
        byte mask = 0x01;
        for (int j = 0; j < 8; ++j) {
            int value = c & mask;
            if (value != 0) {
                bits[getIndex(j)] = '1';
            } else {
                bits[getIndex(j)] = '0';
            }
            mask <<= 1;
        }
        return bits;
    }
}
