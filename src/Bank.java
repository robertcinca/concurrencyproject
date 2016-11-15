import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Bank {
	/**
	- im not quite sure if this is perfectly in line with the task which says employeeids are assigned when the customer goes to the bank.
	actually, in the input files the id is already assigned. i wish these people would learn english because thats bullshit
	bank is already set fixed as well
	- it might be better to continuoulsy add jobs to the blockingqueue depending on the timer but thats not as important right now
	No this is actually important. EMPLOYEES ONLY GO INTO THE QUEUE IF ALL BANK STAFF IS BUSY. CHANGE NECESSARY
	maybe a second blocking queue. if all bank staff is busy, the job would be put in that one, bankstaff also can only access that one. this would
	lead to shutdown problems though / no, check if both queues are empty <- this is a good solution , also solves time problem
	*/
	
	//no idea if lists are the best data structure for this
	private List<BankStaff> staff;
	private BlockingQueue<Runnable> jobs;
	private BlockingQueue<Runnable> schedule;
	private ExecutorService executor;
	public ReentrantReadWriteLock lock;
	
	public void setUpBank(int[] config, BlockingQueue<Runnable> jobs) {
		lock = new ReentrantReadWriteLock();
		this.jobs = jobs;
		schedule = new PriorityBlockingQueue<Runnable>(jobs.size());
		executor = Executors.newFixedThreadPool(config[0]);
		staff = new LinkedList<BankStaff>();
		for(int i=0; i<config[0]; i++) {
			BankStaff bankStaff = new BankStaff(i, this);
			staff.add(bankStaff);
		}
	}

	public void doBusiness() {
		int timer = 0;
		for(BankStaff bankstaff : staff) {
			executor.execute(bankstaff);
		}
		while(!schedule.isEmpty()||!jobs.isEmpty()) {	
			for(Runnable job : jobs) {
				if(((Job) job).getTime() == timer) {
					schedule.add(job);
				}
			}		
					
			//should wait for signal that executor threads are finished then shut them down. outer loop, inner loop maybe the timer?
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			timer++;
			System.out.println(timer);
			
		}
		executor.shutdown();		
	}

	public BlockingQueue<Runnable> getJobs() {
		return jobs;
	}

	public void setJobs(BlockingQueue<Runnable> jobs) {
		this.jobs = jobs;
	}

	public BlockingQueue<Runnable> getSchedule() {
		return schedule;
	}
	
		
}
