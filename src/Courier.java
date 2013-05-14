import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Courier {

	private static final boolean debug = false;
	private static final boolean upload = false;

	public static void main(String[] args){
		CourierGraph g = new CourierGraph();

		if(!debug){
			try	{
				Scanner sc;
				if(!upload){
					sc = new Scanner(new FileReader("F:\\study\\COMP2911\\AssignmentTwo\\src\\input.in"));
				} else {
					sc = new Scanner(new FileReader(args[0]));
				}
				while(sc.hasNextLine()){
					String line = sc.nextLine();
					String[] commands = line.split(" ");
					int x1 = tranInt(commands[1]);
					int y1 = tranInt(commands[2]);
					int x2 = tranInt(commands[4]);
					int y2 = tranInt(commands[5]);
					//System.out.println(x1+" "+y1+" "+x2+" "+y2);
					if (commands.length == 6 && commands[0].equalsIgnoreCase("Job") && commands[3].equalsIgnoreCase("to")
							&& x1!= -1 && y1!= -1 &&  x2!= -1 && y2!= -1){
						g.addJob(x1, y1, x2, y2);
					} else {

					}
				}
				sc.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			g.addJob(3, 3, 5, 3);
			g.addJob(1, 1, 9, 2);		
			g.addJob(3, 5, 2, 7);
			g.addJob(5, 5, 5, 7);
		}
		if(debug){
			System.out.print("@main:	");
			g.printList();
		}
		g.route();
	}


	public static int tranInt(String s){  
		try  
		{   
			return Integer.parseInt(s);  
		}  
		catch(Exception e)  
		{  
			return -1;  
		}  
	}  
}
