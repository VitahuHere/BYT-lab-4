package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MoneyTest {
	Currency SEK, DKK, NOK, EUR;
	Money SEK100, EUR10, SEK200, EUR20, SEK0, EUR0, SEKn100, NOK100;
	
	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
		NOK = new Currency("NOK", 0.10);
		SEK100 = new Money(10000, SEK);
		EUR10 = new Money(1000, EUR);
		SEK200 = new Money(20000, SEK);
		EUR20 = new Money(2000, EUR);
		SEK0 = new Money(0, SEK);
		EUR0 = new Money(0, EUR);
		SEKn100 = new Money(-10000, SEK);
		NOK100 = new Money(10000, NOK);
	}

	@Test
	public void testGetAmount() {
		// return the same amount as the one given in the constructor
		assertEquals((Integer) 10000, SEK100.getAmount());
		assertEquals((Integer) 1000, EUR10.getAmount());
		assertEquals((Integer) 20000, SEK200.getAmount());
		assertEquals((Integer) 2000, EUR20.getAmount());
		assertEquals((Integer) 0, SEK0.getAmount());
		assertEquals((Integer) 0, EUR0.getAmount());
		assertEquals((Integer) (-10000), SEKn100.getAmount());
		assertEquals((Integer) 10000, NOK100.getAmount());
	}

	@Test
	public void testGetCurrency() {
		// return the same currency as the one given in the constructor
		assertEquals(SEK, SEK100.getCurrency());
		assertEquals(EUR, EUR10.getCurrency());
		assertEquals(SEK, SEK200.getCurrency());
		assertEquals(EUR, EUR20.getCurrency());
		assertEquals(SEK, SEK0.getCurrency());
		assertEquals(EUR, EUR0.getCurrency());
		assertEquals(SEK, SEKn100.getCurrency());
		assertEquals(NOK, NOK100.getCurrency());
	}

	@Test
	public void testToString() {
		// check for correct string representation
		// also considering 0 and negative amounts
		assertEquals("100.00 SEK", SEK100.toString());
		assertEquals("10.00 EUR", EUR10.toString());
		assertEquals("200.00 SEK", SEK200.toString());
		assertEquals("20.00 EUR", EUR20.toString());
		assertEquals("0.00 SEK", SEK0.toString());
		assertEquals("0.00 EUR", EUR0.toString());
		assertEquals("-100.00 SEK", SEKn100.toString());
		assertEquals("100.00 NOK", NOK100.toString());
	}

	@Test
	public void testGlobalValue() {
		// check for correct conversion to universal value
		assertEquals((Integer) 1500, SEK100.universalValue());
		assertEquals((Integer) 1500, EUR10.universalValue());
		assertEquals((Integer) 3000, SEK200.universalValue());
		assertEquals((Integer) 3000, EUR20.universalValue());
		assertEquals((Integer) 0, SEK0.universalValue());
		assertEquals((Integer) 0, EUR0.universalValue());
		assertEquals((Integer) (-1500), SEKn100.universalValue());
		assertEquals((Integer) 1000, NOK100.universalValue());
	}

	@Test
	public void testEqualsMoney() {
		// check for equality between two money objects
		assertTrue(SEK100.equals(new Money(10000, SEK)));
		assertTrue(EUR10.equals(new Money(1000, EUR)));
		assertTrue(SEK200.equals(new Money(20000, SEK)));
		assertTrue(EUR20.equals(new Money(2000, EUR)));
		assertTrue(SEK0.equals(new Money(0, SEK)));
		assertTrue(EUR0.equals(new Money(0, EUR)));
		assertTrue(SEKn100.equals(new Money(-10000, SEK)));
		assertTrue(NOK100.equals(new Money(10000, NOK)));
	}

	@Test
	public void testAdd() {
		assertEquals((Integer) 20000, SEK100.add(SEK100).getAmount());
		assertEquals((Integer) 2000, EUR10.add(EUR10).getAmount());
		assertEquals((Integer) 40000, SEK200.add(SEK200).getAmount());
		assertEquals((Integer) 4000, EUR20.add(EUR20).getAmount());
		assertEquals((Integer) 10000, SEK0.add(SEK100).getAmount());
	}

	@Test
	public void testSub() {
		assertEquals((Integer) 10000, SEK100.sub(SEK0).getAmount());
		assertEquals((Integer) 1000, EUR10.sub(EUR0).getAmount());
		assertEquals((Integer) 0, SEK200.sub(SEK200).getAmount());
		assertEquals((Integer) 0, EUR20.sub(EUR20).getAmount());
		assertEquals((Integer) (-10000), SEK0.sub(SEK100).getAmount());
	}

	@Test
	public void testIsZero() {
		assertTrue(SEK0.isZero());
		assertTrue(EUR0.isZero());
		assertFalse(SEK100.isZero());
		assertFalse(EUR10.isZero());
		assertFalse(SEK200.isZero());
		assertFalse(EUR20.isZero());
		assertFalse(SEKn100.isZero());
		assertFalse(NOK100.isZero());
	}

	@Test
	public void testNegate() {
		// check for correct negation, also considering 0 and negative amounts
		assertEquals((Integer) (-10000), SEK100.negate().getAmount());
		assertEquals((Integer) (-1000), EUR10.negate().getAmount());
		assertEquals((Integer) (-20000), SEK200.negate().getAmount());
		assertEquals((Integer) (-2000), EUR20.negate().getAmount());
		assertEquals((Integer) 0, SEK0.negate().getAmount());
		assertEquals((Integer) 0, EUR0.negate().getAmount());
		assertEquals((Integer) 10000, SEKn100.negate().getAmount());
		assertEquals((Integer) (-10000), NOK100.negate().getAmount());
	}

	@Test
	public void testCompareTo() {
		// check for correct comparison between two money objects
		// self comparison should return 0
		assertTrue(SEK100.compareTo(SEK100) == 0);
		assertTrue(EUR10.compareTo(EUR10) == 0);
		assertTrue(SEK200.compareTo(SEK200) == 0);
		assertTrue(EUR20.compareTo(EUR20) == 0);
		assertTrue(SEK0.compareTo(SEK0) == 0);
		assertTrue(EUR0.compareTo(EUR0) == 0);
		assertTrue(SEKn100.compareTo(SEKn100) == 0);
		assertTrue(NOK100.compareTo(NOK100) == 0);

		assertTrue(SEK100.compareTo(SEK200) < 0);
		assertTrue(EUR10.compareTo(EUR20) < 0);
		assertTrue(SEK200.compareTo(SEK100) > 0);
		assertTrue(EUR20.compareTo(EUR10) > 0);

		assertTrue(SEK100.compareTo(SEKn100) > 0);
		assertTrue(EUR10.compareTo(EUR0) > 0);
		assertTrue(SEKn100.compareTo(SEK100) < 0);
		assertTrue(EUR0.compareTo(EUR10) < 0);

		assertTrue(SEK100.compareTo(SEK0) > 0);
		assertTrue(EUR10.compareTo(EUR0) > 0);
		assertTrue(SEK0.compareTo(SEK100) < 0);
		assertTrue(EUR0.compareTo(EUR10) < 0);
	}
}
