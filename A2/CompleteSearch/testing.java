import java.util.*;
import java.lang.*;
import java.io.*;


public class Game {
	
	Board sudoku;

	public Board getBoard() {
		return sudoku;
	}
	
	public class Cell{
		private int row = 0;
		private int column = 0;
		
		public Cell(int row, int column) {
			this.row = row;
			this.column = column;
		}
		public int getRow() {
			return row;
		}
		public int getColumn() {
			return column;
		}
	}
	
	public class Region{
		private Cell[] matrix;
		private int num_cells;
		public Region(int num_cells) {
			this.matrix = new Cell[num_cells];
			this.num_cells = num_cells;
		}
		public Cell[] getCells() {
			return matrix;
		}
		public void setCell(int pos, Cell element){
			matrix[pos] = element;
		}
		
	}
	
	public class Board{
		private int[][] board_values;
		private Region[] board_regions;
		private int num_rows;
		private int num_columns;
		private int num_regions;
		
		public Board(int num_rows,int num_columns, int num_regions){
			this.board_values = new int[num_rows][num_columns];
			this.board_regions = new Region[num_regions];
			this.num_rows = num_rows;
			this.num_columns = num_columns;
			this.num_regions = num_regions;
		}
		
		public int[][] getValues(){
			return board_values;
		}
		public int getValue(int row, int column) {
			return board_values[row][column];
		}
		public Region getRegion(int index) {
			return board_regions[index];
		}
		public Region[] getRegions(){
			return board_regions;
		}
		public void setValue(int row, int column, int value){
			board_values[row][column] = value;
		}
		public void setRegion(int index, Region initial_region) {
			board_regions[index] = initial_region;
		}	
		public void setValues(int[][] values) {
			board_values = values;
		}

			// helper method
		private void print_board() {
       		for (int r = 0; r < this.num_rows; r++) {
            	for(int c = 0 ; c < this.num_columns; c++) {
                	System.out.print(this.getValue(r, c));
            	}
            	System.out.println();
        	}
       		System.out.println("----------------------------");
    	}

	}
	
	public int[][] solver() {
		
		// todo: sort regions in ascending order to tackle smallest regions first 
		recursion();
		
		return sudoku.getValues();
	}


	public boolean checkIfLegal(Board board, Cell[] region, Cell cell, int placement_value) {

		boolean legal = true;

		// check if placement is already in region
		for (int j=0; j<region.length; j++) { // iterate over all cells in this region
			if (placement_value == board.getValue(region[j].getRow(), region[j].getColumn())) { // value is already in region
				legal = false;
			}
		}

		// check if placement is adjacent to the same number
		for (int x=0; x<3; x++) {
			int row = cell.getRow()-1+x;
			//System.out.println("row: " + row);
			for (int y=0; y<3; y++) {
				int column = cell.getColumn()-1+y;
				//System.out.println("(row, column) = " + "(" + row + "," + column +")");

				if (row < 0 || row >= board.num_rows) { // if not in range x
					continue; // don't check neighbour as it is out of bounds
				} else if (column < 0 || column >= board.num_columns) { // if not in range y
					continue; // don't check neighbour as it is out of bounds
				} else if (cell.getRow() == row && cell.getColumn() == column) { // don't compare with original cell 
					continue;
				}

				//System.out.println("comparing with cell: " + "(" + row + "," + column +")" + ":" + board.getValue(row, column));

				if (placement_value == board.getValue(row, column) ) { // check if adjacent
					//System.out.println("can't add, adjacent");
					legal = false;
					break;			
				}
				if (!legal) {
					break;
				}
			}

			if (board.getValue(cell.getRow(), cell.getColumn()) != -1) { // if visited already, don't modify
				legal = false;
			}
		}

		return legal;

	}

	public boolean recursion() {

		for (int region_index=0; region_index<this.getBoard().getRegions().length; region_index++) { // iterate over all regions

			Cell[] region = this.getBoard().getRegions()[region_index].getCells();

			for (int i=0; i<region.length; i++) { // iterate over cells in region

				Cell cell = region[i];

				if (this.getBoard().getValue(cell.getRow(), cell.getColumn()) == -1) {

					for (int placement_value=1; placement_value<=region.length; placement_value++) { // iterate over all possible values for a cell in this region

						//System.out.println("Working on region: " + region_index);
						//System.out.println("Working on cell: " + region[i].getRow() + " " + region[i].getColumn());

						//System.out.println("Placement value: " + placement_value);
						/* check to see if placement value is compatible with current board */
						boolean legal = checkIfLegal(this.getBoard(), region, region[i], placement_value);					

						if (legal) {
							this.getBoard().setValue(region[i].getRow(), region[i].getColumn(), placement_value); // place value
							this.getBoard().print_board();

							if (recursion()) {
								return true;
							} else {
								this.getBoard().setValue(region[i].getRow(), region[i].getColumn(), -1);	
							}	
						}
					} // iterate over placement values
					return false;
				} // if
			} // iterate over cells in region
		} // iterate over regions
		return true;
	}

	public static void main(String[] args) {
		
	try {
		File file = new File("test1.in");
		Scanner sc = new Scanner(file);
		int rows = sc.nextInt();
		int columns = sc.nextInt();
		int[][] board = new int[rows][columns];
		//Reading the board
		for (int i=0; i<rows; i++) {
			for (int j=0; j<columns; j++){
				String value = sc.next();
				if (value.equals("-")) {
					board[i][j] = -1;
				}else {
					try {
						board[i][j] = Integer.valueOf(value);
					}catch(Exception e) {
						System.out.println("Ups, something went wrong");
					}
				}	
			}
		}
		int regions = sc.nextInt();
		Game game = new Game();
	    game.sudoku = game.new Board(rows, columns, regions);
		game.sudoku.setValues(board);
		for (int i=0; i< regions;i++) {
			int num_cells = sc.nextInt();
			Game.Region new_region = game.new Region(num_cells);
			for (int j=0; j< num_cells; j++) {
				String cell = sc.next();
				String value1 = cell.substring(cell.indexOf("(") + 1, cell.indexOf(","));
				String value2 = cell.substring(cell.indexOf(",") + 1, cell.indexOf(")"));
				Game.Cell new_cell = game.new Cell(Integer.valueOf(value1)-1,Integer.valueOf(value2)-1);
				new_region.setCell(j, new_cell);
			}
			game.sudoku.setRegion(i, new_region);
		}
		System.out.println("Board without solution: ");
		game.sudoku.print_board();
		System.out.println("Starting to find solution: ");
		game.solver();
		game.sudoku.print_board();


		} catch(FileNotFoundException e){
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		/*
		for (int i=0; i<answer.length;i++) {
			for (int j=0; j<answer[0].length; j++) {
				System.out.print(answer[i][j]);
				if (j<answer[0].length -1) {
					System.out.print(" ");
				}
			}
			System.out.println();
		}*/
	}
}


