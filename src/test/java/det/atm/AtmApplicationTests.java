package det.atm;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
// import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
// import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

// @EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@SpringBootTest(classes = AtmApplication.class)
public class AtmApplicationTests {

	@Test
	public void test_account_constructor() {
		Account testAccount = new Account();

		testAccount.setId(10L);
		assertEquals(10L, testAccount.getId());

		testAccount.setName("John Doe");
		assertEquals("John Doe", testAccount.getName());

		testAccount.setFirstBalance();
		testAccount.setBalance(Transaction.Type.DEPOSIT, 100D);
		assertEquals("$100.00", testAccount.getBalance());

		testAccount.setBalance(Transaction.Type.WITHDRAWAL, 10.50D);
		assertEquals("$89.50", testAccount.getBalance());
	}

	@Test
	public void test_transaction_constructor() {
		Transaction testTransaction = new Transaction();
		Transaction testTransaction2 = new Transaction(10L, Transaction.Type.WITHDRAWAL, 75.50D);

		testTransaction.setAccountId(10L);
		assertEquals(10L, testTransaction.getId());

		testTransaction.setNum(5L);
		assertEquals(5L, testTransaction.getNum());

		testTransaction.setTransaction(Transaction.Type.DEPOSIT, 20D);
		assertEquals(Transaction.Type.DEPOSIT, testTransaction.getType());
		assertEquals(20D, testTransaction.getAmount());

		assertEquals(10L, testTransaction2.getId());
		assertEquals(Transaction.Type.WITHDRAWAL, testTransaction2.getType());
		assertEquals(75.50D, testTransaction2.getAmount());
	}

}
