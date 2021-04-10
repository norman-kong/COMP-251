import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Vaccines {
	
	public class Country{
		private int ID;
		private int vaccine_threshold;
		private int vaccine_to_receive;
		private ArrayList<Integer> allies_ID;
		private ArrayList<Integer> allies_vaccine; 
		public Country() {
			this.allies_ID = new ArrayList<Integer>();
			this.allies_vaccine = new ArrayList<Integer>();
			this.vaccine_threshold = 0;
			this.vaccine_to_receive = 0;
		}

		public Country(ArrayList<Integer> a1, ArrayList<Integer>a2, int a3, int a4, int id) {
			this.allies_ID = a1;
			this.allies_vaccine = a2;
			this.vaccine_threshold = a3;
			this.vaccine_to_receive = a4;
			this.ID = id;
		}

		public int get_ID() {
			return this.ID;
		}
		public int get_vaccine_threshold() {
			return this.vaccine_threshold;
		}
		public ArrayList<Integer> get_all_allies_ID() {
			return allies_ID;
		}
		public ArrayList<Integer> get_all_allies_vaccine() {
			return allies_vaccine;
		}
		public int get_allies_ID(int index) {
			return allies_ID.get(index);
		}
		public int get_allies_vaccine(int index) {
			return allies_vaccine.get(index);
		}
		public int get_num_allies() {
			return allies_ID.size();
		}
		public int get_vaccines_to_receive() {
			return vaccine_to_receive;
		}
		public void set_allies_ID(int value) {
			allies_ID.add(value);
		}
		public void set_allies_vaccine(int value) {
			allies_vaccine.add(value);
		}
		public void set_ID(int value) {
			this.ID = value;
		}
		public void set_vaccine_threshold(int value) {
			this.vaccine_threshold = value;
		}
		public void set_vaccines_to_receive(int value) {
			this.vaccine_to_receive = value;
		}
		
	}
	
	public int vaccines(Country[] graph) {
		
		boolean[] visited = new boolean[graph.length];
		for (int i=1; i<graph.length; i++) {
			visited[i] = false; // true = sharing, false = not sharing
		}

		// first country stops sharing
		Queue<Integer> q = new LinkedList<>(); // queue holding country IDs
		q.add(1);
		visited[0] = true;
		
		Country ally, u;

		while (!q.isEmpty()) { 

			u = graph[q.remove()-1];

			for (int i=0; i<u.get_num_allies(); i++) {

				ally = graph[u.get_allies_ID(i)-1];
				ally.set_vaccines_to_receive(ally.get_vaccines_to_receive()-u.get_allies_vaccine(i));
				if (ally.get_vaccine_threshold() > ally.get_vaccines_to_receive()) { // ally also stops sharing
					if (visited[ally.get_ID()-1] == false) {
						q.add(ally.get_ID());
						visited[ally.get_ID()-1] = true;
					}
				}
			}
		}

		int notSharing = 0;
		Country node; 
		for (int j=0; j<graph.length; j++) {
			node = graph[j];
			//System.out.println("Looking at node: " + node.get_ID());
			//System.out.println("vaccines in: " + node.get_vaccines_to_receive(), "threshold: " + node.get_vaccine_threshold());
			if (node.get_vaccine_threshold() > node.get_vaccines_to_receive()) {
				notSharing++;
				//System.out.println("Node that won't share: " + node.get_ID());
			} 
		}

		node = graph[0];
		if (node.get_vaccine_threshold() > node.get_vaccines_to_receive()) {
			return graph.length-notSharing;
		} else {
			notSharing++;
		}

		return graph.length-notSharing;
	}

	public void test(String filename) throws FileNotFoundException {
		Vaccines hern = new Vaccines();
		Scanner sc = new Scanner(new File(filename));
		int num_countries = sc.nextInt();
		Country[] graph = new Country[num_countries];
		for (int i=0; i<num_countries; i++) {
			graph[i] = hern.new Country(); 
		}
		for (int i=0; i<num_countries; i++) {
			if (!sc.hasNext()) {
                sc.close();
                sc = new Scanner(new File(filename + ".2"));
            }
			int amount_vaccine = sc.nextInt();
			graph[i].set_ID(i+1);
			graph[i].set_vaccine_threshold(amount_vaccine);
			int other_countries = sc.nextInt();
			for (int j =0; j<other_countries; j++) {
				int neighbor = sc.nextInt();
				int vaccine = sc.nextInt();
				graph[neighbor -1].set_allies_ID(i+1);
				graph[neighbor -1].set_allies_vaccine(vaccine);
				graph[i].set_vaccines_to_receive(graph[i].get_vaccines_to_receive() + vaccine);
			}
		}
		sc.close();
		int answer = vaccines(graph);
		System.out.println(answer);

		/*
		ArrayList<Integer> IDs1 = new ArrayList();
		IDs1.add(2);
		ArrayList<Integer> Vaccines1 = new ArrayList();
		Vaccines1.add(10);

		ArrayList<Integer> IDs2 = new ArrayList();
		IDs2.add(3);
		IDs2.add(4);
		ArrayList<Integer> Vaccines2 = new ArrayList();
		Vaccines2.add(10);
		Vaccines2.add(10);

		ArrayList<Integer> IDs3 = new ArrayList();
		IDs3.add(2);
		ArrayList<Integer> Vaccines3 = new ArrayList();
		Vaccines3.add(10);

		ArrayList<Integer> IDs4 = new ArrayList();
		IDs4.add(2);
		ArrayList<Integer> Vaccines4 = new ArrayList();
		Vaccines4.add(10);

		// allies_ID, allies_vaccine, vaccine_threshold, vaccine_to_receive, ID
		Country c1 = new Country(IDs1, Vaccines1, 0, 0, 1);
		Country c2 = new Country(IDs2, Vaccines2, 25, 30, 2);
		Country c3 = new Country(IDs3, Vaccines3, 10, 10, 3);
		Country c4 = new Country(IDs4, Vaccines4, 10, 10, 4);

		Country[] graph = {c1, c2, c3, c4};

		System.out.println("FINAL ANSWER: " + vaccines(graph));
		*/

	}

	public static void main(String[] args) throws FileNotFoundException{

		Vaccines vaccines = new Vaccines();
		vaccines.test(args[0]); 
	}

}
