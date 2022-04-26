//import KeySchedule.KeySchedule;
//import KeySchedule.Rcon;
//import KeySchedule.RotWord;
//import KeySchedule.SubWord;
//import Transformations.AddRoundKey;
//import Transformations.MixColumns;
//import Transformations.ShiftRows;
//import Transformations.SubBytes;
//import org.junit.*;
//
//public class AESIntegrationTest {
//    private char[][] practice;
//    private char[][] practice2;
//    char[] key = {0x2B,0x7e,0x15,0x16,0x28,0xae,0xd2,0xa6,0xab,0xf7,0x15,0x88,0x09,0xcf,0x4f,0x3c};
//    char[] bytes = {0x32,0x43,0xf6,0xa8,0x88,0x5a,0x30,0x8d,0x31,0x31,0x98,0xa2,0xe0,0x37,0x07,0x34};
//
//    @Before
//    public void setup() {
//        practice = new char[][]
//                {{0x87, 0xf2, 0x4d, 0x97},
//                {0x6e, 0x4c, 0x90, 0xec},
//                {0x46, 0xe7, 0x4a, 0xc3},
//                {0xa6, 0x8c, 0xd8, 0x95}};
//
//        practice2 = new char[][]
//                {{0x2b, 0x28, 0xab, 0x09},
//                {0x7e, 0xae, 0xf7, 0xcf},
//                {0x15, 0xd2, 0x15, 0x4f},
//                {0x16, 0xa6, 0x88, 0x3c}};
//    }
//
//    @Test
//    public void MixColumnsTest() {
//        MixColumns mixColumns = new MixColumns();
//        mixColumns.mixColumns(practice);
//        printState(practice);
//        mixColumns.invMixColumns(practice);
//        printState(practice);
//    }
//
//    @Test
//    public void shiftRowsTest() {
//        ShiftRows shiftRows = new ShiftRows();
//        shiftRows.shiftRows(practice);
//        printState(practice);
//        shiftRows.invShiftRows(practice);
//        printState(practice);
//    }
//
//    @Test
//    public void subBytesTest() {
//        SubBytes subBytes = new SubBytes();
//        subBytes.subBytes(practice);
//        printState(practice);
//        subBytes.invSubBytes(practice);
//        printState(practice);
//    }
//
//    @Test
//    public void rotWordTest() {
//        RotWord rotWord = new RotWord();
//        rotWord.rotateWord(practice);
//        printState(practice);
//        rotWord.invRotateWord(practice);
//        printState(practice);
//    }
//
//    @Test
//    public void subWordTest() {
//        SubWord subWord = new SubWord();
//        subWord.subWord(practice);
//        printState(practice);
//        subWord.invSubWord(practice);
//        printState(practice);
//    }
//
//    @Test
//    public void rconTest() {
//        Rcon rcon = new Rcon(11);
//        printState(rcon.getRcon());
//    }
//
//    @Test
//    public void keyScheduleTest() {
//        KeySchedule ks = new KeySchedule();
//        ks.getKeySchedule(practice2, 12);
//    }
//
//    @Test
//    public void decrypt() {
//        KeySchedule ks = new KeySchedule();
//        State state = new State(bytes, 4);
//        SubBytes sb = new SubBytes();
//        MixColumns mc = new MixColumns();
//        ShiftRows sr = new ShiftRows();
//        AddRoundKey ark = new AddRoundKey();
//
//        char[][] keySchedule = ks.getKeySchedule(new State(key, 4).getMatrix(), 10);
//        char[][] original = state.getMatrix();
//
//        ark.addRoundKey(state.getMatrix(), keySchedule, 0);
//        char[][] rkState1 = state.getMatrix();
//        sb.subBytes(state.getMatrix());
//        char[][] sbState = state.getMatrix();
//        sr.shiftRows(state.getMatrix());
//        char[][] srState = state.getMatrix();
//        mc.mixColumns(state.getMatrix());
//        char[][] mcState = state.getMatrix();
//
//        ark.addRoundKey(state.getMatrix(), keySchedule, 1);
//        char[][] rkState2 = state.getMatrix();
//        ark.addRoundKey(state.getMatrix(), keySchedule, 1);
//        char[][] invrkState2 = state.getMatrix(); // should equal mc State as it reverses the round key guy
//
//        mc.invMixColumns(state.getMatrix());
//        char[][] invmcState = state.getMatrix(); // should equal srState
//        sr.invShiftRows(state.getMatrix());
//        char[][] invsrState = state.getMatrix(); // should equal sbState
//        sb.invSubBytes(state.getMatrix());
//        char[][] invsbState = state.getMatrix(); // should equal rkState1
//        ark.addRoundKey(state.getMatrix(), keySchedule, 0);
//        char[][] invrkState1 = state.getMatrix(); // should equal original
//
//        System.out.println("invrkState2 should equal mcState:");
//        printState(invrkState2);
//        printState(mcState);
//
//        System.out.println("invmcState should equal srState:");
//        printState(invmcState);
//        printState(srState);
//
//        System.out.println("invsrState should equal sbState:");
//        printState(invsrState);
//        printState(sbState);
//
//        System.out.println("invsbState should equal rkState1:");
//        printState(invsbState);
//        printState(rkState1);
//
//        System.out.println("invrkState1 should equal original:");
//        printState(invrkState1);
//        printState(original);
//    }
//
//    public void printState(char[][] state) {
//        for (int i = 0; i < 4; ++i) {
//            System.out.print("[");
//            for (int j = 0; j < state[0].length; ++j) {
//                System.out.print(Integer.toHexString(state[i][j]));
//                if (j != state[0].length-1) { System.out.print(", "); }
//            }
//            System.out.println("]");
//        }
//        System.out.println();
//    }
//}
