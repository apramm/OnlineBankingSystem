package persistence;

import model.BankAccount;

import static org.junit.jupiter.api.Assertions.assertEquals;

// This is a class for JsonTest
public class JsonTest {
    protected void checkThingy(String name, int balance, int rewardPoints, BankAccount ba) {
        assertEquals(name, ba.getName());
        assertEquals(balance, ba.getBalance());
        assertEquals(rewardPoints, ba.getRewardPoints());
    }
}

