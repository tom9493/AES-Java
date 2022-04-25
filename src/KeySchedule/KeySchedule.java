package KeySchedule;

public class KeySchedule {

    public char[][] getKeySchedule(char[][] key, int Nr) {
        RotWord rw = new RotWord();
        SubWord sw = new SubWord();
        Rcon rc = new Rcon(Nr);

        char[][] keySchedule = new char[4][4*(Nr+1)];
        insertKeyIntoSchedule(key, keySchedule);

        for (int round = 1; round < Nr+1; ++round) {
            char[][] word = extractWord(keySchedule, (round*4)-1); // Initially gets column 3 from cipher key (4th one)
            rw.rotateWord(word);                                       // Manipulates word
            sw.subWord(word);
            rc.applyRcon(word, round);
            exclusiveOrWithWords(word, keySchedule, (round*4) - 4);
            for (int col = (round*4)+1; col < (round*4)+4; ++col) {    // for each column, go down rows and ^ with word
                for (int row = 0; row < 4; ++row) {
                    keySchedule[row][col] = (char) (keySchedule[row][col-4] ^ keySchedule[row][col-1]);
                }
            }
            //System.out.println("Round: " + round);
            //printKeyScheduleSoFar(keySchedule, round);
        }
        return keySchedule;
    }

    public void insertKeyIntoSchedule(char[][] key, char[][] keySchedule) {
        for (int i = 0; i < 4; ++i) {
            System.arraycopy(key[i], 0, keySchedule[i], 0, 4);
        }
    }

    public char[][] extractWord(char[][] schedule, int col) {
        char[][] word = new char[4][1];
        for (int i = 0; i < 4; ++i) { word[i][0] = schedule[i][col]; }
        return word;
    }

    public void exclusiveOrWithWords(char[][] word, char[][] schedule, int col) {
        for (int i = 0; i < 4; ++i) {
            schedule[i][col+4] = (char) (schedule[i][col] ^ word[i][0]);
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
