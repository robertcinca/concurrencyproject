
public class Job {

	Employee employee;
	String action;
	int amount;
	int time;
	
	public Job(Employee employee, int time, String action, int amount) {
		this.employee = employee;
		this.time = time;
		this.action = action;
		this.amount = amount;
	}
	
	@Override
	public String toString() {
		if(amount>0) {
			return "This is a " + action + " job over " + amount + " to be executed by " + employee + " at time " + time + ".";
		} else {
			return "This is a balance " + action + " to be executed by " + employee + " at time " + time + ".";
		}
	}
	
}