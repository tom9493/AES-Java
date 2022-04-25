package KeySchedule;

public class RotWord {

    public void rotateWord(char[][] word) {
        char val1 = word[0][0];
        char val2 = word[1][0];
        char val3 = word[2][0];
        char val4 = word[3][0];
        word[0][0] = val2;
        word[1][0] = val3;
        word[2][0] = val4;
        word[3][0] = val1;
    }

    public void invRotateWord(char[][] word) {
        char val1 = word[0][0];
        char val2 = word[1][0];
        char val3 = word[2][0];
        char val4 = word[3][0];
        word[0][0] = val4;
        word[1][0] = val1;
        word[2][0] = val2;
        word[3][0] = val3;
    }
}
