package v1;

//all the actions in this task should return a value to teller instead of running in here. this makes it possible to implement cyclic barrier

public class Employee {

	private int employeeID;
	private Company employer;
	
	Employee(int ID, Company employer) {
		this.employeeID = ID;
		this.employer = employer;
	}
	

	public void doTask(int transactionType, int amount, int admitted, int timer, int arrived, int processingTime, BankStaff teller) {
		if(transactionType == 1) {
			deposit(amount, processingTime, admitted, teller, timer, arrived);
		} else if(transactionType == 2) {
			withdraw(amount, processingTime, admitted, teller, timer, arrived);
		} else {
			checkBalance(admitted, processingTime, teller, timer, arrived);
		}
	}
	
	private void deposit(int amount, int processingTime, int admitted, BankStaff teller, int timer, int arrived) {
		//there is a problem when employees are waiting to acquire a lock, they are not passing barriers
		employer.getLock().writeLock().lock();
		while(teller.getEmployer().getTimer() < (admitted +processingTime)) {
			teller.awaitBarrier(1);
			teller.awaitBarrier(2);
			teller.awaitBarrier(3);
			teller.awaitBarrier(4);
		}
		employer.setBalance(employer.getBalance() + amount);
		System.out.println("(" +timer+ ") Employee " +employeeID+ ", with help of Teller " +teller.getID()+ ", deposits "  +amount+
				" into " +employer+ ". Admitted at time " +admitted+ ", arrived at " +arrived);
		employer.getLock().writeLock().unlock();
	}
	
	private void withdraw(int amount, int processingTime, int admitted, BankStaff teller, int timer, int arrived) {
		employer.getLock().writeLock().lock();
		while(teller.getEmployer().getTimer() < (admitted +processingTime)) {
			teller.awaitBarrier(1);
			teller.awaitBarrier(2);
			teller.awaitBarrier(3);
			teller.awaitBarrier(4);
		}
		employer.setBalance(employer.getBalance() - amount);
		System.out.println("(" +timer+ ") Employee " +employeeID+ ", with help of Teller " +teller.getID()+ ", withdraws " +amount+
				" from " +employer+ ". Admitted at time " +admitted+ ", arrived at " +arrived);
		employer.getLock().writeLock().unlock();
	}
	
	public void checkBalance(int admitted, int processingTime, BankStaff teller, int timer, int arrived) {
		employer.getLock().readLock().lock();
		while(teller.getEmployer().getTimer() < (admitted +processingTime)) {
			teller.awaitBarrier(1);
			teller.awaitBarrier(2);
			teller.awaitBarrier(3);
			teller.awaitBarrier(4);
		}
		System.out.println("(" +timer+ ") Employee " +employeeID+ ", with help of Teller " +teller.getID()+ ", checks balance of " +employer+
				". It is " +employer.getBalance()+ ". Admitted at time " +admitted+ ", arrived at " +arrived);
		employer.getLock().readLock().unlock();
	}
	
	@Override
	public String toString() {
		return Integer.toString(employeeID);
	}
	
}
