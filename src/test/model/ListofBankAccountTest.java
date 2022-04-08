package model;

import model.exceptions.BankingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

// This is testing class for ListofBankAccount Class
public class ListofBankAccountTest {
    ArrayList<BankAccount> listofaccount = new ArrayList<>();
    private ListOfBank TestList;
    private final BankAccount ba1 = new BankAccount("Joe", 100, 0);
    private final BankAccount ba2 = new BankAccount("Joe", 0, 0);

    public ListofBankAccountTest() throws BankingException {
    }

    @BeforeEach
    void runBefore() {
        TestList = new ListOfBank();

        assertEquals(0, listofaccount.size());
        assertTrue(listofaccount.isEmpty());
    }

    @Test
    void addBankAccountTest() {
        ListOfBank.addAccounts(ba1);
        assertEquals(1, ListOfBank.getAccounts().size());
        ListOfBank.addAccounts(ba2);
        assertEquals(2, ListOfBank.getAccounts().size());
    }


}
