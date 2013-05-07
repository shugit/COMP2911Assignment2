

import java.util.Comparator;

public class JobComparator implements Comparator<Job>
{
	public JobComparator(Job j1){
		this.j = j1;
	}
	public int compare(Job j1, Job j2) {
		Integer o1 = this.distanceOf(j1, j);
		Integer o2 = this.distanceOf(j2, j);
		return o1.compareTo(o2);
	}
	
	
	private int distanceOf(Job j1, Job j2){
		return Math.abs(j1.midPoint().getX() - j2.midPoint().getX())+
				Math.abs(j1.midPoint().getY() - j2.midPoint().getY());
	}
	
	private Job j;
}