package v1;

//all the actions in this task should return a value to teller instead of running in here. this makes it possible to implement cyclic barrier

public class Employee {

	private int employeeID;
	private Company employer;
	
	Employee(int ID, Company employer) {
		this.employeeID = ID;
		this.employer = employer;
	}
	

	public void doTask(int transactionType, int amount, int admitted, int tellerNo, int timer) {
		if(transactionType == 1) {
			deposit(amount, admitted, tellerNo, timer);
		} else if(transactionType == 2) {
			withdraw(amount, admitted, tellerNo, timer);
		} else {
			checkBalance(admitted, tellerNo, timer);
		}
	}
	
	private void deposit(int amount, int admitted, int tellerNo, int timer) {
		//here one of the lockmechanisms presented in lecture should be implemented or trylock
		employer.getLock().writeLock().lock();	
		employer.setBalance(employer.getBalance() + amount);
		System.out.println("(" +timer+ ") Employee " +employeeID+ ", with help of Teller " +tellerNo+ ", deposits "  +amount+
				" into " +employer+ ". Admitted at time " +admitted+ ".");
		employer.getLock().writeLock().unlock();
	}
	
	private void withdraw(int amount, int admitted, int tellerNo, int timer) {
		employer.getLock().writeLock().lock();
		employer.setBalance(employer.getBalance() - amount);
		System.out.println("(" +timer+ ") Employee " +employeeID+ ", with help of Teller " +tellerNo+ ", withdraws " +amount+
				" from " +employer+ ". Admitted at time " +admitted+ ".");
		employer.getLock().writeLock().unlock();
	}
	
	public void checkBalance(int admitted, int tellerNo, int timer) {
		employer.getLock().readLock().lock();
		System.out.println("(" +timer+ ") Employee " +employeeID+ ", with help of Teller " +tellerNo+ ", checks balance of " +employer+
				". It is " +employer.getBalance()+ ". Admitted at time " +admitted+ ".");
		employer.getLock().readLock().unlock();
	}
	
	@Override
	public String toString() {
		return Integer.toString(employeeID);
	}
	
}
