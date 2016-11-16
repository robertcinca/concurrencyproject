package v1;

public class Job extends RunsPriority implements Comparable<Job> {

	private Employee employee;
	private String action;
	private int amount;
	private int time;
	private int admitted;
	private int tellerNo;
	
	public Job(Employee employee, int time, String action, int amount) {
		this.employee = employee;
		this.time = time;
		this.action = action;
		this.amount = amount;
	}

	@Override
	public int compareTo(Job o) {
		if( o.getTime() < time) {
			return -1;
		} else if( o.getTime() > time) {
			return 1;
		} else {
			return 0;
		}
	}
	
	@Override
	public void run() {
		employee.doTask(action, amount, admitted, tellerNo);
	}

	public int getTime() {
		return time;
	}

	public void setAdmitted(int admitted, int tellerNo) {
		this.admitted = admitted;
		this.tellerNo = tellerNo;
	}
	
	@Override
	public String toString() {
		return  employee + ", " + Integer.toString(time);
	}

	


	
	
}