package v1;

//this shit shouldnt be runnable

public class Job implements Comparable<Job> {

	private Employee employee;
	private int transactionType;
	private int processingTime;
	private int amount;
	private int time;
	private int admitted;
	private int tellerNo;
	
	
	public Job(Employee employee, int time, int transactionType, int amount, int processingTime) {
		this.employee = employee;
		this.time = time;
		this.transactionType = transactionType;
		this.amount = amount;
		this.processingTime = processingTime;
	}
	
	public void setAdmitted(int admitted, int tellerNo) {
		this.admitted = admitted;
		this.tellerNo = tellerNo;
	}
	
	public void execute(int timer) {
		employee.doTask(transactionType, amount, admitted, tellerNo, timer);	
		employee = null;
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

	public int getTime() {
		return time;
	}
	public int getAdmitted() {
		return admitted;
	}
	public int getProcessingTime() {
		return processingTime;
	}


	
}