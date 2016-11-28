package v1;

public class Job implements Comparable<Job> {

	private Employee employee; //the employee which wants to do this task
	private BankStaff teller; //the teller that handles this task
	private int transactionType; //differs between withdraw, deposit, and check balance
	private int processingTime; //how long does it take to process this job
	private int amount;
	private int time; //at what time did this job arrive at the bank
	private int admitted; //at what time was it admitted by a teller
	private boolean queued; //did this job (and the employee) have to wait in the queue
	private int[] queueingTimes; //contains T_in and T_out
	
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
	
	/**
	 * When a teller starts to process this Job, his ID and the corresponding time are set
	 */
	public void setAdmitted(int admitted, BankStaff teller) {
		this.admitted = admitted;
		this.teller = teller;
	}
	
	/**
	 * For execution, the Jobs are passed from BankStaff to Employee
	 */
	public void execute(int timer) {
		employee.doTask(transactionType, amount, admitted, timer, time, processingTime, teller);	
		employee = null;
	}

	/**
	 * Favors jobs with earlier arrival time. FCFS principle.
	 */
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
