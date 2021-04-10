import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Honors {
	
	static class Vertex {
		int x, y, v; 
		int color = 0; // 0 is white, 1 is grey, 2 is black
		Integer d = Integer.MAX_VALUE; // distance to source

		Vertex(int x, int y, int v) {
			this.x = x;
			this.y = y;
			this.v = v;
		}
	}

	public static int min_moves(int[][] board) {

		if (board[0][0] == 0) {
			return -1;
		}

		int length = board.length;
		int width = board[0].length;

		// store vertices in one place
		Vertex[][] vertices = new Vertex[length][width];
		for (int i=0; i<length; i++) {
			for (int k=0; k<width; k++) {
				vertices[i][k] = new Vertex(i, k, board[i][k]);
			}
		}

		/*
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
		*/

		//print_adjacencyList(adjacencyList);

		Queue<Vertex> q = new LinkedList<>();

		Vertex s = new Vertex(0, 0, board[0][0]);
		s.color = 1;
		s.d = 0; 
		q.add(s);

		int N, E, S, W, x, y, tmp;

		while (!q.isEmpty()) {
			Vertex u = q.remove();
			//System.out.println("Exploring vertex: " + u.num);

			x = u.x;
			y = u.y;

			tmp = board[x][y];

			N = x - tmp;
			E = y + tmp;
			S = x + tmp;
			W = y - tmp;

			if (N >= 0) {
				if (vertices[N][y].color == 0) {
					vertices[N][y].color = 1;
					vertices[N][y].d = u.d+1;
					//vertices[N][y].pi = u;
					q.add(vertices[N][y]);
				}
			}

			if (E < board[0].length) {
				if (vertices[x][E].color == 0) {
					vertices[x][E].color = 1;
					vertices[x][E].d = u.d+1;
					//vertices[x][E].pi = u;
					q.add(vertices[x][E]);
				}
			}

			if (S < board.length) {
				if (vertices[S][y].color == 0) {
					vertices[S][y].color = 1;
					vertices[S][y].d = u.d+1;
					//vertices[S][y].pi = u;
					q.add(vertices[S][y]);
				}
			}

			if (W >= 0) {
				if (vertices[x][W].color == 0) {
					vertices[x][W].color = 1;
					vertices[x][W].d = u.d+1;
					//vertices[x][W].pi = u;
					q.add(vertices[x][W]);
				}
			}
			u.color = 2;
		}

		int answer = vertices[board.length-1][board[0].length-1].d; // get destination square (last square, bottom right)
		if (answer == Integer.MAX_VALUE) {
			return -1;
		} else {
			return answer;
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
		
		/*
		for (int i=1; i<=30; i++) {
			System.out.println("testing grid number: " + i);
			String testBoardFile = "Grid-" + i + ".in";
			honors.test(testBoardFile);
			String answerFile = "Grid-" + i + ".ans";
			Scanner sc = new Scanner(new File(answerFile));
			int expected = sc.nextInt();
			System.out.println("Expected answer: " + expected);
		}
		*/
		
	}

}
