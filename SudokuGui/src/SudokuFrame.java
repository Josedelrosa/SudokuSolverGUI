import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;

public class SudokuFrame extends JFrame {
	
	JMenuBar menuBar;
	JMenu fileMenu;
	JMenuItem resetItem;
	JMenuItem saveItem;
	JMenuItem exitItem;
	SudokuPanel sudokuPanel;
	
	SudokuFrame(){
		
		// Menu bar
		menuBar = new JMenuBar();
		fileMenu = new JMenu("File");
		
		menuBar.add(fileMenu);
		
		resetItem = new JMenuItem("New Game");
		saveItem = new JMenuItem("Save");
		exitItem = new JMenuItem("Exit");
		
		sudokuPanel =  new SudokuPanel();
	    this.add(sudokuPanel);
		
        resetItem.addActionListener((ActionEvent e) -> {  
        	this.remove(sudokuPanel);
        	sudokuPanel =  new SudokuPanel();
        	this.add(sudokuPanel);
        	SwingUtilities.updateComponentTreeUI(this);

        });
        exitItem.addActionListener((ActionEvent e) -> {
        	System.exit(0);            
        });
        
        fileMenu.add(resetItem);
		fileMenu.add(saveItem);
		fileMenu.add(exitItem);	
		
		this.setJMenuBar(menuBar);
		this.setTitle("Sudoku Solver");		
		this.setResizable(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		centerView();
		this.setSize(600,600);
		this.setVisible(true);		
	}
    private void centerView() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = this.getSize();
        this.setLocation((screen.width - frameSize.width) >> 1,
                          (screen.height - frameSize.height) >> 1);
    }
}
