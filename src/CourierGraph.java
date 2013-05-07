import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;


public class CourierGraph{

	/**
	 * read from a int[4],set a job
	 * 
	 */
	public void addJob(int x1, int y1, int x2, int y2) {
		Job j = new Job(x1,y1,x2,y2);
		this.jobs.add(j);
	}
	
	/**
	 * initially start point 0,0
	 * ArrayList rests is a list to store rest jobs for a routing path(a route version of a graph).
	 * fromJob is a job's parent
	 */
	@SuppressWarnings("unchecked")
	public void route() {
		
		PriorityQueue<ASJob> toVisit = new PriorityQueue<ASJob>(1,new PQComparator());
		ArrayList<Job> rests = (ArrayList<Job>) jobs.clone();
		ArrayList<ASJob> visited = new ArrayList<ASJob>();
		
		Job initial = new Job(0,0,0,0);
		
		ArrayList<Job> fromJobs = new ArrayList<Job>();
		
		toVisit.add(new ASJob(initial,hx(initial,rests),fromJobs));
		while (!toVisit.isEmpty()) {
			ASJob asj = toVisit.remove();
			if (rests.isEmpty()) {
				System.out.println("DONE");
				return;
			} else {
				for (Job eachJob : rests) {
					//int gx = this.gx(eachJob); //will change to a ArrayList later for path
					//should build it's own [rests]?
					//remove itself?
					int hx = this.hx(eachJob, (ArrayList<Job>) rests.clone());
					ArrayList<Job> newFromJobs = (ArrayList<Job>) asj.getFrom().clone();
					newFromJobs.add(asj.getJob());
					int gx = this.gx(newFromJobs);
					toVisit.add(new ASJob(eachJob,gx+hx,newFromJobs));
				}
			}
			visited.add(asj);
			rests.remove(asj.getJob());
		}
		
		
	}
	
	/**
	 * 
	 * @param aCurrent
	 * @param rests doesn't involve Job aCurrent
	 * @return
	 */
	private int hx(Job aCurrent, ArrayList<Job> rests){
		// j->nearest
		// nearest -> next nearest
		Job current = aCurrent;
		Job next = this.nearestJob(current.getEnd(), rests);
		int hx = distanceOf(current.getEnd(),next);
		rests.remove(current);
		
		while(!rests.isEmpty()){
			current = next;
			next = this.nearestJob(current, rests);
			rests.remove(current);
			hx += this.distanceOf(current,next);
			
			
		}
		
		return hx;
	}
	
	private int gx(ArrayList<Job> fromJobs){
		int gx = this.distanceOf(new Point(0,0), fromJobs.get(0).getStart());
		gx += fromJobs.get(0).length();
		for(int i = 1; fromJobs.get(i)!=null; i++){
			Job pre = fromJobs.get(i-1);
			Job next = fromJobs.get(i);
			gx += distanceOf(pre.getEnd(),next.getStart());
			gx += next.length();
		}
		
		return gx;
	}
	
	
	private int size() {
		return jobs.size();
	}

	/**
	 * calculate a nearest job based on current job, not end point.
	 * @param j1
	 * @return
	 */
	private Job nearestJob(Job j1,ArrayList<Job> rests){
		ArrayList<Job> jobList = new ArrayList<Job>();
		for(Job j2 : rests){
			if(!j1.equals(j2)){
				jobList.add(j2);
			}
		}
		return Collections.min(jobList, new JobComparator(j1));
	}
	
	
	/** 
	 * calculator for initial point
	 * @param p
	 * @return
	 */
	private Job nearestJob(Point p, ArrayList<Job> rests){
		return Collections.min(rests, new P2JComparator(p));
	}
	
	
	
	
	
	
	private int distanceOf(Job j1,Job j2){
		return Math.abs(j1.midPoint().getX() - j2.midPoint().getX())+
				Math.abs(j1.midPoint().getY() - j2.midPoint().getY());
	}
	
	private int distanceOf(Point p,Job j){
		return Math.abs(p.getX() - j.midPoint().getX())+
				Math.abs(p.getY() - j.midPoint().getY());
	}
	
	private int distanceOf(Point p1, Point p2){
		return Math.abs(p1.getX() - p2.getX())+
				Math.abs(p1.getY() - p2.getY());
	}
	private ArrayList<Job> jobs = new ArrayList<Job>();

	private ArrayList<Job> getRest(ArrayList<Job> visited){
		ArrayList<Job> List = new ArrayList<Job>();
		for( Job j : List){
			if(!visited.contains(j)){
				List.add(j);
			}
		}
		return List;
	}
	

}
