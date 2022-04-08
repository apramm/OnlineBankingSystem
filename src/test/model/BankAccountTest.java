package model;

import model.exceptions.BankingException;
import model.exceptions.EmptyNameException;
import model.exceptions.MoneyLaunderingException;
import model.exceptions.NegativeAmountException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// This is a test class for BankAccount Class
public class BankAccountTest {
    private BankAccount tester1;
    private BankAccount tester2;
    private final int pointneeded = tester1.pointsforcashback;
    private final int cashback = tester1.cashbackReward;
    private final int rewardperdebit = tester1.rewardpointsperdollar;
    private final double rewardbypointneeded = tester1.rewardPerpointneeded;

    {
        try {
            tester1 = new BankAccount("Harry", 8000, 0);
        } catch (BankingException e) {
            fail("Caught BankingException - expected nothing");
        }
    }

    @BeforeEach
    void runBefore() {
        try {
            tester1 = new BankAccount("Harry", 8000, 0);
        } catch (BankingException e) {
            fail("Caught BankingException - expected nothing");
        }
        try {
            tester2 = new BankAccount("Ron", 250, 0);
        } catch (BankingException e) {
            fail("Caught BankingException - expected nothing");
        }
    }


    @Test
    void testConstructor() {
        assertEquals("Harry", tester1.getName());
        assertEquals(8000, tester1.getBalance());
        assertEquals(0, tester1.getRewardPoints());

        assertEquals("Ron", tester2.getName());
        assertEquals(250, tester2.getBalance());
        assertEquals(0, tester2.getRewardPoints());

    }

    @Test
    void emptyNameTest() {
        try {
            BankAccount emptyTester = new BankAccount("", 1000, 0);
            fail("emptyNameException expected");
        } catch (EmptyNameException e) {
            //pass
        } catch (BankingException e) {
            //pass
        }
    }

    @Test
    void negativeAmountTest() {
        try {
            BankAccount negativeTester = new BankAccount("dhrubo", -1, 0);
            fail("negativeAmountException expected");
        } catch (NegativeAmountException e) {
            //pass
        } catch (BankingException e) {
            //pass
        }
    }

    @Test
    void moneyLaunderingTest() {
        try {
            BankAccount moneyLaunderTest = new BankAccount("oi", 1000000000, 0);
            fail("MoneyLaunderingException expected");
        } catch (MoneyLaunderingException e) {
            //pass
        } catch (BankingException e) {
            //pass
        }
    }


    @Test
    void testCredit() {
        try {
            tester1.credit(200);
        } catch (BankingException e) {
            fail("Caught BankingException - expected nothing");
        }
        try {
            tester2.credit(10000);
        } catch (BankingException e) {
            fail("Caught BankingException - expected nothing");
        }

        assertEquals(8200, tester1.getBalance());
        assertEquals(10250, tester2.getBalance());
    }

    @Test
    void testmultipleCredits() {
        try {
            tester1.credit(200);
        } catch (BankingException e) {
            fail("Caught NegativeAmountException - expected nothing");
        }
        try {
            tester1.credit(1000);
        } catch (BankingException e) {
            fail("Caught NegativeAmountException - expected nothing");
        }
        try {
            tester2.credit(1000);
        } catch (BankingException e) {
            fail("Caught NegativeAmountException - expected nothing");
        }
        try {
            tester2.credit(150);
        } catch (BankingException e) {
            fail("Caught NegativeAmountException - expected nothing");
        }

        assertEquals(9200, tester1.getBalance());
        assertEquals(1400, tester2.getBalance());
    }

    @Test
    void testfailcredit() {
        try {
            tester1.credit(-100);
            fail("Did Not Catch BankingException - expected to do so");
        } catch (NegativeAmountException e) {
            //expected
        } catch (BankingException e) {
            //expected
        }
        try {
            tester2.credit(1000001);
            fail("Did Not Catch BankingException - expected to do so");
        } catch (MoneyLaunderingException e) {
            //expected
        } catch (BankingException e) {
            //expected
        }

        assertEquals(8000, tester1.getBalance());
        assertEquals(250, tester2.getBalance());
    }


    @Test
    void testfaildebit() {
        try {
            tester1.debit(8001);
            fail("Did Not Catch BankingException - expected to do so");
        } catch (BankingException e) {
            //expected
        }
        try {
            tester2.debit(300);
            fail("Did Not Catch BankingException - expected to do so");
        } catch (BankingException e) {
            //expected
        }
        try {
            tester2.debit(-3);
            fail("Did Not Catch BankingException - expected to do so");
        } catch (BankingException e) {
            //expected
        }

        assertEquals(8000, tester1.getBalance());
        assertEquals(250, tester2.getBalance());
    }

