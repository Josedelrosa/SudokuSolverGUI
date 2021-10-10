
public class SudokuSolver {
    private int[][] board;    
	public static final int EMPTY = 0; // empty cell
	public static final int SIZE = 9; // size of our Sudoku grid
	
	   public SudokuSolver(int[][] board) {
		   this.board = new int[SIZE][SIZE];
		   
		   for(int i = 0; i < SIZE; i++) {
			   for (int j = 0; j <SIZE; j++) {
				   this.board[i][j] = board[i][j];
				   
			   }
			   
		   }
		   
	   }
	   
	   // we check if a possible number is already in a row
		private boolean isInRow(int row, int number) {
			for (int i = 0; i < SIZE; i++)
				if (board[row][i] == number)
					return true;
			
			return false;
		}
		
		// we check if a possible number is already in a column
		private boolean isInCol(int col, int number) {
			for (int i = 0; i < SIZE; i++)
				if (board[i][col] == number)
					return true;
			
			return false;
		}
		
		// we check if a possible number is in its 3x3 box
		private boolean isInBox(int row, int col, int number) {
			int r = row - row % 3;
			int c = col - col % 3;
			
			for (int i = r; i < r + 3; i++)
				for (int j = c; j < c + 3; j++)
					if (board[i][j] == number)
						return true;
			
			return false;
		}
		
		// combined method to check if a number possible to a row, col position is ok
		private boolean isOk(int row, int col, int number) {
			return !isInRow(row, number)  &&  !isInCol(col, number)  &&  !isInBox(row, col, number);
		}
		
		// Solve method using a recursive BackTracking algorithm.	
	       public boolean solve() {
	        for (int row = 0; row < SIZE; row++) {
	         for (int col = 0; col < SIZE; col++) {
	          // we search an empty cell
	          if (board[row][col] == EMPTY) {
	            // we try possible numbers
	            for (int number = 1; number <= SIZE; number++) {
	              if (isOk(row, col, number)) {
	                // number ok. it respects sudoku constraints
	                board[row][col] = number;

	                if (solve()) {	                	
	                	// we start backtracking recursively
	                  return true;
	                } else { // if not a solution, we empty the cell and we continue
	                  board[row][col] = EMPTY;
	                }
	             }
	            }
	            

	            return false; // we return false
	           }
	          }
	         }

	         return true; // sudoku solved
		}
		//returns the puzzle to display
		public int[][] display() {
			return board;
		}	

	   
}
