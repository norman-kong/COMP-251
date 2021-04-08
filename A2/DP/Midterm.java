import java.util.*;
import java.lang.*;
import java.io.*;

public class Midterm {
	private static int[][] dp_table;
	private static int[] penalization;
	

	public static void main(String[] args) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		int chairs;
		try {
			chairs = Integer.valueOf(reader.readLine());
			penalization = new int[chairs];
			for (int i=0; i< chairs; i++) {
				penalization[i] = Integer.valueOf(reader.readLine());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int answer = lost_marks(penalization);
		System.out.println(answer);
	}
	
	public static int lost_marks(int[] penalization) {
		int[][] table = new int[penalization.length][penalization.length];
		int index_behind, index_ahead;
		int forwards=0, backwards=0;
		int answer=9000000;

		for (int i=penalization.length-1; i>0; i--) {
			table[0][i] = 9000000;
		}

		table[0][0] = 0; // index 0, jump size 0: student starts here

		for (int jump=1; jump<penalization.length; jump++) {
			for (int index=penalization.length-1; index>=0; index--) {
				
				index_behind = index - jump;
				index_ahead = index + jump;

				if ((index_behind < 0) && (index_ahead > penalization.length-1)) {
					forwards = 9000000;
					backwards = 9000000;
				} else if (index_behind < 0) {
					forwards = 9000000;
					backwards = table[jump][index_ahead] + penalization[index];
				} else if (index_ahead > penalization.length-1) {
					backwards = 9000000;
					forwards = table[jump-1][index_behind] + penalization[index];
				} else {
					forwards = table[jump-1][index_behind] + penalization[index];
					backwards = table[jump][index_ahead] + penalization[index];
				}

				table[jump][index] = min(forwards, backwards);

				if (index == penalization.length-1) {
					if (answer > min(forwards, backwards)) {
						answer = min(forwards, backwards);
					}
				}
			}
		}
		//print_table(table);
		return answer;
	}

	public static void print_table(int[][] table) {
		for(int i = 0; i < table[0].length; i++){
            for(int j = 0; j < table[0].length; j++){
                if(table[i][j] >= 9000000){
                    System.out.print("-" + "\t");
                } else {
                    System.out.print(table[i][j] + "\t");
                }
            }
            System.out.println();
        }
	}

	public static int min(int num1, int num2) {
		if (num1 <= num2) {
			return num1;
		} else {
			return num2;
		}
	}

}
