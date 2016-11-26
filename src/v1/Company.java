package v1;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Company {

	private int balance;
	private int companyNo;
	private ReentrantReadWriteLock lock;
	
	public Company(int companyNo, int balance) {
		this.companyNo = companyNo;
		this.setBalance(balance);
		lock = new ReentrantReadWriteLock();
	}
	
	@Override
	public String toString() {
		return "company "+companyNo;
	}

	public ReentrantReadWriteLock getLock() {
		return lock;
	}

	public void setLock(ReentrantReadWriteLock lock) {
		this.lock = lock;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}
	
}
