
public class Job extends RunsPriority implements Comparable {

	private Employee employee;
	private String action;
	private int amount;
	private int time;
	
	public Job(Employee employee, int time, String action, int amount) {
		this.employee = employee;
		this.time = time;
		this.action = action;
		this.amount = amount;
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void run() {
		employee.doTask(action, amount);
	}

	public int getTime() {
		return time;
	}
	
}