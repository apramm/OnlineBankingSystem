package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// This is a class containing ArrayList of BankAccount
public class ListOfBank implements Writable {
    private static ArrayList<BankAccount> accounts;


    //EFFECTS: instantiates the account's list.
    public ListOfBank() {
        accounts = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds a new bank account to the list of bank accounts
    public static void addAccounts(BankAccount bankAccount) {
        accounts.add(bankAccount);
    }

    public static void doSomething(BankAccount bankAccount) {
        EventLog.getInstance().logEvent(new Event("Added " + bankAccount + " to Banking System"));
    }

    //getter
    public static ArrayList<BankAccount> getAccounts() {
        return accounts;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("accounts", accountsToJson());
        return json;
    }

    // EFFECTS: returns accounts in this bankacc as a JSON array
    private JSONArray accountsToJson() {
        JSONArray jsonArray = new JSONArray();
        accounts = ListOfBank.getAccounts();

        for (BankAccount ba : accounts) {
            jsonArray.put(ba.toJson());
        }
        return jsonArray;
    }


}


