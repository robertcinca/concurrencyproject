
public class Employee {

	public int employeeID;
	public Company employer;
	
	Employee(int ID, Company employer) {
		this.employeeID = ID;
		this.employer = employer;
	}
	
	public void deposit(int amount) {
		
	}
	
	public void withdraw(int amount) {
		
	}
	
	public int checkBalance() {
		return 1;
	}
	
	@Override
	public String toString() {
		return "Employee " + employeeID;
	}
}
