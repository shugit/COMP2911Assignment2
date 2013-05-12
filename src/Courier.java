


public class Courier {

	private static final boolean debug = true;

	public static void main(String[] args){
		CourierGraph g = new CourierGraph();
		g.addJob(3, 3, 5, 3);
		g.addJob(1, 1, 9, 2);
/*
		PriorityQueue<ASJob> pq = new PriorityQueue<ASJob>(1,new PQComparator());
		ArrayList<Job> a = new ArrayList<Job>();
		pq.add(new ASJob(new Job(3,3,5,3), 5,a));
		pq.add(new ASJob(new Job(1,1,9,2), 7,a));
		pq.add(new ASJob(new Job(7,3,1,4), 3,a));
		ASJob j = pq.remove();
		j.getJob().print();
		
		*/
		
		g.addJob(3, 5, 2, 7);
		g.addJob(5, 5, 5, 7);
		if(debug){
			System.out.print("@main:	");
			g.printList();
		}
		
		g.route();
	}
}
