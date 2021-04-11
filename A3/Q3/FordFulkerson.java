import java.util.*;
import java.io.File;

public class FordFulkerson {

	public static ArrayList<Integer> pathDFS(Integer source, Integer destination, WGraph graph){
		
		ArrayList<Integer> path = new ArrayList<Integer>();

		// build adjacency list
		LinkedList<Integer>[] adjList = new LinkedList[graph.getNbNodes()];
		for (int i=0; i<adjList.length; i++) { // add LLs for adjList
			adjList[i] = new LinkedList<Integer>();
		}
		for (Edge edge : graph.getEdges()) { // fill with vertices
			if (graph.getEdge(edge.nodes[0], edge.nodes[1]).weight > 0) { // only add neighbours if residual capacity > 0
				adjList[edge.nodes[0]].add(edge.nodes[1]);
			}	
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

			//System.out.println("stack: " + stack);
			//System.out.println("path: " + path);

			current = stack.pop();
			while (current == -1) {
				path.remove(path.size()-1);
				if (stack.isEmpty()) {
					return new ArrayList<Integer>();
				}
				current = stack.pop();
			}

			path.add(current);
			//System.out.println("exploring vertex: " + current);
			visited[current] = true;

			if (current == destination) {
				//System.out.println("reached destination " + destination + ", path is: " + path);
				return path;
			}

			//if (!adjList[current].isEmpty()) {
				stack.push(-1);
			//} 

			for (Integer node : adjList[current]) { // explore neighbours
				if ((visited[node] == false))  { 
					stack.push(node);
					visited[node] = true;
				}
			}
		}
			
		return new ArrayList<Integer>();
	}

	
	public static void print_adjacencyList(LinkedList<Integer>[] list) {
		System.out.println("ADJACENCY LIST: ");
		for (int i=0; i<list.length; i++) {
			System.out.print("vertex " + i + " -> ");
			for (int k=0; k<list[i].size(); k++) {
				System.out.print("Vertex " + list[i].get(k) + ", ");
			}
			System.out.println();
		}
	}


	public static String fordfulkerson(WGraph graph) {
		String answer="";
		int maxFlow = 0;

		/* initial flow graph with flows set to 0 */
		WGraph flowGraph = new WGraph(graph);
		int source, destination;
		for (int i=0; i<flowGraph.getEdges().size(); i++) { // set flow to 0 
			// forwards edges 
			source = flowGraph.getEdges().get(i).nodes[0];
			destination = flowGraph.getEdges().get(i).nodes[1];
			flowGraph.setEdge(source, destination, 0);
		}

		/* initial residual graph */
		WGraph residualGraph = new WGraph(graph);
		int ce, fe;
		for (Edge edge : residualGraph.getEdges()) {

			ce = graph.getEdge(edge.nodes[0], edge.nodes[1]).weight;
			fe = flowGraph.getEdge(edge.nodes[0], edge.nodes[1]).weight;

			// forwards edges 
			residualGraph.setEdge(edge.nodes[0], edge.nodes[1], ce); // since no flow

			// backwards edges
			residualGraph.setEdge(edge.nodes[1], edge.nodes[0], 0); // since there is no flow, all backwards edges will be 0
		}

		/*
		for (Edge edge : residualGraph.getEdges()) {
			System.out.println(edge.weight);
		}
		*/

		Integer s = graph.getSource();
		Integer t = graph.getDestination();
		boolean pathExists; // path from source to sink in residual graph

		int ctr = 0; //testing

		do { 

			ctr++;
			ArrayList<Integer> P = pathDFS(s, t, residualGraph);

			pathExists = (!P.isEmpty());

			// f' = augment(f, P), update f to f'
			augment(P, flowGraph, residualGraph);

			// update Gf 
			for (Edge edge : residualGraph.getEdges()) {

				//System.out.println("looking at edge: " + edge.nodes[0] + "," + edge.nodes[1]);

				ce = graph.getEdge(edge.nodes[0], edge.nodes[1]).weight;
				fe = flowGraph.getEdge(edge.nodes[0], edge.nodes[1]).weight;
				//System.out.println("ce is: " + ce);
				//System.out.println("fe is: " + fe);
				if (fe <= ce) {
					// put forward edge (u,v) in Ef with residual capacity cf(e)=c(e)–f(e)
					residualGraph.setEdge(edge.nodes[0], edge.nodes[1], ce - fe);
				}

				if (fe > 0) {
					// put backwards edge (v,u) in Ef with residual capacity cf(e) = f(e)
					residualGraph.setEdge(edge.nodes[1], edge.nodes[0], fe);
				}
				//System.out.println();
			}

			/*
			System.out.println("FLOW GRAPH: ");
			for (Edge edge : flowGraph.getEdges()) {
				System.out.println(edge.weight);
			}

			System.out.println("RESIDUAL GRAPH: ");
			for (Edge edge : residualGraph.getEdges()) {
				System.out.println(edge.weight);
			}
			*/

		} while (pathExists);
		//} while (ctr<3); // for testing 


		for (Edge edge : flowGraph.getEdges()) {
			if (edge.nodes[0] == s) {
				maxFlow += edge.weight;
			}
		}

		answer += maxFlow + "\n" + flowGraph.toString();	
		return answer;
	}

	public static void augment(ArrayList<Integer> p, WGraph f, WGraph r) {

		int rc; // residual capacity
		Integer b = Integer.MAX_VALUE; // bottleneck minimum capacity
		for (int i=0; i<p.size()-1; i++) { // find b
			//System.out.println("vertices: " + p.get(i) + "," + p.get(i+1));
			rc = r.getEdge(p.get(i), p.get(i+1)).weight;
			if (rc < b) {
				b = rc;
			}
		}

		Integer u, v;
		for (int i=0; i<p.size()-1; i++) { 
			u = p.get(i);
			v = p.get(i+1);

			if (r.getEdge(u,v) != null) { // if e is a forward edge
				f.setEdge(u, v, f.getEdge(u, v).weight + b);
			} else { // if e is a backward edge
				f.setEdge(u, v, f.getEdge(u, v).weight - b);
			}
		}
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

