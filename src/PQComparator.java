import java.util.Comparator;


public class PQComparator implements Comparator<ASJob> {

	public int compare(ASJob j1, ASJob j2) {
		Double o1 = j1.getCx();
		Double o2 = j2.getCx();
		return o1.compareTo(o2);
	}

}
