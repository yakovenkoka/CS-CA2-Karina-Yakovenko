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
            String input = "";
            while (read.hasNextLine()) {
                input += read.nextLine();
            }
            System.out.println("Please enter a key to decrypt a file: ");

            String keyString = scanner.nextLine();

            byte[] decodedKey = Base64.getDecoder().decode(keyString);
            SecretKey key = new SecretKeySpec(decodedKey, "AES");

            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.DECRYPT_MODE, key, iv);
            byte[] plainText = cipher.doFinal(Base64.getDecoder().decode(input));
            String result = new String(plainText, StandardCharsets.UTF_8);

            try {
                FileWriter write = new FileWriter("plaintext.txt");
                write.write(new String(result));
                write.close();
                System.out.println("File successfully decrypted. Decrypted text saved in plaintext.txt");
            } catch (IOException e) {
                System.out.println("Error writing to file");
            }
        } catch (Exception e) {
            System.out.println("Decryption failed");
            decrypt(algorithm, file, iv);
        }

    }
}
