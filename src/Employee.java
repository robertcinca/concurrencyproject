
public class Employee {

	//private public needs checking in all classes / settergetter
	private int employeeID;
	private Company employer;
	//bank is assigned when employee comes to bank (two queue system)
	private Bank bank;
	
	Employee(int ID, Company employer, Bank bank) {
		this.employeeID = ID;
		this.employer = employer;
		this.bank = bank;
	}
	
	public void doTask(String task, int amount, int admitted) {
		if(task.equals("deposit")) {
			deposit(amount, admitted);
		} else if(task.equals("withdraw")) {
			withdraw(amount, admitted);
		} else {
			checkBalance(admitted);
		}
	}
	
	public void deposit(int amount, int admitted) {
		//here one of the lockmechanisms presented in lecture should be implemented or trylock
		employer.getLock().writeLock().lock();	
		employer.setBalance(employer.getBalance() + amount);
		while(true) {
			if(bank.getTimer() >= admitted + bank.getConfig(1)) {
				break;
			} else {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		System.out.println("(" + bank.getTimer() + "). Employee " + employeeID + " deposits " + amount + " into " + employer + ". Admitted at time " +admitted);
		employer.getLock().writeLock().unlock();
	}
	
	public void withdraw(int amount, int admitted) {
		employer.getLock().writeLock().lock();
		employer.setBalance(employer.getBalance() - amount);
		while(true) {
			if(bank.getTimer() >= admitted + bank.getConfig(2)) {
				break;
			} else {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		System.out.println("(" + bank.getTimer() + "). Employee " + employeeID + " withdraws " + amount + " from " + employer + ". Admitted at time " +admitted);
		employer.getLock().writeLock().unlock();
	}
	
	public void checkBalance(int admitted) {
		employer.getLock().readLock().lock();
		while(true) {
			if(bank.getTimer() >= admitted + bank.getConfig(3)) {
				break;
			} else {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		System.out.println("(" + bank.getTimer() + "). Employee " + employeeID + " checks balance of " + employer + ". It is " + employer.getBalance() + ". Admitted at time " +admitted);
		employer.getLock().readLock().unlock();
	}
	
	@Override
	public String toString() {
		return "Employee " + employeeID;
	}
}
