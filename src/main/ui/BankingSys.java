package ui;

import model.BankAccount;
import model.ListOfBank;
import model.exceptions.BankingException;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

// This is a Banking system ui
public class BankingSys {
    // choose type of account here
    // mix with bank account to
    private static final String JSON_STORE = "./data/bankacc.json";
    private final JsonWriter jsonWriter;
    private final JsonReader jsonReader;
    private BankAccount saving;
    private BankAccount business;
    private BankAccount investment;
    private Scanner input;
    private ListOfBank listofaccounts = new ListOfBank();
    private ArrayList<BankAccount> accounts = ListOfBank.getAccounts();


    //EFFECTS: runs the Online Banking Model
    public BankingSys() throws BankingException {
        input = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runBank();
    }

    // MODIFIES: this
    // EFFECTS: processes and acts according to user input
    private void runBank() {
        boolean continues = true;
        String command = null;
        displayIntro();
        initialise();

        while (continues) {
            displayMenu1();
            command = input.next();
            command = command.toLowerCase();

            while (command.equals("q")) {
                displayMenu2();
                command = input.next();
                command = command.toLowerCase();
            }

            if (command.equals("y")) {
                continues = false;
            } else if (command.equals("n")) {
                continues = true;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\n ThankYou for using our model, See You Again!");
    }

    //REQUIRES: Customer needs to create the account first before using any functions
    // MODIFIES: this
    // EFFECTS: processes user command to perform functions
    private void processCommand(String command) {
        if (command.equals("a")) {
            addAccount();
        } else if (command.equals("c")) {
            try {
                addCredit();
            } catch (BankingException e) {
                e.printStackTrace();
            }
        } else if (command.equals("v")) {
            doAccounts();
        } else if (command.equals("s")) {
            try {
                saveWorkRoom();
            } catch (BankingException e) {
                System.out.println("A BankingException Occured!");
            }
        } else if (command.equals("l")) {
            loadWorkRoom();
        } else {
            System.out.println("Please Choose one of the above options");
        }

    }


    // MODIFIES: this
    // EFFECTS: initializes Scanner for BankAccount
    private void initialise() {
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    //EFFECTS: displays the introduction information with requires and functionality
    private void displayIntro() {

        System.out.println("\n WELCOME to Online Banking System!!!");
        System.out.println("\n You are 'REQUIRED' to create an account before accessing any functions!! ");
        System.out.println("Initial Balance goes in savings account and other accounts are created with 0$ Balance");
        System.out.println("\n Reward Point System is as follows: ");
        System.out.println(" 1 Reward Point awarded for each dollar spent!");
        System.out.println(" 5 dollar automatic cashback after 1000 Reward Points ");
        System.out.println("\n Enjoy the variety of offers after creating account :");
    }

    // EFFECTS: displays account creation options to user
    private void displayMenu1() {
        System.out.println("\tPRESS (a) - CREATE new account");
        System.out.println("\tPRESS (c) - credit");
        System.out.println("\tPRESS (d) - debit");
        System.out.println("\tPRESS (t) - transfer between accounts");
        System.out.println("\tPRESS (v) - View List of Accounts");
        System.out.println("\tPRESS (q) - quit?");
    }

    // EFFECTS: displays options to remind after quit option
    private void displayMenu2() {
        System.out.println("\tPRESS (s) - save work room to file");
        System.out.println("\tPRESS (l) -  load work room from file");
        System.out.println("\tPRESS (y) -  YES!! quit");
        System.out.println("\tPRESS (n) -  NO! return back");
    }


    // MODIFIES: this
    // EFFECTS: adds a new BankAccount of specified name, balance, gives 0 rewardpoints
    //           if balance is < 0 or accountName is empty gives BankingException, account not created
    //           otherwise, prints balance and reward points for savings account
    void addAccount() {

        System.out.print("Enter New Bank Account information");
        System.out.print("\n Enter Your Name :");
        String name = input.next();
        System.out.print("Enter the initial balance of account : ");
        double initialbalance = input.nextDouble();
        try {
            saving = new BankAccount(name, initialbalance, 0);
            business = new BankAccount(name, 0, 0);
            investment = new BankAccount(name, 0, 0);
        } catch (BankingException e) {
            System.out.println("Not good info for bank account!!");
        }

        printBalance(saving);
        printRewardPoints(saving);

        ListOfBank.addAccounts(saving);
        ListOfBank.addAccounts(business);
        ListOfBank.addAccounts(investment);
        accounts = ListOfBank.getAccounts();
    }

    // MODIFIES: this
    // EFFECTS: if amount < 0 gives NegativeAmountException
    //          otherwise, credits the account with specified amount
    private void addCredit() throws BankingException {

        System.out.print("Enter credit amount : $");
        double amount = input.nextDouble();
        BankAccount selected = selectAccount();

        selected.credit(amount);


        printBalance(selected);
        printRewardPoints(selected);
    }

    // MODIFIES: this
    // EFFECTS:  if amount < 0 gives NegativeAmountException
    //          if amount > balance gives NegativeAmountException
    //          otherwise conducts debit transaction with cashback if meets condition
    private void deductDebit() throws BankingException {
        BankAccount selected = selectAccount();
        System.out.print("Enter debit amount: $");
        double amount = input.nextDouble();

        try {
            selected.debit(amount);
        } catch (BankingException e) {
            System.out.println("NOT ACCEPTED!! Provide a Valid Debit Amount");
        }

        printBalance(selected);
        printRewardPoints(selected);
    }

    // MODIFIES: this
    // EFFECTS: if amount < 0 gives NegativeAmountException
    //          if amount > balance gives NegativeAmountException
    //          otherwise, conducts a transfer transaction between types of accounts
    private void doTransfer() throws BankingException {
        System.out.println("\nTransfer from?");
        BankAccount source = selectAccount();
        System.out.println("Transfer to?");
        BankAccount destination = selectAccount();

        System.out.print("Enter amount to transfer: $");
        double amount = input.nextDouble();

        if (amount < 0.0) {
            System.out.println("Cannot transfer negative amount...\n");
        } else if (source.getBalance() < amount) {
            System.out.println("Insufficient balance on source account...\n");
        } else {
            try {
                source.transfer(amount);
                destination.credit(amount);
            } catch (BankingException e) {
                System.out.println("There's some banking exception!!");
            }
        }

        System.out.print("Source ");

        printBalance(source);
        System.out.print("Destination ");
        printBalance(destination);

        printRewardPoints(source);
    }

    //EFFECTS: prints and iterate over all the bank account type in list
    private void doAccounts() {
        this.accounts = ListOfBank.getAccounts();
        for (BankAccount ba : this.accounts) {
            System.out.println(ba);
        }
        System.out.println("These accounts exist in the list");


    }

    // EFFECTS: prompts user to select saving,business or investment account and returns it
    private BankAccount selectAccount() throws BankingException {
        String selection = "";  // force entry into loop
        BankAccount ba = new BankAccount("aah", 0, 0);


        while (!(selection.equals("s") || selection.equals("b") || selection.equals("i"))) {
            System.out.println("s for saving");
            System.out.println("b for business");
            System.out.println("i for investment");
            selection = input.next();
            selection = selection.toLowerCase();
        }

        if (selection.equals("s")) {
            return saving;
        } else if (selection.equals("b")) {
            return business;
        } else if (selection.equals("i")) {
            return investment;
        } else {
            return ba;
        }
    }

    // EFFECTS: saves the workroom to file
    private void saveWorkRoom() throws BankingException {
        try {
            jsonWriter.open();
            jsonWriter.write(listofaccounts);
            jsonWriter.close();
            System.out.println("Saved" + accounts + " to " + JSON_STORE);
        } catch (FileNotFoundException | BankingException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void loadWorkRoom() {
        try {
            listofaccounts = jsonReader.read();
            System.out.println("Loaded " + this.listofaccounts + " from " + JSON_STORE);
        } catch (IOException | BankingException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }


    // EFFECTS: prints balance of account to the screen
    private void printBalance(BankAccount selected) {
        System.out.printf("Balance in account is: $%.2f\n", selected.getBalance());
    }

    //EFFECTS: prints reward points of the account to screen
    private void printRewardPoints(BankAccount selected) {
        System.out.printf("Reward points in account is: %.2f\n", selected.getRewardPoints());
    }

}


