import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.Border;

public class SudokuPanel extends JPanel implements KeyListener  {
 
	private static final Font FONT = new Font("Verdana", Font.CENTER_BASELINE, 20);
	private final JTextField[][] grid;   
    private final JPanel gridPanel;
    private final JPanel buttonPanel;
    private final JButton solveButton;
    private final JButton clearButton;
    private final JButton checkButton;
    private final JPanel[][] minisquarePanels;
    final int dimension = 9;
    JLabel seconds_left = new JLabel();
    JLabel errors = new JLabel();  
	int elapsedTime = 0;
	int seconds = 0;
	int minutes = 0;
	int counter = 1;
	Timer timer;
	String seconds_string = String.format("%02d", seconds);
	String minutes_string = String.format("%02d", minutes);	
	JTextField field = new JTextField();
    
    int[][] board = { 
			{ 3, 0, 6, 5, 0, 8, 4, 0, 0 },
            { 5, 2, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 8, 7, 0, 0, 0, 0, 3, 1 },
            { 0, 0, 3, 0, 1, 0, 0, 8, 0 },
            { 9, 0, 0, 8, 6, 3, 0, 0, 5 },
            { 0, 5, 0, 0, 9, 0, 6, 0, 0 },
            { 1, 3, 0, 0, 0, 0, 2, 5, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 7, 4 },
            { 0, 0, 5, 2, 0, 6, 3, 0, 0 } };    

