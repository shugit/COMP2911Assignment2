import java.util.Comparator;


public class PQComparator implements Comparator<ASJob> {

	public int compare(ASJob j1, ASJob j2) {
		Integer o1 = j1.getCx();
		Integer o2 = j2.getCx();
		return o1.compareTo(o2);
	}

}
