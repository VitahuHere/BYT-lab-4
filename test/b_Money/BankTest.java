package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BankTest {
	Currency SEK, DKK;
	Bank SweBank, Nordea, DanskeBank;
	
	@Before
	public void setUp() throws Exception {
		DKK = new Currency("DKK", 0.20);
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		Nordea = new Bank("Nordea", SEK);
		DanskeBank = new Bank("DanskeBank", DKK);
		SweBank.openAccount("Ulrika");
		SweBank.openAccount("Bob");
		Nordea.openAccount("Bob");
		DanskeBank.openAccount("Gertrud");
	}

	@Test
	public void testGetName() {
		assertEquals("SweBank", SweBank.getName());
		assertEquals("Nordea", Nordea.getName());
		assertEquals("DanskeBank", DanskeBank.getName());
	}

	@Test
	public void testGetCurrency() {
		assertEquals(SEK, SweBank.getCurrency());
		assertEquals(SEK, Nordea.getCurrency());
		assertEquals(DKK, DanskeBank.getCurrency());
	}

	@Test
	public void testOpenAccount() throws AccountExistsException, AccountDoesNotExistException {
		SweBank.openAccount("Alice");
		assertEquals((Integer) 0, SweBank.getBalance("Alice"));
	}

	@Test
	public void testDeposit() throws AccountDoesNotExistException {
		SweBank.deposit("Bob", new Money(10000, SEK));
		assertEquals((Integer) 10000, SweBank.getBalance("Bob"));
		// deposit in different currency
		SweBank.deposit("Bob", new Money(10000, DKK));
		assertEquals((Integer) 17500, SweBank.getBalance("Bob"));
	}

	@Test
	public void testWithdraw() throws AccountDoesNotExistException {
		SweBank.withdraw("Bob", new Money(10000, SEK));
		assertEquals((Integer) (-10000), SweBank.getBalance("Bob"));
	}
	
	@Test
	public void testGetBalance() throws AccountDoesNotExistException {
		assertEquals((Integer) 0, SweBank.getBalance("Bob"));
	}
	
	@Test
	public void testTransfer() throws AccountDoesNotExistException {
		SweBank.transfer("Bob", "Ulrika", new Money(10000, SEK));
		assertEquals((Integer) (-10000), SweBank.getBalance("Bob"));
		assertEquals((Integer) 10000, SweBank.getBalance("Ulrika"));
	}
	
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		SweBank.addTimedPayment("Bob", "1", 1, 0, new Money(10000, SEK), SweBank, "Ulrika");
		SweBank.tick();
		assertEquals((Integer) (-10000), SweBank.getBalance("Bob"));
		assertEquals((Integer) 10000, SweBank.getBalance("Ulrika"));
		SweBank.tick();
		assertEquals((Integer) (-10000), SweBank.getBalance("Bob"));
		assertEquals((Integer) 10000, SweBank.getBalance("Ulrika"));
		SweBank.removeTimedPayment("Bob", "1");
		SweBank.tick();
		assertEquals((Integer) (-10000), SweBank.getBalance("Bob"));
		assertEquals((Integer) 10000, SweBank.getBalance("Ulrika"));
	}
}
