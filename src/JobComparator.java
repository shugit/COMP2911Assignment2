

import java.util.Comparator;

public class JobComparator implements Comparator<Job>
{
	private Job j;
	
	public JobComparator(Job j1){
		this.j = j1;
	}
	
	public int compare(Job j1, Job j2) {
		Double o1 = this.distanceOf(j1, j);
		Double o2 = this.distanceOf(j2, j);
		return o1.compareTo(o2);
	}
	
	/**
	 * straight light distance of jobs' midpoint
	 * @param j1
	 * @param j2
	 * @return
	 */
	private Double distanceOf(Job j1, Job j2){
		return Math.sqrt((Math.pow(j1.midPoint().getX() - j2.midPoint().getX(),2)+
				Math.pow(j1.midPoint().getY() - j2.midPoint().getY(),2)));
	}
	
	
}