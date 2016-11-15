import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Bank {
	/**
	- im not quite sure if this is perfectly in line with the task which says employeeids are assigned when the customer goes to the bank.
	actually, in the input files the id is already assigned. i wish these people would learn english because thats bullshit
	bank is already set fixed as well
	- the sleep method is not sufficient for the task, it only shows if the program is working. for submission, it has to be removed so the efficiency of the program can be
	tested. locks need to be implemented
	- we might have to search for bottlenecks as well
	- the step in and out of queue can be implemented by letting jobs check the executor if all threads are busy, then a boolean value is changed
	*/
	
	private List<BankStaff> staff;
	private PriorityBlockingQueue<Runnable> jobs;
	private PriorityBlockingQueue<Runnable> schedule;
	private ExecutorService executor;	
	private int timer = 0;
	private int[] config;
	
	public void setUpBank(int[] config, PriorityBlockingQueue<Runnable> jobs) {
		this.jobs = jobs;
		this.config = config;
		schedule = new PriorityBlockingQueue<Runnable>(jobs.size());
		executor = Executors.newFixedThreadPool(config[0]);
		staff = new LinkedList<BankStaff>();
		for(int i=0; i<config[0]; i++) {
			BankStaff bankStaff = new BankStaff(i+1, this);
			staff.add(bankStaff);
		}
	}

	public void doBusiness() {	
		for(BankStaff bankstaff : staff) {
			executor.execute(bankstaff);
		}
		//shutdown starts too early because im stup oh i know why oh no i dont
		while( !(schedule.isEmpty()) || config[6]>0) {
			System.out.println("Timer = " + timer + " (J:" + jobs.size()+ " S:"+ schedule.size() + ")");
			
			for(Runnable job : jobs) {
				if(((Job) job).getTime() == timer) {
					schedule.add(job);
					config[6] = config[6] - 1;
				}
			}
			
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			timer++;
		}
		executor.shutdown();	
		System.out.println("ExecutorShutdown");
	}

	public BlockingQueue<Runnable> getJobs() {
		return jobs;
	}

	public void setJobs(PriorityBlockingQueue<Runnable> jobs) {
		this.jobs = jobs;
	}

	public BlockingQueue<Runnable> getSchedule() {
		return schedule;
	}
	
	public int getTimer() {
		return timer;
	}
	
	public int getConfig(int x) {
		return config[x];
	}
		
}
