package v1;

public class Job implements Comparable<Job> {

	private Employee employee;
	private int transactionType;
	private int processingTime;
	private int amount;
	private int time;
	private int admitted;
	private int tellerNo;
	private boolean queued;
	private int[] queueingTimes;
	
	
	public Job(Employee employee, int time, int transactionType, int amount, int processingTime, int T_in, int T_out ) {
		this.employee = employee;
		this.time = time;
		this.transactionType = transactionType;
		this.amount = amount;
		this.processingTime = processingTime;
		queueingTimes = new int[2];
		this.queueingTimes[0] = T_in;
		this.queueingTimes[1] = T_out;
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
	public boolean getQueued() {
		return queued;
	}
	public void setQueued(boolean x) {
		this.queued = x;
	}
	public Employee getEmployee() {
		return employee;
	}
	public int getQueueingTimes(int i) {
		return queueingTimes[i];
	}
	public void setQueueingTimes(int i, int j) {
		queueingTimes[i] = queueingTimes[i] + j;
	}


	
}