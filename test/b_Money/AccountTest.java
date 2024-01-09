package b_Money;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AccountTest {
	Currency SEK;
	Bank SweBank;
	Account testAccount;
	
	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);

		SweBank = new Bank("SweBank", SEK);

		testAccount = new Account("Hans", SEK);
		testAccount.deposit(new Money(10_000_000, SEK));

		SweBank.openAccount("Alice");
	}
	
	@Test
	public void testAddRemoveTimedPayment() {
		testAccount.addTimedPayment("1", 2, 0, new Money(100, SEK), SweBank, "Alice");
		assertTrue(testAccount.timedPaymentExists("1"));
		testAccount.removeTimedPayment("1");
		assertFalse(testAccount.timedPaymentExists("1"));
	}
	
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		testAccount.addTimedPayment("1", 2, 0, new Money(1_000_000, SEK), SweBank, "Alice");
		for (int i = 0; i < 5; i++) {
			testAccount.tick();
		}
		// every 2 ticks remove 1_000_000 starting at tick 0
		// 0 tick or start: 10_000_000
		// 1 tick: 9_000_000 pay and set next = 2
		// 2 tick: 9_000_000 next = 1
		// 3 tick: 9_000_000 next = 0
		// 4 tick: 8_000_000 pay and set next = 2
		// 5 tick: 8_000_000 next = 1
		assertEquals((Integer) 8_000_000, testAccount.getBalance().getAmount());
		assertEquals((Integer) 2_000_000, SweBank.getBalance("Alice"));
		testAccount.removeTimedPayment("1");

		// test for non-existing account
		testAccount.addTimedPayment("2", 2, 0, new Money(1_000_000, SEK), SweBank, "Bob");
		for (int i = 0; i < 5; i++) {
			testAccount.tick();
		}
		assertEquals((Integer) 8_000_000, testAccount.getBalance().getAmount());
		assertEquals((Integer) 2_000_000, SweBank.getBalance("Alice"));
	}

	@Test
	public void testAddWithdraw() {
		testAccount.deposit(new Money(1000000, SEK));
		assertEquals((Integer) 11000000, testAccount.getBalance().getAmount());
		testAccount.withdraw(new Money(1000000, SEK));
		assertEquals((Integer) 10000000, testAccount.getBalance().getAmount());
	}
	
	@Test
	public void testGetBalance() {
		assertEquals((Integer) 10000000, testAccount.getBalance().getAmount());
	}
}
