import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class a1_real {
  
  public static int silence(int[] positions) {

        // hashmap slots are the 
        HashMap<Integer, Integer> hashMap = new HashMap<>(); // 10^9 possible languages 
        int min = positions.length; 
        int tempDistance; // computing temporary distance
  
        // populate map from array, find min distance as we add elements
        for (int i=0; i<positions.length; i++) { 

            if (hashMap.containsKey(positions[i])) { // student with that language is already in map: compute distance
                tempDistance = i - hashMap.get(positions[i]).intValue();
                if (tempDistance < min) { // update min distance
                  min = tempDistance;
                }
            } 
            hashMap.put(positions[i], i); 
        } 
        return min;
  }

  public static void main(String[] args) {
    try {
      String path = args[0];
          File myFile = new File(path);
          Scanner sc = new Scanner(myFile);
          int num_students = sc.nextInt();
          int[] positions = new int[num_students];
          for (int student = 0; student<num_students; student++){
        positions[student] =sc.nextInt();
          }
          sc.close();
          int answer = silence(positions);
          System.out.println(answer);
      } catch (FileNotFoundException e) {
          System.out.println("An error occurred.");
          e.printStackTrace();
      }
    }   
}