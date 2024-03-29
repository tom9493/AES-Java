import static java.lang.System.exit;

public class Main {

    public static void main(String[] args) {
        int Nk = 0;
        boolean verbose = false;
        char[] result;

        if (args.length == 0) {
            System.out.println("Please provide a string with 16 characters, then a key with 16, 24, or 32 characters");
            System.out.println("Use '-e' or '-d' tags after inputs to denote either encryption or decryption. Use '-ed' for both");
            System.out.println("Add the '-v' tag at the end for round outputs (Optional)");
            exit(1);
        }
        if (args[0] == null || args[1] == null) {
            System.out.println("Please provide a string with 16 characters, then a key with 16, 24, or 32 characters");
            System.out.println("Use '-e' or '-d' tags after inputs to denote either encryption or decryption. Use '-ed' for both");
            System.out.println("Add the '-v' tag at the end for round outputs (Optional)");
            exit(1);
        }

        char[] chars = hex2CharArray(args[0]);
        char[] key = hex2CharArray(args[1]);

        if (chars.length != 16 || (key.length != 16 && key.length != 24 && key.length != 32)) {
            System.out.println("Input included, but incorrect lengths");
            System.out.println("Please provide a string with 16 characters and a key with 16, 24, or 32 characters");
            System.out.println("Use '-e' or '-d' tags after inputs to denote either encryption or decryption. Use '-ed' for both");
            System.out.println("Add the '-v' tag at the end for round outputs (Optional)");
            exit(1);
        }
        else if (key.length == 16) { Nk = 4; }
        else if (key.length == 24) { Nk = 6; }
        else { Nk = 8; }

        if (args[2] == null || (!args[2].equals("-e") && !args[2].equals("-d") && !args[2].equals("-ed"))) {
            System.out.println("Use '-e' or '-d' tags after string and key inputs to denote either encryption or decryption. Use '-ed' for both");
            exit(1);
        }

        if (args.length > 3) {
            if ((args[3].equals("-v"))) {
                verbose = true;
            }
        }

        AES aes = new AES(Nk, key);

        if (args[2].equals("-e")) {
            System.out.print("round[0].input\t\t"); printBytes(chars);
            result = aes.encrypt(chars, verbose);
        }

        else if (args[2].equals("-ed")) {
            System.out.print("round[0].input\t\t"); printBytes(chars);
            result = aes.encrypt(chars, verbose);
            System.out.println();
            System.out.print("round[0].iinput\t\t"); printBytes(result);
            result = aes.decrypt(result, verbose);
        }

        else {
            System.out.print("round[0].iinput\t\t"); printBytes(chars);
            result = aes.decrypt(chars, verbose);
        }
    }

    public static void printBytes(char[] bytes) {
        for (char aByte : bytes) {
            if (aByte <= 0x0f) {
                System.out.print("0" + Integer.toHexString(aByte));
            }
            else { System.out.print(Integer.toHexString(aByte)); }
        }
    }

    public static char[] hex2CharArray(String s) {
        if (s == null) { return null; }
        int length = s.length();
        if ((1 & length) != 0) { System.out.println("'" + s + "' has odd length. Exiting..."); exit(1); }
        length /= 2;
        char[] result = new char[length];
        for (int i = 0, indexSrc = 0; i < length; ++i) {
            int digit = Character.digit(s.charAt(indexSrc), 16);
            if (digit < 0) { System.out.println("'" + s + "' digit " + indexSrc + " is not sadecimal. Exiting..."); exit(1); }
            result[i] = (char) (digit << 4);
            ++indexSrc;
            digit = Character.digit(s.charAt(indexSrc), 16);
            if (digit < 0) { System.out.println("'" + s + "' digit " + indexSrc + " is not sadecimal. Exiting..."); exit(1); }
            result[i] |= (char) digit;
            ++indexSrc;
        }
        return result;
    }
}

// Practice values
//        char[] key = {0x00,0x01,0x02,0x03,0x04,0x05,0x06,0x07,0x08,0x09,0x0a,0x0b,0x0c,0x0d,0x0e,0x0f};
//        char[] bytes = {0x00,0x11,0x22,0x33,0x44,0x55,0x66,0x77,0x88,0x99,0xaa,0xbb,0xcc,0xdd,0xee,0xff};
