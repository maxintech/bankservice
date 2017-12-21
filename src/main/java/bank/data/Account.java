package bank.data;

/**
 * Plain data object that contains the attributes and a minimum of operations for a bank account. 
 * 
 * @author Maximiliano Sanchez de Bustamante
 *
 */
public class Account {
	private long balance;
	private String name;
	
	/**
	 * The sequence is managed by the Account DAO. Allows to control the version of the account in the store
	 */
	private long sequence;

    protected Account(String name, long balance, long sequence) {
        this.balance = balance;
        this.name = name;
        this.sequence = sequence;
    }

    public Account(String name, long balance) {
    		this(name, balance, 0);
    }

	public long getBalance() {
        return balance;
    }

    public String getName() {
        return name;
    }
    
    public void withdraw(long value) {
    		this.balance -= value;
    }

    public void deposit(long value) {
		this.balance += value;
    }
    
	public long getSequence() {
		return sequence;
	}

	public void nextSequence() {
		this.sequence++;
	}

	public Account copy() {
    		return new Account(this.name, this.balance, this.sequence);
    }

}
