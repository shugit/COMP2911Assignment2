import java.util.Comparator;


public class P2JComparator implements Comparator<Job> {

	public P2JComparator(Point point){
		this.p = point;
	}
	
	public int compare(Job j1, Job j2) {
		Integer o1 = this.distanceOf(j1);
		Integer o2 = this.distanceOf(j2);
		return o1.compareTo(o2);
	}
	
	
	private int distanceOf(Job j){
		return Math.abs(this.p.getX() - j.midPoint().getX())+
				Math.abs(this.p.getY() - j.midPoint().getY());
	}
	
	private Point p;

}
