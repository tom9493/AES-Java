package Transformations;

public class AddRoundKey {

    public void addRoundKey(char[][] state, char[][] keySchedule, int round) {
        int k = 0;
        for (int i = round*4; i < (round*4)+4; ++i) {
            for (int j = 0; j < 4; ++j) {
                state[j][k] ^= keySchedule[j][i];
            }
            ++k;
        }
    }
}
