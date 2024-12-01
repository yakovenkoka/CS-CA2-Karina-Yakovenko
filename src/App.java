import java.util.InputMismatchException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        String[] menuOptions = {
            "Exit",
            "Encrypt a File",
            "Decrypt a File"
        };

        int menuChoice = -1;

        do {
            displayMenu(menuOptions, "--- Menu ---");
            try {
                menuChoice = getUserChoice(menuOptions.length - 1);
                switch (menuChoice) {
                    case 0:
                        System.out.println("Exiting the program.");
                        break;
                    case 1:
                        System.out.println("Enter the filename to encrypt:");
                        String encryptFilename = kb.next();
                        AESUtil.encryptFile(encryptFilename);
                        break;
                    case 2:
                        System.out.println("Enter the filename to decrypt:");
                        String decryptFilename = kb.next();
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

    public static void displayMenu(String[] menuOptions, String menuTitle) {
        System.out.println("\n" + menuTitle);
        System.out.println("Please select an option:");
        for (int i = 0; i < menuOptions.length; i++) {
            System.out.println(i + ". " + menuOptions[i]);
        }
    }

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
