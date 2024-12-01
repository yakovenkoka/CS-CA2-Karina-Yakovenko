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
            String input = "";
            while (read.hasNextLine()) {
                input += read.nextLine();
            }

            System.out.println("Key : " + Base64.getEncoder().encodeToString(key.getEncoded()));

            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);
            byte[] cipherText = cipher.doFinal(input.getBytes());
            String result  = Base64.getEncoder().encodeToString(cipherText);
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