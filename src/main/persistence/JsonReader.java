package persistence;

import model.BankAccount;
import model.ListOfBank;
import model.exceptions.BankingException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;


// Represents a JsonReader that reads BankAccounts from JSON data stored in file
public class JsonReader {

    private final String accountInfo;

    // EFFECTS: constructs reader to read from bankacc file
    public JsonReader(String accountInfo) {
        this.accountInfo = accountInfo;
    }

    // EFFECTS: reads accountInfo from file and returns it
    //          throws IOException and BankingException.
    public ListOfBank read() throws IOException, BankingException {
        String jsonData = readFile(accountInfo);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseWorkRoom(jsonObject);
    }


    // EFFECTS: reads accountInfo file as string and returns it
    //          throws IOException.
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

    // EFFECTS: parses ListofBank from JSON object and returns it
    private ListOfBank parseWorkRoom(JSONObject jsonObject) throws BankingException {
        ListOfBank listOfBank = new ListOfBank();
        addAccounts(listOfBank, jsonObject);
        return listOfBank;
    }

    // MODIFIES: listOfBank
    // EFFECTS: parses accounts from JSON object and adds them to ListofBank
    private void addAccounts(ListOfBank listOfBank, JSONObject jsonObject) throws BankingException {
        JSONArray jsonArray = jsonObject.getJSONArray("accounts");
        for (Object json : jsonArray) {
            JSONObject nextAccount = (JSONObject) json;
            addAccount(listOfBank, nextAccount);
        }
    }

    // MODIFIES: listOfBank
    // EFFECTS: parses bankaccount from JSON object and adds it to ListofBank
    private void addAccount(ListOfBank listOfBank, JSONObject jsonObject) throws BankingException {
        String name = jsonObject.getString("name");
        double balance = jsonObject.getInt("balance");
        int rewardPoints = jsonObject.getInt("reward points");
        BankAccount account = new BankAccount(name, balance, rewardPoints);
        ListOfBank.addAccounts(account);
    }

}

