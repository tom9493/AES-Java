import java.util.Objects;

import static java.lang.System.exit;

public class Main {

    public static void main(String[] args) {
        int Nk = 0;
        int verbose = 0;
        char[] result;

        if (args[0] == null || args[1] == null) {
            System.out.println("Please provide a string with 16 characters, then a key with 16, 24, or 32 characters");
            System.out.println("Use '-e' or '-d' tags after inputs to denote either encryption or decryption");
            System.out.println("Add the '-v' tag at the end for round outputs (Optional)");
            exit(1);
        }

        if (args[0].length() != 16 || (args[1].length() != 16 && args[1].length() != 24 && args[1].length() != 32)) {
            System.out.println("Input included, but incorrect lengths");
            System.out.println("Please provide a string with 16 characters and a key with 16, 24, or 32 characters");
            System.out.println("Use '-e' or '-d' tags after inputs to denote either encryption or decryption");
            System.out.println("Add the '-v' tag at the end for round outputs (Optional)");
            exit(1);
        }
        else if (args[1].length() == 16) { Nk = 4; }
        else if (args[1].length() == 24) { Nk = 6; }
        else { Nk = 8; }

        if (args[2] == null || (!args[2].equals("-e") && !args[2].equals("-d"))) {
            System.out.println("Use '-e' or '-d' tags after string and key inputs to denote either encryption or decryption");
            exit(1);
        }

        if ((args[3].equals("-v"))) { verbose = 1; }

        char[] bytes = args[0].toCharArray();
        char[] key = args[1].toCharArray();
        AES aes = new AES(Nk, key);


        if (args[2].equals("-e")) {
            result = aes.encrypt(bytes, verbose);
            System.out.println("Result of encryption:");
            printBytes(result);
        }

        else {
            result = aes.decrypt(bytes, verbose);
            System.out.println("Result of decryption:");
            printBytes(result);
        }
    }

    public static void printBytes(char[] bytes) {
        for (char aByte : bytes) {
            System.out.print(Integer.toHexString(aByte) + " ");
        }
        System.out.println();
    }
}

// Practice values
//        char[] key = {0x2B,0x7e,0x15,0x16,0x28,0xae,0xd2,0xa6,0xab,0xf7,0x15,0x88,0x09,0xcf,0x4f,0x3c};
//        char[] bytes = {0x32,0x43,0xf6,0xa8,0x88,0x5a,0x30,0x8d,0x31,0x31,0x98,0xa2,0xe0,0x37,0x07,0x34};