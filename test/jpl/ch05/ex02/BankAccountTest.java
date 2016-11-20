package jpl.ch05.ex02;

import static org.junit.Assert.*;

import org.junit.Test;

public class BankAccountTest {

	@Test
	public void testConstructorsAndGetters() {
		long number = 1001;
		long balance = 10000;
		
		BankAccount bankAccount = new BankAccount(number, balance);
		
		assertTrue(bankAccount instanceof BankAccount);
		assertEquals(number, bankAccount.number());
		assertEquals(balance, bankAccount.balance());
		assertEquals(null, bankAccount.lastAct());
	}
	
	@Test
	public void testDeposit() {
		long number = 1001;
		long balance = 10000;
		long addingAmount = 1000;
		BankAccount bankAccount = new BankAccount(number, balance);
		
		bankAccount.deposit(addingAmount);
		assertEquals(balance + addingAmount, bankAccount.balance());
		assertEquals(number + ": deposit " + addingAmount, bankAccount.lastAct().toString());
		
	}

	@Test
	public void testWithdraw() {
		long number = 1001;
		long balance = 10000;
		long subtractingAmount = 1000;
		BankAccount bankAccount = new BankAccount(number, balance);
		
		bankAccount.withdraw(subtractingAmount);
		assertEquals(balance - subtractingAmount, bankAccount.balance());
		assertEquals(number + ": withdraw " + subtractingAmount, bankAccount.lastAct().toString());
		
	}

	@Test
	public void testOverdraw() {
		long number = 1001;
		long balance = 10000;
		long subtractingAmount = 11000;
		BankAccount bankAccount = new BankAccount(number, balance);
		
		bankAccount.withdraw(subtractingAmount);
		assertEquals(balance - subtractingAmount, bankAccount.balance());
		assertEquals(number + ": withdraw " + subtractingAmount, bankAccount.lastAct().toString());
		
	}

	@Test
	public void testHistoryWith10Actions() {
		long number = 1001;
		long balance = 10000;
		long subtractingAmount = 10;
		BankAccount bankAccount = new BankAccount(number, balance);
		for (int i = 0; i < 10; i++) {
			bankAccount.withdraw(subtractingAmount);			
		}
		
		BankAccount.History history = bankAccount.history();
		
		for (int i = 0; i < 10; i++) {
			assertEquals(number + ": withdraw " + subtractingAmount, history.next().toString());			
		}
		assertEquals(balance - (subtractingAmount * 10), bankAccount.balance());
		assertEquals(null, history.next());			
				
	}

	@Test
	public void testHistoryAfter10Actions() {
		long number = 1001;
		long balance = 10000;
		long subtractingAmount = 10;
		BankAccount bankAccount = new BankAccount(number, balance);
		for (int i = 0; i < 10; i++) {
			bankAccount.withdraw(subtractingAmount);			
		}
		bankAccount.withdraw(subtractingAmount * 2);			
		
		BankAccount.History history = bankAccount.history();
		
		for (int i = 0; i < 9; i++) {
			assertEquals(number + ": withdraw " + subtractingAmount, history.next().toString());			
		}
		assertEquals(number + ": withdraw " + (subtractingAmount * 2), history.next().toString());			
		assertEquals(balance - (subtractingAmount * 12), bankAccount.balance());
		assertEquals(null, history.next());			
				
	}

}
