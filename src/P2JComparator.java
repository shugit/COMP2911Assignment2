import java.util.Comparator;


public class P2JComparator implements Comparator<Job> {
	private Point p;
	
	public P2JComparator(Point point){
		this.p = point;
	}
	
	public int compare(Job j1, Job j2) {
		Double o1 = this.distanceOf(j1);
		Double o2 = this.distanceOf(j2);
		return o1.compareTo(o2);
	}
	
	/**
	 * straight line distance of p to j's midpoint
	 * @param j
	 * @return
	 */
	private double distanceOf(Job j){
		return Math.sqrt(Math.pow(this.p.getX() - j.midPoint().getX(),2)+
				Math.pow(this.p.getY() - j.midPoint().getY(),2));
	}
	
	

}
