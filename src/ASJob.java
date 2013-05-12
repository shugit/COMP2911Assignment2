import java.util.ArrayList;


public class ASJob {

	private static final boolean debug = true;
	private Job j;
	private double cx;
	private ArrayList<Job> fromJobs;
	

	public ASJob(Job job, double thecx, ArrayList<Job> aFromJobs){
		this.j = job;
		this.cx = thecx;
		this.fromJobs = aFromJobs;
		if(debug){
			System.out.print("ASJob: ");
			j.print();
			System.out.println("cx="+cx);
		}
	}
	
	public Job getJob(){
		return j;
	}
	public double getCx(){
		return cx;
	}
	public ArrayList<Job> getFrom(){
		return fromJobs;
	}
	
	public void print(){
		System.out.println("This cx is "+cx);
	}
	
}
