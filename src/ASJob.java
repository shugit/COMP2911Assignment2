import java.util.ArrayList;


public class ASJob {

	public ASJob(Job job, int thecx, ArrayList<Job> aFromJobs){
		this.j = job;
		this.cx = thecx;
		this.fromJobs = aFromJobs;
	}
	
	public Job getJob(){
		return j;
	}
	public int getCx(){
		return cx;
	}
	public ArrayList<Job> getFrom(){
		return fromJobs;
	}
	
	private Job j;
	private int cx;
	private ArrayList<Job> fromJobs;
}
