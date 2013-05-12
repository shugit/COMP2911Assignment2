import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;


public class CourierGraph{
	public static boolean debug = true;
	private ArrayList<Job> jobs = new ArrayList<Job>();

	/**
	 * read from a int[4],set a job
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
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
		ArrayList<Job> fromJobs = new ArrayList<Job>();
		// step 1, add starting jobs to toVisit
		
		for(Job j: jobs){
			ArrayList<Job> newRests = (ArrayList<Job>) jobs.clone();
			newRests.remove(j);
			//System.out.println("DONE" + this.hx(j, newRests));
			toVisit.add(new ASJob(j,this.hx(j, newRests) + this.gx(fromJobs), fromJobs) );
			
		}
		
		// step 2, while toVisit is not empty
		while (!toVisit.isEmpty()) {
			
			// step 3, get asj from toVisit
			ASJob asj = toVisit.remove();
			// setp 4, if it's not goal(which means all job not cleared)
			this.printList(asj.getFrom());
			ArrayList<Job> rests = (ArrayList<Job>) this.getRest(asj.getFrom()).clone();
			rests.remove(asj.getJob());
			asj.getJob().print();
			System.out.println("testflag");
			if (rests.isEmpty()) {
				System.out.println("DONE");
				this.printList(asj.getFrom());
				return;
			} else {
				if(debug){
					System.out.println("@route : rests:");
					this.printList(rests);
				}
				
				// setp 5, calculate asj's adjancent points' h(x) and g(x) and push them into PriorityQueue
				for (Job eachJob : rests) {

					double hx = this.hx(eachJob, (ArrayList<Job>) rests.clone());

					// add asj to fromJobs (for calculating path)
					ArrayList<Job> newFromJobs = (ArrayList<Job>) asj.getFrom().clone();
					newFromJobs.add(asj.getJob());
					
					double gx = this.gx(newFromJobs);
					System.out.println("testflag");
					toVisit.add(new ASJob(eachJob,gx+hx,newFromJobs));
				}
			}
			// step 6, a new visited list, add asj. visited should be complementary of rest.
			//visited.add(asj);
			//rests.remove(asj.getJob());
		}


	}

	/**
	 * get h(x), from end point of aCurrent to it's nearest job's midpoint from list "rests".
	 * and from this midpoint to it's next nearest midpoint.
	 * 
	 * @param aCurrent
	 * @param rests doesn't involve Job aCurrent, which must be a clone coz this.hx gonna manipulate on it
	 * @return
	 */
	private double hx(Job aCurrent, ArrayList<Job> rests){
		//this.removeInitial(rests);
		// j->nearest
		// nearest -> next nearest
		Job current = aCurrent;
		
		Job next = this.nearestJob(current.getEnd(), rests);
		double hx = distanceOf(current.getEnd(),next);
		while(!rests.get(rests.size()-1).equals(next)){
			rests.remove(current);
			current = next;
			next = this.nearestJob(current, rests);
			//System.out.println("testflag");
			hx += this.distanceOf(current, next);
			//System.out.println("testflag");
			
		}
		
		/*if(debug){
			System.out.println("hx = "+hx);
		}*/
		
		return hx;
	}

	/**
	 * get g(x), which is the length from starting point to end point consists of job length
	 * @param fromJobs
	 * @return
	 */
	private int gx(ArrayList<Job> fromJobs){
		if(fromJobs.size() > 0){
			int gx = (int)this.distanceOf(new Point(0,0), fromJobs.get(0).getStart());
			gx += fromJobs.get(0).length();
			for(int i = 1; fromJobs.get(i) != null; i++){
				Job pre = fromJobs.get(i-1);
				Job next = fromJobs.get(i);
				gx += distanceOf(pre.getEnd(),next.getStart());
				gx += next.length();
			}

			return gx;
		} else 
			return 0;
	}


	private int size() {
		return jobs.size();
	}

	/**
	 * calculate a nearest job based on current job, not end point.
	 * @param j1
	 * @param rests
	 * @return
	 */
	private Job nearestJob(Job j1,ArrayList<Job> rests){
		this.removeInitial(rests);
		
		ArrayList<Job> jobList = new ArrayList<Job>();
		for(Job j2 : rests){
			if(!j1.equals(j2)){
				jobList.add(j2);
			}
		}
		Job j = Collections.min(jobList, new JobComparator(j1));
		
		//for debug
		/*if(debug){
			System.out.println("@nearestJob");
			this.printList(jobList);
			System.out.print("gonna return:");
			j.print();
		}*/
		return j;
	}


	/**
	 * get the nearst job from point "p" in a list "rests"
	 * @param p
	 * @param rests
	 * @return
	 */
	private Job nearestJob(Point p, ArrayList<Job> rests){
		return Collections.min(rests, new P2JComparator(p));
	}

	private double distanceOf(Job j1,Job j2){
		//System.out.println(j1.midPoint().getX()+" "+j2.midPoint().getX());
		return Math.abs(j1.midPoint().getX() - j2.midPoint().getX())+
				Math.abs(j1.midPoint().getY() - j2.midPoint().getY());
	}

	private double distanceOf(Point p,Job j){
		return Math.abs(p.getX() - j.midPoint().getX())+
				Math.abs(p.getY() - j.midPoint().getY());
	}

	private double distanceOf(Point p1, Point p2){
		return Math.abs(p1.getX() - p2.getX())+
				Math.abs(p1.getY() - p2.getY());
	}

	private ArrayList<Job> getRest(ArrayList<Job> visited){
		ArrayList<Job> newList = new ArrayList<Job>();
		
		for( Job j : jobs){
			if(!visited.contains(j)){
				newList.add(j);
			}
		}
		return newList;
	}


	// for DEBUG 
	public void printList(ArrayList<Job> list){
		for(Job each : list){
			each.print();
		}
	}

	public void printList(){
		for(Job each : jobs){
			each.print();
		}
	}
	
	private void removeInitial(ArrayList<Job> list){
		for(int i = 0; i< list.size();i++){
			Job each = list.get(i);
			if(each.equals(new Job(0,0,0,0))){
				list.remove(each);
				if(debug){
					System.out.println("@removeInitial : initial detected");
				}
			}
		}
	}

}

