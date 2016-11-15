
public class Company {

	public int balance;
	public int companyNo;
	
	public Company(int companyNo, int balance) {
		this.companyNo = companyNo;
		this.balance = balance;
	}
	
	@Override
	public String toString() {
		return "company "+companyNo;
	}
}
