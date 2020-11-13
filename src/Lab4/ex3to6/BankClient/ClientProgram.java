package Lab4.ex3to6.BankClient;

import java.util.Scanner;

public class ClientProgram {
    public static void main(String[] args) {
        System.out.println("Enter your customer ID:");

        try {
            Scanner in = new Scanner(System.in);
            int customerId = Integer.parseInt(in.nextLine());

            try (Client client = new Client(customerId)) {
                System.out.println("Logged in successfully.");

                while (true) {


                    var accountNumbers = client.getAccountNumbers();
                    System.out.println("Your accounts:");
                    for (int account : accountNumbers)
                        System.out.printf("  Account %5d: balance %10d GBP%n", account, client.getBalance(account));

                    System.out.println("\nPlease select an option:");
                    System.out.println("A: See details of all your accounts (type 'A')");
                    System.out.println("B: Transfer money between accounts (type 'B')");
                    System.out.println("C: Create a new account ('C')");

                    System.out.println("\nPlease enter your choice");

                    switch (in.nextLine().toLowerCase().charAt(0)){
                        case 'a':
                            break;
                        case 'b':
                            transfer(client,in);
                            break;
                        case 'c':
                            client.makeAccount();
                            break;
                        default:
                            break;
                    }
                    /*
                    System.out.println("Enter the account number to transfer from or -1 to print the account list:");
                    int fromAccount = Integer.parseInt(in.nextLine());
                    if (fromAccount < 0)
                        continue;

                    System.out.println("Enter the account number to transfer to (this could be someone else's account):");
                    int toAccount = Integer.parseInt(in.nextLine());

                    System.out.println("Enter the amount to be transferred:");
                    int amount = Integer.parseInt(in.nextLine());

                    client.transfer(fromAccount, toAccount, amount);

                     */
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    private static void transfer(Client client, Scanner in) throws Exception{
        System.out.println("Enter the account number to transfer from:");
        int fromAccount = Integer.parseInt(in.nextLine());

        System.out.println("Enter the account number to transfer to (this could be someone else's account):");
        int toAccount = Integer.parseInt(in.nextLine());

        System.out.println("Enter the amount to be transferred:");
        int amount = Integer.parseInt(in.nextLine());

        client.transfer(fromAccount, toAccount, amount);
    }
}
