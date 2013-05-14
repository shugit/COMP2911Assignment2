
public class Point {
	private double x;
	private double y;
	
	public Point(double d, double e){
		this.x = d;
		this.y = e;
	}
	
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	public void print() {
		System.out.print((int)this.x+" "+(int)this.y);
	}
	
	public boolean equalsTo(Point p){
		if(this.getX() == p.getX() && this.getY() == p.getY()){
			return true;
		} else {
			return false;
		}
	}
}
