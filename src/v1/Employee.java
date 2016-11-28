package v1;

import java.util.concurrent.TimeUnit;

public class Employee {

	private int employeeID;
	private Company employer;
	
	public Employee(int ID, Company employer) {
		this.employeeID = ID;
		this.employer = employer;
	}
	
	/**
	 * Transfers an incoming task to the appropriate handling method
	 */
	public void doTask(int transactionType, int amount, int admitted, int timer, int arrived, int processingTime, BankStaff teller) {
		if(transactionType == 1) {
			deposit(amount, processingTime, admitted, teller, arrived);
		} else if(transactionType == 2) {
			withdraw(amount, processingTime, admitted, teller, arrived);
		} else {
			checkBalance(admitted, processingTime, teller, arrived);
		}
	}
	
	/**
	 * Executes a job. The on-code comments in this method apply in the withdraw and check methods as well
	 */
	private void deposit(int amount, int processingTime, int admitted, BankStaff teller, int arrived) {
		while(true) {
			try {
				if(employer.getLock().writeLock().tryLock(0, TimeUnit.SECONDS)) { //tries to acquire a writelock. trylock avoid deadlocks
					break;
				} else { //if lock couldnt be acquired, this thread has to wait one turn before trying again
					teller.getEmployer().awaitBarrier(1);
					teller.getEmployer().awaitBarrier(2);
					teller.getEmployer().awaitBarrier(3);
					teller.getEmployer().awaitBarrier(4);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		while(teller.getEmployer().getTimer() < (admitted +processingTime)) { //executes the job. after each pass, timer is incremented by one
			teller.getEmployer().awaitBarrier(1);
			teller.getEmployer().awaitBarrier(2);
			teller.getEmployer().awaitBarrier(3);
			teller.getEmployer().awaitBarrier(4);
		}
		employer.setBalance(employer.getBalance() + amount);
		System.out.println("(" +teller.getEmployer().getTimer()+ ") Employee " +employeeID+ ", with help of Teller " +teller.getID()+ ", deposits $"  +amount+
				" into " +employer+ ". Admitted at time " +admitted+ ", arrived at " +arrived);
		employer.getLock().writeLock().unlock(); //releases the lock
	}
	
	private void withdraw(int amount, int processingTime, int admitted, BankStaff teller, int arrived) {
		while(true) {
			try {
				if(employer.getLock().writeLock().tryLock(0, TimeUnit.SECONDS)) {
					break;
				} else {
					teller.getEmployer().awaitBarrier(1);
					teller.getEmployer().awaitBarrier(2);
					teller.getEmployer().awaitBarrier(3);
					teller.getEmployer().awaitBarrier(4);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		while(teller.getEmployer().getTimer() < (admitted +processingTime)) {
			teller.getEmployer().awaitBarrier(1);
			teller.getEmployer().awaitBarrier(2);
			teller.getEmployer().awaitBarrier(3);
			teller.getEmployer().awaitBarrier(4);
		}
		employer.setBalance(employer.getBalance() - amount);
		System.out.println("(" +teller.getEmployer().getTimer()+ ") Employee " +employeeID+ ", with help of Teller " +teller.getID()+ ", withdraws $" +amount+
				" from " +employer+ ". Admitted at time " +admitted+ ", arrived at " +arrived);
		employer.getLock().writeLock().unlock();
	}
	
	public void checkBalance(int admitted, int processingTime, BankStaff teller, int arrived) {
		while(true) {
			try {
				if(employer.getLock().readLock().tryLock(0, TimeUnit.SECONDS)) {
					break;
				} else {
					teller.getEmployer().awaitBarrier(1);
					teller.getEmployer().awaitBarrier(2);
					teller.getEmployer().awaitBarrier(3);
					teller.getEmployer().awaitBarrier(4);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		while(teller.getEmployer().getTimer() < (admitted +processingTime)) {
			teller.getEmployer().awaitBarrier(1);
			teller.getEmployer().awaitBarrier(2);
			teller.getEmployer().awaitBarrier(3);
			teller.getEmployer().awaitBarrier(4);
		}
		System.out.println("(" +teller.getEmployer().getTimer()+ ") Employee " +employeeID+ ", with help of Teller " +teller.getID()+ ", checks balance of " +employer+
				". It is $" +employer.getBalance()+ ". Admitted at time " +admitted+ ", arrived at " +arrived);
		employer.getLock().readLock().unlock();
	}
	
	@Override
	public String toString() {
		return Integer.toString(employeeID);
	}
	
}
