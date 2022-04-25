package KeySchedule;

import Helper.Sbox;

public class SubWord {
    Sbox sbox = new Sbox();

    public void subWord(char[][] word) {
        char bite = word[0][0];
        word[0][0] = sbox.getSbox()[bite / 16][bite % 16];
        bite = word[1][0];
        word[1][0] = sbox.getSbox()[bite / 16][bite % 16];
        bite = word[2][0];
        word[2][0] = sbox.getSbox()[bite / 16][bite % 16];
        bite = word[3][0];
        word[3][0] = sbox.getSbox()[bite / 16][bite % 16];
    }

    public void invSubWord(char[][] word) {
        char bite = word[0][0];
        word[0][0] = sbox.getInvsbox()[bite / 16][bite % 16];
        bite = word[1][0];
        word[1][0] = sbox.getInvsbox()[bite / 16][bite % 16];
        bite = word[2][0];
        word[2][0] = sbox.getInvsbox()[bite / 16][bite % 16];
        bite = word[3][0];
        word[3][0] = sbox.getInvsbox()[bite / 16][bite % 16];
    }
}
