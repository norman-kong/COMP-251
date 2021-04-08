import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Honors {
	
	static class Vertex {
		int x, y, v, num; 
		int color = 0; // 0 is white, 1 is grey, 2 is black
		Integer d = Integer.MAX_VALUE; // distance to source
		Vertex pi = null; // TODO: what is this??
		//LinkedList<Vertex> adjacentVertices = ;

		Vertex(int x, int y, int v, int num) {
			this.x = x;
			this.y = y;
			this.v = v;
			this.num = num;
		}
	}

	public static int min_moves(int[][] board) {

		if (board[0][0] == 0) {
			return -1;
		}

		// store vertices in one place
		Vertex[][] vertices = new Vertex[board.length][board[0].length];
		for (int i=0; i<board.length; i++) {
			for (int k=0; k<board[0].length; k++) {
				vertices[i][k] = new Vertex(i, k, board[i][k], i*board[0].length + k);
			}
		}


		// build adjacency list
		LinkedList<LinkedList<Vertex>> adjacencyList = new LinkedList<>();
		int N, E, S, W, current = 0;

		for (int x=0; x<board.length; x++) {
			for (int y=0; y<board[0].length; y++) {
				N = x - board[x][y];
				E = y + board[x][y];
				S = x + board[x][y];
				W = y - board[x][y];

				adjacencyList.add(new LinkedList<Vertex>()); 

				if (N >= 0) adjacencyList.get(current).add(vertices[N][y]);
				if (E < board[0].length) adjacencyList.get(current).add(vertices[x][E]);
				if (S < board.length) adjacencyList.get(current).add(vertices[S][y]);
				if (W >= 0) adjacencyList.get(current).add(vertices[x][W]);
				current++;
			}
		}

		//print_adjacencyList(adjacencyList);

		Queue<Vertex> q = new LinkedList<>();

		Vertex s = new Vertex(0, 0, board[0][0], 0);
		s.color = 1;
		s.d = 0; 
		s.pi = null;
		q.add(s);

		while (!q.isEmpty()) {
			Vertex u = q.remove();
			//System.out.println("Exploring vertex: " + u.num);
			for (Vertex v : adjacencyList.get(u.num)) {
				if (v.color == 0) {
					v.color = 1;
					v.d = u.d+1;
					v.pi = u;
					q.add(v);
					//System.out.println("colouring vertex " + v.num + " grey and distance is: " + v.d);
				}
			}
			u.color = 2;
		}

		int prev = adjacencyList.get((board.length-1)*(board[0].length-1)).get(0).num-1;
		//System.out.println(prev);

		int answer = vertices[board.length-1][board[0].length-1].d; // get destination square (last square, bottom left)
		if (answer == Integer.MAX_VALUE) {
			return -1;
		} else {
			return answer;
		}
	}

	public static void print_adjacencyList(LinkedList<LinkedList<Vertex>> list) {
		System.out.println("ADJACENCY LIST: ");
		for (int i=0; i<list.size(); i++) {
			System.out.print("vertex " + i + " : ");
			for (int k=0; k<list.get(i).size(); k++) {
				System.out.print(list.get(i).get(k).num + " ");
			}
			System.out.println();
		}
	}

	public void test(String filename) throws FileNotFoundException{
		Scanner sc = new Scanner(new File(filename));
		int num_rows = sc.nextInt();
		int num_columns = sc.nextInt();
		int [][]board = new int[num_rows][num_columns];
		for (int i=0; i<num_rows; i++) {
			char line[] = sc.next().toCharArray();
			for (int j=0; j<num_columns; j++) {
				board[i][j] = (int)(line[j]-'0');
			}
			
		}
		sc.close();

		int answer = min_moves(board);
		System.out.println("My answer: " + answer);
	}

	public static void main(String[] args) throws FileNotFoundException{
		Honors honors = new Honors();

		/*
		int[][] board = {
							{1, 1},
							{1, 1}
						}; 
		int answer = min_moves(board); 
		//System.out.println(answer);
		*/
		
		for (int i=1; i<=30; i++) {
			System.out.println("testing grid number: " + i);
			String testBoardFile = "Grid-" + i + ".in";
			honors.test(testBoardFile);
			String answerFile = "Grid-" + i + ".ans";
			Scanner sc = new Scanner(new File(answerFile));
			int expected = sc.nextInt();
			System.out.println("Expected answer: " + expected);
		}
		
	}

}
