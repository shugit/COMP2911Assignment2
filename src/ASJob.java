import java.text.DecimalFormat;
import java.util.ArrayList;


public class ASJob {

	private static final boolean debug = false;
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
			DecimalFormat df = new DecimalFormat("#.00");
			System.out.print("cx="+df.format(this.cx));
			System.out.println();
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
		DecimalFormat df = new DecimalFormat("#.00");
		System.out.println("This cx is "+df.format(this.cx));
	}
	
}