    @Test
    void balanceafterdebit() {
        try {
            tester1.credit(pointneeded / rewardperdebit);
        } catch (BankingException e) {
            fail("Caught BankingException - expected nothing");
        }
        try {
            tester1.debit(pointneeded / rewardperdebit);
        } catch (BankingException e) {
            fail("Caught BankingException - expected nothing");
        }
        try {
            tester2.credit(10 * (pointneeded / rewardperdebit));
        } catch (BankingException e) {
            fail("Caught BankingException - expected nothing");
        }
        try {
            tester2.debit(10 * (pointneeded / rewardperdebit));
        } catch (BankingException e) {
            fail("Caught BankingException - expected nothing");
        }

        // tester1 : remains with initial(8000) + cashback
        assertEquals(8000 + cashback, tester1.getBalance());
        // tester2 : remains with initial (150) + 10*cashback
        assertEquals(250 + (10 * cashback), tester2.getBalance());
    }

    @Test
    void rewardafterdebit() {
        try {
            tester1.debit(1200);
        } catch (BankingException e) {
            fail("Caught BankingException - expected nothing");
        }
        assertEquals(1200 * rewardperdebit - pointneeded, tester1.getRewardPoints());
    }

    @Test
    void rewardafterdebitwithoutcasback() {
        try {
            tester2.credit(800);
        } catch (BankingException e) {
            fail("Caught NegativeAmountException - expected nothing");
        }
        try {
            tester2.debit(800);
        } catch (BankingException e) {
            fail("Caught BankingException - expected nothing");
        }
        assertEquals(800, tester2.getRewardPoints());
    }

    @Test
    void balanceaftermultipledebit() {
        try {
            tester1.credit(2 * pointneeded / rewardperdebit);
        } catch (BankingException e) {
            fail("Caught NegativeAmountException - expected nothing");
        }
        try {
            tester1.debit(pointneeded / rewardperdebit);
        } catch (BankingException e) {
            fail("Caught BankingException - expected nothing");
        }
        try {
            tester1.debit(pointneeded / rewardperdebit);
        } catch (BankingException e) {
            fail("Caught BankingException - expected nothing");
        }

        // tester1 : remains with initial(8000) + cashback
        assertEquals(8000 + (2 * cashback), tester1.getBalance());
    }

    @Test
    void rewardaftermultipledebit() {
        try {
            tester1.debit(1000);
        } catch (BankingException e) {
            fail("Caught BankingException - expected nothing");
        }
        try {
            tester1.debit(800);
        } catch (BankingException e) {
            fail("Caught BankingException - expected nothing");
        }
        assertEquals(1800 * rewardperdebit - pointneeded, tester1.getRewardPoints());
    }

    @Test
    void rewardaftermultipledebitwithoutcashback() {
        try {
            tester2.credit(1000);
        } catch (BankingException e) {
            fail("Caught NegativeAmountException - expected nothing");
        }
        try {
            tester2.debit(100);
        } catch (BankingException e) {
            fail("Caught BankingException - expected nothing");
        }
        try {
            tester2.debit(200);
        } catch (BankingException e) {
            fail("Caught BankingException - expected nothing");
        }
        assertEquals(300, tester2.getRewardPoints());
    }

    @Test
    void testfailtransfer() {
        try {
            tester1.transfer(8001);
            fail("Did Not Catch BankingException - expected to do so");
        } catch (BankingException e) {
            //expected
        }
        try {
            tester2.transfer(300);
            fail("Did Not Catch BankingException - expected to do so");
        } catch (BankingException e) {
            //expected
        }
        try {
            tester1.transfer(-400);
            fail("Did Not Catch NegativeAmountException - expected to do so");
        } catch (NegativeAmountException e) {
            //expected
        } catch (BankingException e) {
            //expected
        }

        assertEquals(8000, tester1.getBalance());
        assertEquals(250, tester2.getBalance());
    }

    @Test
    void balanceaftertransfer() {
        try {
            tester1.credit(1000);
        } catch (BankingException e) {
            fail("Caught BankingException - expected nothing");
        }
        try {
            tester1.transfer(1000);
        } catch (BankingException e) {
            fail("Caught BankingException - expected nothing");
        }

        assertEquals(8000, tester1.getBalance());

    }

    @Test
    void rewardaftertransfer() {
        try {
            tester1.debit(1200);
        } catch (BankingException e) {
            fail("Caught BankingException - expected nothing");
        }
        assertEquals(tester1.getRewardPoints(), tester1.getRewardPoints());
    }


    @Test
    void balanceaftermultipletransfer() {
        try {
            tester1.credit(2000);
        } catch (BankingException e) {
            fail("Caught NegativeAmountException - expected nothing");
        }
        try {
            tester1.transfer(1000);
        } catch (BankingException e) {
            fail("Caught BankingException - expected nothing");
        }
        try {
            tester1.transfer(1000);
        } catch (BankingException e) {
            fail("Caught BankingException - expected nothing");
        }

        // tester1 : remains with initial(8000) + cashback
        assertEquals(8000, tester1.getBalance());
    }


    @Test
    void testToString() {
        assertTrue(tester1.toString().contains("name = Harry, balance = $8000.00"));
    }


}
