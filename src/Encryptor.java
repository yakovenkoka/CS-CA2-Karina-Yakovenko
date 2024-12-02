import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

public class Encryptor {
    public static void encrypt(String algorithm, File file, SecretKey key,
            IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {

        try {
            Scanner read = new Scanner(file);

            // Read the file content to encrypt
            String input = "";
            while (read.hasNextLine()) {
                input += read.nextLine();
            }

            //Display the encryption key to the user
            System.out.println("Key : " + Base64.getEncoder().encodeToString(key.getEncoded()));

            //Configure the cipher to encrypt the file
            //source: https://www.baeldung.com/java-aes-encryption-decryption
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);
            //Encrypt the file content
            byte[] cipherText = cipher.doFinal(input.getBytes());
            String result  = Base64.getEncoder().encodeToString(cipherText);
            //Write the encrypted content to a new file
            try{
                FileWriter write = new FileWriter("ciphertext.txt");
                write.write(result);
                write.close();
                System.out.println("File successfully encrypted");
            }catch (IOException e) {
                System.out.println("Error writing to file");
                encrypt(algorithm, file, key, iv);
            }
            
        } catch (Exception e) {
            System.out.println("Encryption failed");
            encrypt(algorithm, file, key, iv);
        }

    }
}