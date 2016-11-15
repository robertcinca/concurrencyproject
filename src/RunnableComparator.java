import java.util.Comparator;

public class RunnableComparator implements Comparator<Runnable> {

	@Override
	public int compare(Runnable o1, Runnable o2) {
		if(((Job) o1).getTime() < ((Job) o2).getTime()) {
			return -1;
		} else if(((Job) o2).getTime() > ((Job) o1).getTime()) {
			return 1;
		} else {
			return 0;
		}
	}

}
