import java.io.BufferedReader;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Scanner;

public class MiniTester {	
	
	private static void setPenalizationArray(int[] arr) {
		try {
			Class<?> clazz = Midterm.class;
			Object cc = clazz.newInstance();
	
			Field f1 = cc.getClass().getDeclaredField("penalization");
			f1.setAccessible(true);
			f1.set(cc, arr);
		} catch(Exception e) {
			e.printStackTrace();
			System.err.println("Failed to change penalization variable in Midterm class!");
		}
	}
	
	private static void testMidterm1() {
		System.out.println("----------TEST OUTPUT OF MIDTERM 1----------\n");

		int[] penalization = {216,190,171,59,62};
		setPenalizationArray(penalization);
		int expected = 501;
		int answer = Midterm.lost_marks(penalization);
		if (expected != answer)
			System.err.println("Failed Midterm test 1.\nOutput: " + answer + "\nExpected: " + expected + "\n");
		else
			System.out.println("Passed Midterm test 1 for the lost_marks method!"+ "\n");
	}
	
	private static void testMidterm2() {
		System.out.println("----------TEST OUTPUT OF MIDTERM 2----------\n");

		int[] penalization = {111,179,64,78,219,97,160,40};
		setPenalizationArray(penalization);
		int expected = 535;
		int answer = Midterm.lost_marks(penalization);
		if (expected != answer)
			System.err.println("Failed Midterm test 2.\nOutput: " + answer + "\nExpected: " + expected + "\n");
		else
			System.out.println("Passed Midterm test 2 for the lost_marks method!"+ "\n");
	}
	
	private static void testMidterm3() {
		System.out.println("----------TEST OUTPUT OF MIDTERM 3----------\n");

		int[] penalization = {84,235,135,229,154,185,4,114,86,106};
		setPenalizationArray(penalization);
		int expected = 745;
		int answer = Midterm.lost_marks(penalization);
		if (expected != answer)
			System.err.println("Failed Midterm test 3.\nOutput: " + answer + "\nExpected: " + expected + "\n");
		else
			System.out.println("Passed Midterm test 3 for the lost_marks method!"+ "\n");
	}
	
	private static void testMidterm4() {
		System.out.println("----------TEST OUTPUT OF MIDTERM 4----------\n");

		int[] penalization = {98,180,141,12,161,202,259,214,150,163,60,202,148,137,40,150,15,83,205,115,21,78,166,224,81};
		setPenalizationArray(penalization);
		int expected = 1056;
		int answer = Midterm.lost_marks(penalization);
		if (expected != answer)
			System.err.println("Failed Midterm test 4.\nOutput: " + answer + "\nExpected: " + expected + "\n");
		else
			System.out.println("Passed Midterm test 4 for the lost_marks method!"+ "\n");
	}
	
	private static void testMidterm5() {
		System.out.println("----------TEST OUTPUT OF MIDTERM 5----------\n");

		int[] penalization = {86,181,218,223,104,29,234,236,33,118,1,130,181,212,153,51,101,17,238,105,131,36,249,263,61,132,132,42,64,267,96,35,208,229,253,160,69,96,3,100,73,254,112,12,1,268,220,1,98,119};
		setPenalizationArray(penalization);
		int expected = 1205;
		int answer = Midterm.lost_marks(penalization);
		if (expected != answer)
			System.err.println("Failed Midterm test 5.\nOutput: " + answer + "\nExpected: " + expected + "\n");
		else
			System.out.println("Passed Midterm test 5 for the lost_marks method!"+ "\n");
	}
	
	
	
	
	public static void main(String[] args) {

		System.out.println("---------------------------------------------------------------------------------\n");
		System.out.println("STARTING TESTS");
		
		testMidterm1();
		testMidterm2();
		testMidterm3();
		testMidterm4();
		testMidterm5();

		System.out.println("---------------------------------------------------------------------------------\n");

		
	}
	
}