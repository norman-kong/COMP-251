import java.util.*;

public class HW_Sched {
	ArrayList<Assignment> Assignments = new ArrayList<Assignment>();
	int m;
	int lastDeadline = 0;
	
	protected HW_Sched(int[] weights, int[] deadlines, int size) {
		for (int i=0; i<size; i++) {
			Assignment homework = new Assignment(i, weights[i], deadlines[i]);
			this.Assignments.add(homework);
			if (homework.deadline > lastDeadline) {
				lastDeadline = homework.deadline;
			}
		}
		m =size;
	}
	
	
	/**
	 * 
	 * @return Array where output[i] corresponds to the assignment 
	 * that will be done at time i.
	 */
	public int[] SelectAssignments() {
		
		/*
		System.out.println("Before sorting: ");
		for (int i=0; i<Assignments.size(); i++){
			System.out.println(Assignments.get(i).getWeight());
		}
		*/

		//Sort assignments in decreasing order
		Collections.sort(Assignments, new Assignment());

		/*
		System.out.println("After sorting: ");
		for (int i=0; i<Assignments.size(); i++){
			System.out.println(Assignments.get(i).getWeight());
		}
		*/
		
		// If homeworkPlan[i] has a value -1, it indicates that the 
		// i'th timeslot in the homeworkPlan is empty
		//homeworkPlan contains the homework schedule between now and the last deadline
		int[] homeworkPlan = new int[lastDeadline];
		for (int i=0; i < homeworkPlan.length; ++i) {
			homeworkPlan[i] = -1;
		}

		for (int i=0; i<Assignments.size(); i++) { // iterate over assignments
			if (Assignments.get(i).getDeadline() <= lastDeadline) {
				for (int j=Assignments.get(i).getDeadline()-1; j>=0; j--) { // schedule assignment in latest possible slot (while respecting its deadline)
					if (homeworkPlan[j] == -1) { // find empty slot 
						homeworkPlan[j] = Assignments.get(i).getNumber();
						break;
					}
				}
			}
		}
		return homeworkPlan;
	}
}
	



