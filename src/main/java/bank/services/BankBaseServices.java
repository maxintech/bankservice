package bank.services;

import bank.dao.AccountDAO;
import bank.data.Account;
import bank.data.OperationError;
import bank.data.OperationStatus;

/**
 * This class encapsulates the services of a bank.
 * Deals with the {@link AccountDAO} to obtain and persist the account objects. 
 * 
 * Allows to create a new account.
 * Allows to transfer money form one existing account to another existing account.
 * 
 * @author Maximiliano Sanchez de Bustamante
 *
 */
public class BankBaseServices {

	/**
	 * This method realize the transfer from one existing account to another existing account validating the following:
	 * 	The value to transfer is positive greater than zero.
	 * 	The accounts are not the same.
	 * 	The accounts exists.
	 * 	The source account has funds to do the transfer.
	 * 
	 * The service uses the {@link AccountDAO} to obtain a valid snapshot of the accounts to do the validation 
	 * and later to do the transfer. Because of that the validation of the funds is valid meanwhile when the DAO tries
	 * to commit the changes the snapshot still valid.  
	 * 
	 * @param fromAccountName The name of the source account to transfer money
	 * @param toAccountName The name of the target account to transfer money
	 * @param transferValue The positive greater than zero value to transfer between the accounts 
	 * @return Returns an {@link OperationStatus}
	 */
	public OperationStatus transfer(String fromAccountName, String toAccountName, long transferValue) {
		AccountDAO dao = new AccountDAO();	
		
		// check the transfer value is positive
		if (transferValue <= 0) {
			return new OperationStatus(false, OperationError.INVALID_VALUE);
		}
		
		// Could not transfer to the same account
		if (fromAccountName.equals(toAccountName)) {
			return new OperationStatus(false, OperationError.ACCOUNTS_ARE_EQUAL);
		}
		
		// Check account exists
		Account fromAccount = dao.getAccount(fromAccountName);
		if (fromAccount == null) {
			return new OperationStatus(false, OperationError.ACCOUNT_DOESNT_EXIST);
		}
		Account toAccount = dao.getAccount(toAccountName);
		if (toAccount == null) {
			return new OperationStatus(false, OperationError.ACCOUNT_DOESNT_EXIST);
		}
		
		/* 
		 * Check it has enough balance to do the transfer
		 * The check is optimistic, because we are using the last snapshot of the accounts
		 * The DAO will do the final check using the sequence to know if  one or both were used
		 * meanwhile this is processing.
		 */
		if (fromAccount.getBalance()-transferValue < 0) {
			return new OperationStatus(false, OperationError.ACCOUNT_NOT_ENOUGH_FUNDS);
		}		 
		
		// Do the transfer ...
		fromAccount.withdraw(transferValue);
		toAccount.deposit(transferValue);
		// Persist the transfer
		// If some thread changed one or both accounts the method will return false
		// because they are out of sequence and could not do the transfer
		if (!dao.updateAccounts(fromAccount, toAccount)) {
			return new OperationStatus(false, OperationError.ACCOUNT_OUT_OF_SEQUENCE);
		}

		// Return the operation was a success
		return new OperationStatus(true);
	}
	
	/**
	 * Creates a new account.
	 * Validates if the balance is a positive number (included zero)
	 * Validates if the account already exists. If that the case return an error.
	 * 
	 * @param name A name for the new account
	 * @param balance The positive value to be set as balance for the account 
	 * @return Returns an {@link OperationStatus}
	 */
	public OperationStatus createAccount(String name, long balance) {
		AccountDAO dao = new AccountDAO();
		
		// check the balance value is positive
		if (balance < 0) {
			return new OperationStatus(false, OperationError.INVALID_VALUE);
		}
		
		// The account already exists
		if (dao.createAccount(name, balance) == null) {
			return new OperationStatus(false, OperationError.ACCOUNT_ALREADY_EXISTS);
		}

		// Return the operation was a success
		return new OperationStatus(true);
	}
}
