import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;


public class CourierGraph{
	public static boolean debug = false;

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
			toVisit.add(new ASJob(j,this.hx(j, newRests) + this.gx(j,fromJobs), fromJobs) );

		}

		// step 2, while toVisit is not empty
		while (!toVisit.isEmpty()) {
			if(debug){
				System.out.println("******ASJob List****");
				for(ASJob test: toVisit){
					test.getJob().print();
					test.print();
				}
				System.out.println("***end of ASJob List****");
			}
			// step 3, get asj from toVisit
			ASJob asj = toVisit.remove();
			// setp 4, if it's not goal(which means all job not cleared)
			if(debug){
				System.out.print("This Job is ");
				asj.getJob().print();
				System.out.println("Reach node from");
				this.printList(asj.getFrom());
			}
			ArrayList<Job> rests = (ArrayList<Job>) this.getRest(asj.getFrom()).clone();
			rests.remove(asj.getJob());
			if (rests.isEmpty()) {
				System.out.println((toVisit.size()+asj.getFrom().size())+" nodes explores");
				System.out.println("cost = "+this.gx(asj.getJob(), asj.getFrom()));
				asj.getFrom().add(asj.getJob());
				//this.printList(asj.getFrom());
				this.printPath(asj.getFrom());
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

					double gx = this.gx(eachJob,newFromJobs);
					//System.out.println("testflag");
					toVisit.add(new ASJob(eachJob,gx+hx,newFromJobs));
				}
			}
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


		// j->nearest
		// nearest -> next nearest
		Job current = aCurrent;

		Job next = this.nearestJob(current.getEnd(), rests);
		double hx = this.distanceOf(current.getEnd(),next);

		while(!rests.get(rests.size()-1).equals(next)){
			rests.remove(current);
			current = next;
			next = this.nearestJob(current, rests);
			//hx += current.length()*0.5;
			//hx += this.distanceOf(current.getEnd(), next);
			hx += this.distanceOf(current, next);
		}

		if(debug){
			System.out.println("hx = "+hx);
		}

		return hx;

		//return 0;

	}


	/**
	 * get g(x), which is the length from starting point to end point consists of job length
	 * @param fromJobs
	 * @param j
	 * @return
	 */
	private int gx(Job j,ArrayList<Job> fromJobs){
		int gx = 0;
		//System.out.print("@gx the list is ");
		//j.print();
		//printList(fromJobs);
		Point lastPoint;
		if (fromJobs.size() == 0){ // Job j is the first
			lastPoint = new Point(0,0);
		} else {
			gx += distanceOf(new Point(0,0), fromJobs.get(0).getStart());
			gx += fromJobs.get(0).length();
			lastPoint = fromJobs.get(0).getEnd();

			if(fromJobs.size() > 1){
				for(int i = 1; i < fromJobs.size() ; i++){
					Job pre = fromJobs.get(i-1);
					Job next = fromJobs.get(i);
					gx += distanceOf(pre.getEnd(),next.getStart());
					gx += next.length();
					lastPoint = next.getEnd();
				}
			}

		}
		gx += this.distanceOf(lastPoint, j.getStart()); 
		gx += j.length();
		return gx;

	}


	/**
	 * calculate a nearest job based on current job, not end point.
	 * @param j1
	 * @param rests
	 * @return
	 */
	private Job nearestJob(Job j1,ArrayList<Job> rests){
		//this.removeInitial(rests);
		//this.printList(rests);
		//j1.print();
		ArrayList<Job> jobList = new ArrayList<Job>();
		for(Job j2 : rests){
			if(!j1.equals(j2)){
				jobList.add(j2);
			}
		}
		Job j = j1;
		if( jobList.size() == 0){
			return j;
		}else {
			j = Collections.min(jobList, new JobComparator(j1));
		}
		//for debug
		if(debug){
			System.out.println("@nearestJob");
			this.printList(jobList);
			System.out.print("gonna return:");
			j.print();
		}
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

	/**
	 * 
	 * @param j1
	 * @param j2
	 * @return a straight distance
	 */
	private double distanceOf(Job j1,Job j2){
		//System.out.println(j1.midPoint().getX()+" "+j2.midPoint().getX());


		return Math.sqrt(Math.pow(j1.midPoint().getX() - j2.midPoint().getX(),2) +
				Math.pow(j1.midPoint().getY() - j2.midPoint().getY(),2 ));
	}

	/**
	 * straight distance
	 * @param p
	 * @param j
	 * @return 
	 */
	private double distanceOf(Point p,Job j){
		return Math.sqrt(Math.pow(p.getX() - j.midPoint().getX(),2)+
				Math.pow(p.getY() - j.midPoint().getY(),2));
	}

	/**
	 * Manhatten distance
	 * @param p1
	 * @param p2
	 * @return
	 */
	private int distanceOf(Point p1, Point p2){
		return (int) (Math.abs(p1.getX() - p2.getX())+
				Math.abs(p1.getY() - p2.getY()));
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


	public void printPath(ArrayList<Job> list){
		for(int i = 1; i < list.size(); i++){
			Job cur = list.get(i-1);
			Job next = list.get(i);
			if(list.indexOf(cur) == 0){
				if(cur.getStart().equalsTo(new Point(0,0))){

				}else{
					System.out.print("Move from 0 0 to ");
					cur.getStart().print();
					System.out.println();
					
				}
				System.out.print("Carry from");
				cur.print();
			} 

			if (cur.getEnd().equalsTo(next.getStart())){

			} else {
				System.out.print("Move from ");
				cur.getEnd().print();
				System.out.print(" to ");
				next.getStart().print();
			}


			System.out.println();
			System.out.print("Carry from");
			next.print();
		}
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


}

