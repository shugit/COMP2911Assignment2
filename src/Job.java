/**
 * All distance are in Manhatten
 * @author Sephy
 *
 */
public class Job {

	public Job(int x1, int y1, int x2, int y2){
		this.start = new Point(x1,y1);
		this.end = new Point(x2,y2);
	}
	
	public int length(){
		return Math.abs(this.start.getX()-this.end.getX())
				+Math.abs(this.start.getY() - this.end.getY());
	}
	
	public Point midPoint(){
		return new Point(1/2*(this.start.getX()+this.end.getX()),
				1/2*(this.start.getY()+this.end.getY()));
	}
	
	public Point getStart(){
		return start;
	}
	public Point getEnd(){
		return end;
	}
	
	private Point start;
	private Point end;
}
