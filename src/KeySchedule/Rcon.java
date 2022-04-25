package KeySchedule;

public class Rcon {
    char[][] rcon;

    public Rcon(int Nr) {
        char[][] rcon = new char[4][Nr];
        char value = 0x01;
        for (int i = 0; i < Nr; ++i) {
            for (int j = 0; j < 4; ++j) {
                if (j == 0) { rcon[j][i] = value; }
                else { rcon[j][i] = 0x00; }
            }
            value = (char)xtime(value);
        }
        this.rcon = rcon;
    }

    public void applyRcon(char[][] word, int round) {
        round -= 1;
        word[0][0] ^= rcon[0][round];
        word[1][0] ^= rcon[1][round];
        word[2][0] ^= rcon[2][round];
        word[3][0] ^= rcon[3][round];
    }

    public char[][] getRcon() { return rcon; }

    private int xtime(int c) {
        c = (c & 0x000000ff) * 2;
        return (c & 0x100) != 0 ? c ^ 0x11b : c;
    }
}

