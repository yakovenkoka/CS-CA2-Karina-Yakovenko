import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class App {

    // Stores the IvParameterSpec for the encryption/decryption process to use the same Initialization Vector for both operations
    private static IvParameterSpec IV;

    public static void main(String[] args) throws FileNotFoundException {

        Scanner kb = new Scanner(System.in);
        String[] menuOptions = {
                "Exit",
                "Encrypt a File",
                "Decrypt a File"
        };

        int menuChoice = -1;

        // Main menu loop
        do {
            displayMenu(menuOptions, "--- Menu ---");
            try {
                menuChoice = getUserChoice(menuOptions.length - 1);
                switch (menuChoice) {
                    case 0:
                        System.out.println("Exiting the program.");
                        break;
                    case 1:
                        // Encrypt a file 
                        while (true) {
                            System.out.println(
                                    "Enter the filename to encrypt (or type 'back' to return to the main menu):");
                            String encryptFilename = kb.next();

                            // Return to the main menu if the user types 'back' 
                            if (encryptFilename.equals("back")) {
                                break;
                            }

                            File fileToEncrypt = new File(encryptFilename);

                            // Check if the file exists
                            if (!fileToEncrypt.exists()) {
                                System.out.println("Error: File not found.");
                                continue;
                            }

                            try {
                                // Generate AES encryption key (128 bits)
                                SecretKey key = AESUtil.generateKey(128);
                                // Generate Initialization Vector
                                IV = AESUtil.generateIv();

                                // Encrypt the file using the provided key and IV
                                Encryptor.encrypt("AES/CBC/PKCS5Padding", fileToEncrypt, key, IV);
                                System.out.println("File encrypted successfully.");
                            } catch (InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException
                                    | InvalidAlgorithmParameterException | BadPaddingException
                                    | IllegalBlockSizeException e) {
                                System.out.println("Encryption error: " + e.getMessage());
                            }
                            break;
                        }
                        break;

                    case 2:
                        // Decrypt a file
                        while (true) {
                            System.out.println("Enter the filename to decrypt (or type 'back' to return to the main menu):");
                            String decryptFilename = kb.next();

                            // Return to the main menu if the user types 'back'
                            if (decryptFilename.equals("back")) {
                                break;
                            }
                            File fileToDecrypt = new File(decryptFilename);

                            // Check if the file exists
                            if (!fileToDecrypt.exists()) {
                                System.out.println("Error: File not found.");
                                continue;
                            }

                            try {
                                // Decrypt the file using the provided IV
                                // The key is entered by the user during the decryption process 
                                Decryptor.decrypt("AES/CBC/PKCS5Padding", fileToDecrypt, IV);
                            } catch (InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException
                                    | InvalidAlgorithmParameterException | BadPaddingException
                                    | IllegalBlockSizeException e) {
                                System.out.println("Encryption error: " + e.getMessage());
                            }
                            break;
                        }
                        break;

                    default:
                        System.out.println("Invalid choice. Try again.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid option.");
                kb.next();
            }
        } while (menuChoice != 0);
    }

    // Display the menu options to the user 
    public static void displayMenu(String[] menuOptions, String menuTitle) {
        System.out.println("\n" + menuTitle);
        System.out.println("Please select an option:");
        for (int i = 0; i < menuOptions.length; i++) {
            System.out.println(i + ". " + menuOptions[i]);
        }
    }

    // Get the user's choice from the menu options 
    public static int getUserChoice(int numItems) {
        Scanner scanner = new Scanner(System.in);
        int choice = -1;
        while (true) {
            try {
                System.out.print("Select an option: ");
                choice = scanner.nextInt();
                if (choice >= 0 && choice <= numItems) {
                    break;
                } else {
                    System.out.println("Invalid choice. Please enter a number between 0 and " + numItems);
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }
        }
        return choice;
    }

}
