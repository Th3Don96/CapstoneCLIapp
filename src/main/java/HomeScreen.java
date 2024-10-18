import com.pluralsight.Account;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.regex.Pattern;

public class HomeScreen {
    public static void main(String[] args) {
        System.out.println("Welcome");
        Scanner scanner = new Scanner(System.in);
        System.out.println("What would you like to do today? \n(1)Add Deposit\n(2)Make Payment(Debit)\n(3)Transactions\n(4)Exit\n");
        System.out.print("Please select an option: ");
        String option = scanner.nextLine().trim();

        switch (option) {
            //Add Deposit
            case "1": {
                Scanner scannerSet1 = new Scanner(System.in);
                System.out.println("What would you like to do today? \n(1)Checking Deposit\n(2)Savings Deposit\n(3)Back\n");
                System.out.print("Please select an option: ");
                String optionSet1 = scannerSet1.nextLine().trim();
                switch (optionSet1) {
                    //Checking Deposit
                    case "1": {

                        Scanner scanner1 = new Scanner(System.in);

                        double amount = Double.parseDouble(scanner1.nextLine());
                        Account myAccount = new Account(0.00f);
                        myAccount.deposit(amount);
                        System.out.println("Current balance: " + myAccount.getBalance());
                        try {
                            BufferedWriter deposit = new BufferedWriter(new FileWriter("src/main/resource/transactions.capstone.csv"));
                            deposit.newLine();
                            LocalDateTime today = LocalDateTime.now();
                            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd|HH:mm:ss");
                            String formattedDate = today.format(fmt);
                            deposit.write(formattedDate + "|Online Deposit|CapStoneBanking|" + String.valueOf(myAccount.getBalance()) + "\n");
                            System.out.print("Deposit Confirmed, Thank you!\n");
                        }
                        catch(IOException e) {
                            e.printStackTrace();
                        }
                    }//Savings
                        case "2": {
                            Scanner scanner1 = new Scanner(System.in);
                            double amount = Double.parseDouble(scanner1.nextLine());
                            Account myAccount = new Account(0.00f);
                            myAccount.deposit(amount);
                            System.out.println("Current balance: " + myAccount.getBalance());
                            try {
                                BufferedWriter deposit = new BufferedWriter(new FileWriter("src/main/resource/savings.capstone.csv"));
                                deposit.newLine();
                                LocalDateTime today = LocalDateTime.now();
                                DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd|HH:mm:ss");
                                String formattedDate = today.format(fmt);
                                deposit.write(formattedDate + "|Online Deposit|CapStoneBanking|" + String.valueOf(myAccount.getBalance()) + "\n");
                                System.out.print("Savings Deposit Confirmed, Thank you!\n");
                            }

                            catch(IOException e) {
                                e.printStackTrace();

                        }break;
                    }
                    //return to home
                    case "3": {
                        break;

                    }
                }break;
            }
                //Make Payment (debit)
            case "2": {
                Scanner scannerSet2 = new Scanner(System.in);
                System.out.println("What would you like to do today? \n(1)Debit Card Payment\n(2)Outstanding Balance\n(3)Back)\n");
                System.out.print("Please select an option: ");
                String optionSet2 = scannerSet2.nextLine().trim();
                switch (optionSet2) {
                    // Add Debit Card
                    case "1": {
                        try (BufferedWriter cardInfo = new BufferedWriter(new FileWriter("src/main/resources/savings.capstone.csv", true))) {
                            cardInfo.newLine();
                            Account myAccount = new Account(0.00f);
                            Scanner scannerSet21 = new Scanner(System.in);
                            System.out.println("Please provide Payment information.");
                            System.out.print("Debit Card Number: ");
                            String cardNum = (scannerSet21.nextLine());
                            System.out.print("Expiration Date MM-YY: ");
                            String cardExp = (scannerSet21.nextLine());
                            System.out.print("Card Verification Code (CVC)");
                            String cardCvc = (scannerSet21.nextLine());
                            System.out.print("First Name: ");
                            String firstName = (scannerSet21.nextLine());
                            System.out.print("Last Name: ");
                            String lastName = (scannerSet21.nextLine());
                            String customerInfo = "Customer Card Info- " + firstName + "," + lastName + "\nCard: " + cardNum +
                                    "\nExp: " + cardExp + "  Cvc: " + cardCvc;
                            String filePath = "src/main/resources/transactions.capstone.csv";
                            double totalBalance = 0.0;
                            try
                                    (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                                String line;
                                while ((line = reader.readLine()) != null) {
                                    if (line.trim().isEmpty() || line.startsWith("date")) {
                                        continue;
                                    }
                                    String[] statementSplit = line.split(Pattern.quote("|"));
                                    if (statementSplit.length != 5) {
                                        System.err.println("Skipping invalid line: " + line);
                                        continue;
                                    }
                                    double amount = Double.parseDouble(statementSplit[4]);
                                    totalBalance += amount;
                                }
                                System.out.printf("Total Available Balance: $%.2f%n\n", totalBalance);

                                System.out.println("Would you like to make a Payment?\n(1)Yes\n(2)No\n");
                                System.out.print("Please select an option: ");
                                String optionSet21 = scannerSet2.nextLine().trim();
                                switch (optionSet21) {
                                    case ("1"): {
                                        System.out.println("How much will you like to pay?");
                                        System.out.println("Payment Amount:");
                                        LocalDateTime today = LocalDateTime.now();
                                        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd|HH:mm:ss");
                                        String formattedDate = today.format(fmt);
                                        cardInfo.write(formattedDate + "|Online Payment|CapStoneBanking|" + String.valueOf(myAccount.getBalance()) + "\n" + "Customer Card Info- " + customerInfo + "\n");
                                        System.out.print("Payment Confirmed, Thank you!\n");
                                    }
                                    case ("2"): {
                                        break;
                                    }
                                }
                            } catch (FileNotFoundException e) {
                                throw new RuntimeException(e);
                            }
                        } catch (IOException e) {
                            System.out.println("Error updating Account: " + e.getStackTrace());
                        }break;
                    }//Outstanding Balance
                    case "2": {
                        // Make payment
                        String filePath = "src/main/resources/transactions.capstone.csv";
                        double totalBalance = -0.0;
                        try
                                (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                            String line;
                            while ((line = reader.readLine()) != null) {
                                if (line.trim().isEmpty() || line.startsWith("date")) {
                                    continue;
                                }
                                String[] statementSplit = line.split(Pattern.quote("|"));
                                if (statementSplit.length != 5) {
                                    System.err.println("Skipping invalid line: " + line);
                                    continue;
                                }
                                double amount = Double.parseDouble(statementSplit[4]);
                                totalBalance += amount;
                            }
                            System.out.printf("Total Balance: $%.2f%n\n", totalBalance);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }break;}
            }
            // (Ledger) View Transactions
            case "3": {
                Scanner scannerSet3 = new Scanner(System.in);
                System.out.println("What would you like to do today? \n(1)All Transactions\n(2)Sort Transactions)\n(3)Payments\n(4)Back\n");
                System.out.print("Please select an option: ");
                String optionSet3 = scannerSet3.nextLine().trim();
                switch (optionSet3) {
                    // All Transactions
                    case "1": {
                        try {
                            FileReader fileReader = new FileReader("src/main/resources/transactions.capstone.csv");
                            BufferedReader bufReader = new BufferedReader(fileReader);
                            String input;
                            Transactions[] statements = new Transactions[250];
                            int balanceCount = 0;

                            while ((input = bufReader.readLine()) != null) {
                                if (input.startsWith("date")) {
                                    continue;
                                }
                                String[] lineSplit = input.split(Pattern.quote("|"));
                                statements[balanceCount++] = new Transactions(lineSplit[0], lineSplit[1], lineSplit[2], lineSplit[3], Double.parseDouble(lineSplit[4]));
                            }
                            for (Transactions transaction : statements) {
                                System.out.printf("""
                                        Date: %s ,Time: %s
                                        Purchase description: %s,From %s
                                        Amount: $%.2f
                                        %n
                                        """, transaction.getDate(), transaction.getTime(), transaction.getDescription(), transaction.getVendor(), transaction.getAmount());
                                    }
                                    bufReader.close();
                                } catch (IOException e) {
                                    System.out.println("ERROR:  An unexpected error occurred");
                                    e.getStackTrace();
                                }

                            }
                            case "2": {
                                //Sort Transactions by Month,Date,Year & Vendor
                                System.out.println("Coming Soon....");
                                break;
                            }
                            case "3": {
                                System.out.println("Coming Soon....");
                                break;
                            }
                        }break;
                    }

                    // Exit Application
                    case "4": {
                        System.out.println("Thank you, Goodbye");
                        System.exit(0);

                    }
                    // Invalid User Input
                    default:
                        System.out.println("Invalid option. Please try again.\n");
                        break;
                   }
            }
        }

