import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Decryptor {
    public static void decrypt(String algorithm, File file,
            IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {

        try {
            Scanner scanner = new Scanner(System.in);
            Scanner read = new Scanner(file);

            // Read the file content 
            String input = "";
            while (read.hasNextLine()) {
                input += read.nextLine();
            }

            // Ask the user for the key to decrypt the file
            System.out.println("Please enter a key to decrypt a file: ");

            // Read the key from the user
            String keyString = scanner.nextLine();

            // Convert the String key to a SecretKey object 
            byte[] decodedKey = Base64.getDecoder().decode(keyString);
            SecretKey key = new SecretKeySpec(decodedKey, "AES");

            // Decrypt the file using the key and the IV
            //source: https://www.baeldung.com/java-aes-encryption-decryption
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.DECRYPT_MODE, key, iv);
            // Decrypt the file content 
            byte[] plainText = cipher.doFinal(Base64.getDecoder().decode(input));
            String result = new String(plainText, StandardCharsets.UTF_8);
            // Write the decrypted content to a new file
            try {
                FileWriter write = new FileWriter("plaintext.txt");
                write.write(new String(result));
                write.close();
                System.out.println("File successfully decrypted. Decrypted text saved in plaintext.txt");
            } catch (IOException e) {
                System.out.println("Error writing to file");
            }
        } catch (Exception e) {
            System.out.println("Decryption failed. Invalid key.");
            decrypt(algorithm, file, iv);
        }

    }
}
