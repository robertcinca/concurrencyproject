
public class Job {

	Employee employee;
	String action;
	int amount;
	int time;
	
	public Job(Employee employee, int time, String action, int amount) {
		this.employee = employee;
		this.time = time;
		this.action = action;
		if(amount!=0) {
			this.amount = amount;
		}
	}
	
}
