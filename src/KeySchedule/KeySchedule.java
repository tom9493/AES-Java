package KeySchedule;

public class KeySchedule {

    public char[][] getKeySchedule(char[][] key, int Nr, int Nk) {
        RotWord rw = new RotWord();
        SubWord sw = new SubWord();
        Rcon rc = new Rcon(Nr);

        char[][] keySchedule = new char[4][4*(Nr+1)];
        insertKeyIntoSchedule(key, keySchedule, Nk);

        for (int col = Nk; col < 4*(Nr+1); ++col) {
            if (col % Nk == 0) {
                char[][] word = extractWord(keySchedule, col - 1);      // Initially gets column 3 from cipher key (4th one)
                rw.rotateWord(word);                                       // Manipulates word
                sw.subWord(word);
                rc.applyRcon(word, col/Nk);
                exclusiveOrWithWord(word, keySchedule, col - Nk, Nk);
            }
            else {
                for (int row = 0; row < 4; ++row) {
                    if (Nk > 6 && col % Nk == 4) {
                        char[][] word = extractWord(keySchedule, col - 1);
                        sw.subWord(word);
                        keySchedule[row][col] = (char) (keySchedule[row][col - Nk] ^ word[row][0]);
                    } else {
                        keySchedule[row][col] = (char) (keySchedule[row][col - Nk] ^ keySchedule[row][col - 1]);
                    }
                }
            }
//            System.out.println("Round: " + round);
//            printKeyScheduleSoFar(keySchedule, round);
        }
        return keySchedule;
    }

    public void insertKeyIntoSchedule(char[][] key, char[][] keySchedule, int Nk) {
        for (int i = 0; i < Nk; ++i) {
            for (int j = 0; j < 4; ++j) {
                keySchedule[j][i] = key[j][i];
            }
        }
    }

    public char[][] extractWord(char[][] schedule, int col) {
        char[][] word = new char[4][1];
        for (int i = 0; i < 4; ++i) { word[i][0] = schedule[i][col]; }
        return word;
    }

    public void exclusiveOrWithWord(char[][] word, char[][] schedule, int col, int Nk) {
        for (int i = 0; i < 4; ++i) {
            schedule[i][col+Nk] = (char) (schedule[i][col] ^ word[i][0]);
        }
    }

    private void printKeyScheduleSoFar(char[][] keySchedule, int round) {
        for (int i = 0; i < 4; ++i) {
            System.out.print("[");
            for (int j = 4; j < (round+1)*4; ++j) {
                System.out.print(Integer.toHexString(keySchedule[i][j]));
                if (j != (round+1)*4-1) { System.out.print(", "); }
            }
            System.out.println("]");
        }
        System.out.println();
    }
}
