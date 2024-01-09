package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CurrencyTest {
	Currency SEK, DKK, NOK, EUR;
	
	@Before
	public void setUp() throws Exception {
		/* Setup currencies with exchange rates */
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
		NOK = new Currency("NOK", 0.10);
	}

	@Test
	public void testGetName() {
		assertEquals("SEK", SEK.getName());
		assertEquals("DKK", DKK.getName());
		assertEquals("EUR", EUR.getName());
		assertEquals("NOK", NOK.getName());
	}
	
	@Test
	public void testGetRate() {
		assertEquals((Double) 0.15, SEK.getRate());
		assertEquals((Double) 0.20, DKK.getRate());
		assertEquals((Double) 1.5, EUR.getRate());
		assertEquals((Double) 0.10, NOK.getRate());
	}
	
	@Test
	public void testSetRate() {
		SEK.setRate(0.5);
		assertEquals((Double) 0.5, SEK.getRate());
		DKK.setRate(0.5);
		assertEquals((Double) 0.5, DKK.getRate());
		EUR.setRate(0.5);
		assertEquals((Double) 0.5, EUR.getRate());
		NOK.setRate(0.5);
		assertEquals((Double) 0.5, NOK.getRate());
	}
	
	@Test
	public void testGlobalValue() {
		assertEquals((Integer) 1500, SEK.universalValue(10000));
		assertEquals((Integer) 2000, DKK.universalValue(10000));
		assertEquals((Integer) 15000, EUR.universalValue(10000));
		assertEquals((Integer) 1000, NOK.universalValue(10000));
	}
	
	@Test
	public void testValueInThisCurrency() {
		// should give the amount in currency on which method is called
		assertEquals((Integer) 100000, SEK.valueInThisCurrency(10000, EUR));
		assertEquals((Integer) 75000, DKK.valueInThisCurrency(10000, EUR));
		assertEquals((Integer) 1000, EUR.valueInThisCurrency(10000, SEK));
		assertEquals((Integer) 15000, NOK.valueInThisCurrency(10000, SEK));
	}
}
