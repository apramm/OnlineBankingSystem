package persistence;

import model.BankAccount;
import model.ListOfBank;
import model.exceptions.BankingException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

//This is a class for JsonWriterTest extending JsonTest.
public class JsonWriterTest extends JsonTest {
    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.


    @Test
    void testWriterInvalidFile() {
        try {
            ListOfBank listOfBank = new ListOfBank();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            ListOfBank listOfBank = new ListOfBank();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyWorkroom.json");
            writer.open();
            writer.write(listOfBank);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyWorkroom.json");
            listOfBank = reader.read();
            assertEquals(new ArrayList<>(), ListOfBank.getAccounts());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        } catch (BankingException e) {
            fail("Exception not expected");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            ListOfBank listOfBank = new ListOfBank();
            ListOfBank.addAccounts(new BankAccount("dhrubo", 1000, 10));
            ListOfBank.addAccounts(new BankAccount("batman", 100, 20));
            JsonWriter writer = new JsonWriter("./data/testWriter.json");
            writer.open();
            writer.write(listOfBank);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriter.json");
            listOfBank = reader.read();
            assertEquals(2, ListOfBank.getAccounts().size());
            ListOfBank.addAccounts(new BankAccount("spiderman", 100, 5));
            ArrayList<BankAccount> accounts = ListOfBank.getAccounts();
            assertEquals(3, accounts.size());

            checkThingy("dhrubo", 1000, 10, accounts.get(0));
            checkThingy("batman", 100, 20, accounts.get(1));
            checkThingy("spiderman", 100, 5, accounts.get(2));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        } catch (BankingException e) {
            fail("Exception should not have been thrown");
        }
    }
}

