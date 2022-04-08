package model;

import model.exceptions.*;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// This is a BankAccount class containing Bank Account of different users having name, balance and rewardpoints
// INVARIANT: getBalance >= 0
public class BankAccount implements Writable {
    private static final int minBalance = 0;                 // Minimum Balance required in account
    //ratio of rewardpoints and pointsforcashback
    private static final ArrayList<BankAccount> accounts = new ArrayList<>();
    protected String name;                             // account owner name
    protected double rewardPoints = 0;                // reward points awarded to account
    protected int pointsforcashback = 1000;     // points needed to get cash-back reward
    protected int rewardpointsperdollar = 1;    // points earned for each dollar charged to card
    protected int cashbackReward = 5;           // reward in dollars
    protected double rewardPerpointneeded = rewardPoints / pointsforcashback;
    double balance;                                    // balance of account

    /*
     * EFFECTS: name on account is set to accountName; rewardPoints is double
     *           assigned to a new account created ; if initialBalance >= 0 then
     *           balance on account is set to initialBalance and reward points set to 0,
     *           otherwise throws NegativeAmountException; if amountName is empty then throws
     *           EmptyNameException; if initialBalance > 1000000 then throws MoneyLaunderingException
     */
    public BankAccount(String accountName, double initialBalance, double initialRewardPoints) throws BankingException {
        if (accountName.isEmpty()) {
            throw new EmptyNameException();
        }
        if (initialBalance < 0) {
            throw new NegativeAmountException();

        }
        if (initialBalance >= 1000000) {
            throw new MoneyLaunderingException();
        }
        name = accountName;
        this.rewardPoints = initialRewardPoints;
        this.balance = initialBalance;
    }

    /*
     * MODIFIES: balance
     * EFFECTS: if money < 0  then throws NegativeAmountException;
     *           if amount > 1000000 then throws MoneyLaunderingException;
     *          otherwise, money is added to balance and updated, balance is returned.
     */
    public double credit(double money) throws BankingException {
        if (money < 0) {
            throw new NegativeAmountException();
        }
        if (money > 1000000) {
            throw new MoneyLaunderingException();
        }

        this.balance += money;
        EventLog.getInstance().logEvent(new Event("Credited BankAccount with " + money));
        return this.balance;
    }

    /*
     * MODIFIES: balance
     * EFFECTS: if the money > balance throws InsufficientFundsException;
     *          if money < 0 throws NegativeAmountException;
     *          otherwise deducts the money from balace and returns it.
     */
    public double debit(double money) throws BankingException {
        if (this.balance < money) {
            throw new InsufficientFundsException();
        }
        if (money < 0) {
            throw new NegativeAmountException();
        } else {
            this.rewardPoints += money * rewardpointsperdollar;
            while (rewardPoints >= pointsforcashback) {
                this.balance += cashbackReward;
                this.rewardPoints -= pointsforcashback;
            }
            this.balance -= money;
            EventLog.getInstance().logEvent(new Event("Debited Account with " + money));
            return balance;
        }

    }

    /*
     * MODIFIES: balance
     * EFFECTS: if negative money then throws NegativeException;
     *          if money more than account's balance then throws InsufficientException
     *          otherwise, money is debited from account and updated
     * 		    balance is returned.
     */
    public double transfer(double money) throws BankingException {
        if (this.balance < money) {
            throw new InsufficientFundsException();
        }
        if (money < 0) {
            throw new NegativeAmountException();
        } else {
            this.balance -= money;
            return balance;
        }
    }


    // EFFECTS: returns a string representation of account
    @Override
    public String toString() {
//        String balanceStr = String.format("%.2f", balance);  // get balance to 2 decimal places as a string
//        return "[ name = " + name + ", "
//                + "balance = $" + balanceStr + "reward points =" + rewardPoints + "]";
        return name;
    }


    //getter method
    public String getName() {
        return name;
    }

    //getter method
    public double getBalance() {
        return balance;
    }

    //getter method
    public double getRewardPoints() {
        return rewardPoints;
    }

    // toJson() puts in the account representation.
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();

        json.put("reward points", rewardPoints);
        json.put("balance", balance);
        json.put("name", name);

        return json;
    }


}

