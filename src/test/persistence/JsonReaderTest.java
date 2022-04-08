package persistence;

import model.BankAccount;
import model.ListOfBank;
import model.exceptions.BankingException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// This is a class for JsonReaderTest extending JsonTest
public class JsonReaderTest extends JsonTest {
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            ListOfBank listOfBank = reader.read();
            fail("IOException expected");
        } catch (IOException | BankingException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyWorkRoom.json");
        try {
            ListOfBank listOfBank = reader.read();
            assertEquals(new ArrayList<>(), ListOfBank.getAccounts());
            assertEquals(0, ListOfBank.getAccounts().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        } catch (BankingException e) {
            fail("Caught Banking Exception");
        }
    }

    @Test
    void testReader() {
        JsonReader reader = new JsonReader("./data/testReader.json");
        try {
            ListOfBank listOfBank = reader.read();
            List<BankAccount> accounts = ListOfBank.getAccounts();
            assertEquals(3, ListOfBank.getAccounts().size());
            checkThingy("kashish", 10000, 0, accounts.get(0));
            checkThingy("kashish", 100, 300, accounts.get(1));
            checkThingy("kashish", 90, 100, accounts.get(2));
        } catch (IOException e) {
            fail("Couldn't read from file");
        } catch (BankingException e) {
            fail("Caught Banking Exception");
        }
    }
}
