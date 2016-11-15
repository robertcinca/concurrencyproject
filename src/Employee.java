
public class Employee {

	//private public needs checking in all classes / settergetter
	public int employeeID;
	public Company employer;
	//bank is assigned when employee comes to bank (two queue system)
	private Bank bank;
	
	Employee(int ID, Company employer, Bank bank) {
		this.employeeID = ID;
		this.employer = employer;
		this.bank = bank;
	}
	
	public void doTask(String task, int amount) {
		if(task.equals("deposit")) {
			deposit(amount);
		} else if(task.equals("withdraw")) {
			withdraw(amount);
		} else {
			checkBalance();
		}
	}
	
	public void deposit(int amount) {
		//here one of the lockmechanisms presented in lecture should be implemented or trylock
		bank.lock.writeLock().lock();
		System.out.println("Employee " + employeeID + " deposits " + amount + " into " + employer);
		employer.balance += amount;
		bank.lock.writeLock().unlock();
	}
	
	public void withdraw(int amount) {
		bank.lock.writeLock().lock();
		System.out.println("Employee " + employeeID + " withdraws " + amount + " from " + employer);
		employer.balance -= amount;
		bank.lock.writeLock().unlock();
	}
	
	public void checkBalance() {
		bank.lock.readLock().lock();
		System.out.println("Employee " + employeeID + " checks balance of " + employer + ". It is " + employer.balance);
		bank.lock.readLock().unlock();
	}
	
	@Override
	public String toString() {
		return "Employee " + employeeID;
	}
}
