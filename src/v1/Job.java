package v1;

public class Job implements Comparable<Job> {

	private Employee employee;
	private BankStaff teller;
	private int transactionType;
	private int processingTime;
	private int amount;
	private int time;
	private int admitted;
	private boolean queued;
	private int[] queueingTimes;
	
	public Job(Employee employee, int time, int transactionType, int amount, int processingTime, int T_in, int T_out) {
		this.employee = employee;
		this.time = time;
		this.transactionType = transactionType;
		this.amount = amount;
		this.processingTime = processingTime;
		queueingTimes = new int[2];
		this.queueingTimes[0] = T_in;
		this.queueingTimes[1] = T_out;
	}
	
	public void setAdmitted(int admitted, BankStaff teller) {
		this.admitted = admitted;
		this.teller = teller;
	}
	
	public void execute(int timer) {
		employee.doTask(transactionType, amount, admitted, timer, time, processingTime, teller);	
		employee = null;
	}

	@Override
	public int compareTo(Job other) {
		return Integer.compare(this.time, other.time);
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