	SudokuPanel(){	
		
		grid = new JTextField[9][9];		
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                JTextField field = new JTextField();
                grid[i][j] = field;   
                
                if(board[i][j] != 0) {
                    field.setText(String.valueOf(board[i][j]));
                    field.setEditable(false);
                   
                }
                else 
                {
                	field.setForeground(Color.BLUE);
                	field.addKeyListener(this);
                	field.addMouseListener(new MouseAdapter() {
                	    @Override
                	    public void mousePressed(MouseEvent e) {                	    	
             	    		for (JTextField[] row : grid) {
            	                for (JTextField field : row) {
            	                    field.setBackground(Color.WHITE);
            	                }
            	            }
                	    	field.setBackground(Color.YELLOW);          	    	
                      
                	         
                	    }
                	});
                	
                }

            }
        }
        
        gridPanel   = new JPanel();
        buttonPanel = new JPanel();
        timer();        
        Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
        Dimension fieldDimension = new Dimension(30, 30);

        for (int i = 0; i < dimension; ++i) {
            for (int j = 0; j < dimension; ++j) {
                JTextField field = grid[i][j];
                field.setBorder(border);
                field.setFont(FONT);
                field.setHorizontalAlignment(JTextField.CENTER);
                field.setPreferredSize(fieldDimension);
                
            }
        }               
        
        int minisquareDimension = (int) Math.sqrt(dimension);
        gridPanel.setLayout(new GridLayout(minisquareDimension,minisquareDimension));
        minisquarePanels = new JPanel[minisquareDimension][minisquareDimension];
        Border minisquareBorder = BorderFactory.createLineBorder(Color.BLACK, 2);
        
        for (int x = 0; x < minisquareDimension; x++) {
            for (int y = 0; y < minisquareDimension; y++) {
                JPanel panel = new JPanel();
                panel.setLayout(new GridLayout(minisquareDimension, minisquareDimension));
                panel.setBorder(minisquareBorder);
                minisquarePanels[x][y] = panel;
                gridPanel.add(panel);
            }
        }

        for (int x = 0; x < dimension; x++) {
            for (int y = 0; y < dimension; y++) {
                int minisquareX = x / minisquareDimension;
                int minisquareY = y / minisquareDimension;
                minisquarePanels[minisquareX][minisquareY].add(grid[x][y]);
            }
        }
        
        gridPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        clearButton = new JButton("Clear");
        solveButton = new JButton("Solve"); 
        checkButton = new JButton("Check All");        
        
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        buttonPanel.add(clearButton);
        buttonPanel.add(solveButton);
        buttonPanel.add(checkButton);
        buttonPanel.add(errors);
        
        seconds_left.setHorizontalAlignment(JLabel.RIGHT);
        seconds_left.setVerticalAlignment(JButton.BOTTOM);

        this.setLayout(new BorderLayout());
        this.add(gridPanel);
        this.add(buttonPanel, BorderLayout.SOUTH);
        this.add(seconds_left, BorderLayout.NORTH);          
        
        clearButton.addActionListener((ActionEvent e) -> {
            clearAll();
        });

        solveButton.addActionListener((ActionEvent e) -> {
        	solve();            
        });
        
        checkButton.addActionListener((ActionEvent e) -> {            
            checkAll();
        });

	}
	public void clearAll() {
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
            	JTextField field = grid[i][j];         
                field.setText("");
                field.setBackground(Color.WHITE);
            
                if(board[i][j] != 0) {
                	field.setText(String.valueOf(board[i][j]));               	
                	
                }                         
            }
        }	
	}
	public void solve() {
		SudokuSolver solver = new SudokuSolver(board);				
		if (solver.solve()) 
			 {
				System.out.println("Sudoku Grid solved with Back Tracking: ");
				int[][] board = solver.display();
				
				for (int i = 0; i < dimension; i++) {
		            for (int j = 0; j < dimension; j++) {
		            	JTextField field = grid[i][j];  	                                                                   
		                field.setText(String.valueOf(board[i][j]));       	
		                
		           }
		        }		
			 }  
			 else
			 {
				System.out.println("Unsolvable");
			 }
	
	}	
	
	public void timer() {
		
		 timer = new Timer(1000, new ActionListener() {			
			@Override
			
			public void actionPerformed(ActionEvent e) {
				elapsedTime = elapsedTime+1000;			
				minutes = (elapsedTime/60000) % 60;
				seconds = (elapsedTime/1000) % 60;
				seconds_string = String.format("%02d", seconds);
				minutes_string = String.format("%02d", minutes);
				seconds_left.setFont(new Font("Verdana", Font.CENTER_BASELINE, 20));
				seconds_left.setText("Timer : "+ minutes_string+":" + seconds_string);				

				}
			});
		timer.start();
	}
	
	public void checkAll() {
		SudokuSolver solver = new SudokuSolver(board);
		String sonk;
		int count = 0;
		
		if (solver.solve()) 
			 {
				
				int[][] checkBoard = solver.display();
				
				for (int i = 0; i < dimension; i++) {
		            for (int j = 0; j < dimension; j++) {
		            	JTextField field = grid[i][j];
		            	sonk = field.getText();          	
		            	if(sonk.equals("")) { sonk = "0"; }		            
		            	
		            	if(checkBoard[i][j] == Integer.parseInt(sonk)) {
		                	count++;
		                	if(count == dimension * dimension) {
		                		timer.stop();
		                		System.out.println("Sudoku Grid solved");
		                		JOptionPane.showMessageDialog(null, "Congrats you solved the puzzle!!!", "Status", JOptionPane.INFORMATION_MESSAGE);
		                		
		                	}     		         
		                }           	                        
		           }
		        }
                 if(count < 81) {
                	System.out.println("Sudoku Grid unsolved ");
                	JOptionPane.showMessageDialog(null, "You have not solved the puzzle, TRY AGAIN!!!", "Status", JOptionPane.INFORMATION_MESSAGE);
                }

			 }
		 
		
	}
	public int Counter() {		
		return counter++;		
	}
	@Override
	public void keyTyped(KeyEvent e) {
		if(e.getKeyChar() == KeyEvent.VK_ENTER){
			
			SudokuSolver solver = new SudokuSolver(board);		
			int value;			
			solver.solve();			
					
					int[][] checkBoard = solver.display();
					
					for (int i = 0; i < dimension; i++) {
			            for (int j = 0; j < dimension; j++) {
			            	JTextField field = grid[i][j];
			                
			                field.setBackground(Color.WHITE);			            	
			            				            
			                if(field == e.getSource()) {
			                	try{
			                		value = Integer.parseInt(field.getText());
			                		System.out.println("Key Typed " + value);
			                		
				                	if(checkBoard[i][j] == value) {
					            		field.setBackground(Color.GREEN); 						            		
					            	}
					            	else {
					            		field.setBackground(Color.RED);					            		
					     				errors.setFont(new Font("Verdana", Font.CENTER_BASELINE, 20));					     				
					     				errors.setText("<html>Incorrect: <font color='red'>" + Counter() + "</font></html>");
					             	} 
			                	} catch(NumberFormatException ex){ // handle your exception
			                	   
			                	}			                
			              }
			           }
			        }				   
		}	
	}
	@Override
	public void keyPressed(KeyEvent e) {
	}
	@Override
	public void keyReleased(KeyEvent e) {
	}
}
