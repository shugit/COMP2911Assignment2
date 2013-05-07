
public class Courier {

	public static void main(String[] args){
		CourierGraph g = new CourierGraph();
		g.addJob(3, 3, 5, 3);
		g.addJob(1, 1, 9, 2);
		g.addJob(3, 5, 2, 7);
		g.addJob(5, 5, 5, 7);
		g.route();
	}
}
