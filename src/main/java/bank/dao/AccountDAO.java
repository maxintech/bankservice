package bank.dao;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantLock;

import bank.data.Account;

/**
 * The class manages the access to the storage where the accounts are.
 * In this simplistic implementation the storage is a Map where the key of the map entry is the name of the account
 * and the value of the map entry is an instance of {@link Account}
 * 	
 * The management of the concurrency and the integrity has its roots in the MVCC (Multiversion concurrency control)
 * This class implements the snapshot isolation as level of isolation. Everyone could read the last consistent state
 * of the accounts and let modify freely the content of the accounts, but when the thread want to write (commit) the changes
 * the system evaluates if the modified accounts to be written are the latest or in between other thread did a commit
 * first.
 * 
 * The system hasn't retries. In case of fail to write the changes, an error is returned and the business layer is 
 * responsible to retry or not the operation.
 * 
 * A lock is used to serialize the write access the the map.
 * 
 * @author Maximiliano Sanchez de Bustamante
 *
 */
public class AccountDAO {
	/**
	 * The map where the accounts are stored
	 */
	private static ConcurrentMap<String, Account> map = new ConcurrentHashMap<>();
	/**
	 * The lock used to serialize the write access the the map. 
	 * It is a reentrant lock because this class could be composed  and several operation in the same thread
	 * could try to acquire the same lock. 
	 * @{link java.util.concurrent.locks.ReentrantLock} allows recursive locking for the same thread.
	 */
	private static ReentrantLock lock = new ReentrantLock();
	
	/**
	 * Returns an {@link Account} instance given the name of the account if exists.
	 * 
	 * @param name The name of the account
	 * @return Returns an {@link Account} instance given the name of the account if exists. Otherwise returns null.
	 */
	public Account getAccount(String name) {
		return map.get(name);
	}
	
	/**
	 * Creates a new {@link Account} instance given the name and the balance.
	 * The balance should be positive and the name should not exists already, otherwise the method will return null.
	 * The account is added to the map and the instance is returned.
	 * 
	 * @param name The name of the account
	 * @param balance Balance (positive) to be set in the new account
	 * @return Returns the new {@link Account} instance created given the name and the balance. 
	 * If the account already exists returns null.
	 */
	public Account createAccount(String name, long balance) {
		Account account = new Account(name, balance);
		
		// Serialize the access to the map when we try to update the map with the accounts
		lock.lock();
		try {
			Account value = map.putIfAbsent(name, account);
			// The account already exists
			if (value != null) {
				return null;
			}
		} finally {
			lock.unlock();
		}
		
		return account;
	}
	
	/**
	 * Takes two modified accounts and tries to write (commit) the changes in the store (map).
	 * The validation process is the following:
	 * 	Obtain the current accounts from the map.
	 * 	Compare if the sequences are the same. If they are, the changes could be written in the map.
	 * 	If at least one of the sequences is different, means the account was modified and committed by other thread 
	 * 	in between.
	 *  If the accounts are going to be committed, the sequence is advanced in both accounts.
	 * The validation process is serialized with the lock object. This guarantee consistency in the data. 
	 * 
	 * @param one A modified {@link Account} instance to be updated
	 * @param two A modified {@link Account} instance to be updated
	 * @return Returns true if all the accounts could be updated. Returns false if at least one account is outdated.
	 */
	public boolean updateAccounts(Account one, Account two) {
		Account localOneAccount = one.copy();
		Account localTwoAccount = two.copy();

		// Serialize the access to the map when we try to write the data of the transfer
		lock.lock();
		try {
			// We obtain the current accounts from the store (map)
			Account currentOneAccount = map.get(localOneAccount.getName());
			Account currentTwoAccount = map.get(localTwoAccount.getName());
			
			/* 
			 * We check the sequence in both accounts.
			 * If the sequence is different in at least one of the account the operation will fail
			 * because other operation was done in the accounts and the local copy doesn't reflect
			 * the current value of the accounts
			 */
			if (currentOneAccount.getSequence() != localOneAccount.getSequence() ||
				currentTwoAccount.getSequence() != localTwoAccount.getSequence()) {
				return false;
			}
			
			// Update the sequences 
			localOneAccount.nextSequence();
			localTwoAccount.nextSequence();
			// Update the map with the updated accounts
			map.put(localOneAccount.getName(), localOneAccount);
			map.put(localTwoAccount.getName(), localTwoAccount);
	
			
		} finally {
			lock.unlock();
		}
		
		return true;
	}
}
