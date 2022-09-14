#AES Implementation Using Java
This is a java implementation of the AES encryption/decryption algorithm. It encrypts a 128 bit string using a 128, 192, or 256 bit cipher key and a number of byte manipulating operations.

For clarity, 128, 192, and 256 bits map to 16, 24, and 32 characters. So the input string should be 16 characters and the cipher key can be any of the above.

Compile: "javac Main.java" 

Run:     "java Main <128 bit string> <128, 192, or 256 bit key> <-e, -d, or -ed to determine encrption, decryption, or both> <-v for round outputs (Optional)>"

You can also run the program through an IDE, placing the above command line arguments in the appropriate configuration field.

I used only the materials provided, and no additional materials.

I passed all the test cases in appendix C, meaning all round outputs were identical between my implementation and the provided round outputs in the appendix. 




