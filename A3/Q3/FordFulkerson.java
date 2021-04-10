import java.util.*;
import java.io.File;

public class FordFulkerson {

	static class Vertex {
		int node;
		Integer weight;

		Vertex(int node, Integer weight) {
			this.node = node; 
			this.weight = weight; 
		}

		public int getNode(){
			return this.node;
		}
	}

	public static ArrayList<Integer> pathDFS(Integer source, Integer destination, WGraph graph){
		
/*
		ArrayList<Integer> path = new ArrayList<Integer>();

		// build adjacency list
		LinkedList<Vertex>[] adjList = new LinkedList[graph.getNbNodes()];
		for (int i=0; i<adjList.length; i++) {
			adjList[i] = new LinkedList<Vertex>();
		}
		for (Edge edge : graph.getEdges()) {
			adjList[edge.getSource()].add(new Vertex(edge.getDestination(), edge.getWeight()));
		}

		boolean[] visited = new boolean[graph.getNbNodes()];
		for (int i=0; i<visited.length; i++) {
			visited[i] = false;
		}

		//print_adjacencyList(adjList);

		Stack<Integer> stack = new Stack<Integer>();
		stack.push(source);
		Integer current;

		while (!stack.isEmpty()) {

			current = stack.pop();
			path.add(current);
			//System.out.println("exploring vertex: " + current);
			visited[current] = true;

			if (current == destination) {
				//System.out.println("reached destination!");
				System.out.println("PATH IS: ");
				System.out.println("vertex: " + path);
				return path;
			}

			if (adjList[current].isEmpty()) {
				path.remove(current);
			}

			for (Vertex node : adjList[current]) { // explore neighbours
				if (visited[node.getNode()] == false) {
					stack.push(node.getNode());
					visited[node.getNode()] = true;
				}
			}

		}
		*/
			
		return new ArrayList<Integer>();
	}

	
	public static void print_adjacencyList(LinkedList<Vertex>[] list) {
		System.out.println("ADJACENCY LIST: ");
		for (int i=0; i<list.length; i++) {
			System.out.print("vertex " + i + " -> ");
			for (int k=0; k<list[i].size(); k++) {
				System.out.print("Vertex " + list[i].get(k).getNode() + ", ");
			}
			System.out.println();
		}
	}


	public static String fordfulkerson(WGraph graph){
		String answer="";
		int maxFlow = 0;
		
		pathDFS(0, 5, graph); 

		answer += maxFlow + "\n" + graph.toString();	
		return answer;
	}
	

	 public static void main(String[] args){
	 	// original
		String file = args[0];
		File f = new File(file);
		WGraph g = new WGraph(file);
	    System.out.println(fordfulkerson(g));

	    /*
	    // TESTING ADJACENCY LIST
	    Edge e0 = new Edge(0, 1, 1);
	    Edge e1 = new Edge(1, 2, 1);
	    Edge e2 = new Edge(1, 3, 1);
	    Edge e3 = new Edge(2, 3, 1);
	    Edge e4 = new Edge(2, 4, 1);
		
		ArrayList<Edge> edges = new ArrayList<Edge>();
		edges.add(e0);
		edges.add(e1);
		edges.add(e2);
		edges.add(e3);
		edges.add(e4);

		Integer n0 = 0;
		Integer n1 = 1;
		Integer n2 = 2;
		Integer n3 = 3;
		Integer n4 = 4;

		ArrayList<Integer> nodes = new ArrayList<Integer>();
		nodes.add(n0);
		nodes.add(n1);
		nodes.add(n2);
		nodes.add(n3);
		nodes.add(n4);

		int nb_nodes = 5;
		Integer source = 0;
		Integer destination = 4;

		WGraph test = new WGraph(edges, nodes, nb_nodes, source, destination);
		System.out.println("path is : " + FordFulkerson.pathDFS(0, 4, test));
		*/
	}
}

