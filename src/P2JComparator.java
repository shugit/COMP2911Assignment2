import java.util.Comparator;


public class P2JComparator implements Comparator<Job> {

	public P2JComparator(Point point){
		this.p = point;
	}
	
	public int compare(Job j1, Job j2) {
		Double d1 = this.distanceOf(j1);
		Double d2 = this.distanceOf(j2);
		return d1.compareTo(d2);
	}
	
	/**
	 * straight line distance
	 * @param j
	 * @return
	 */
	private double distanceOf(Job j){
		return Math.pow(this.p.getX() - j.midPoint().getX(),2)+
				Math.pow(this.p.getY() - j.midPoint().getY(),2);
	}
	
	private Point p;

}
