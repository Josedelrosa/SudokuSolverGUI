import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;

public class SudokuFrame extends JFrame {

	SudokuPanel sudokuPanel =  new SudokuPanel();
	SudokuFrame(){		
		this.add(sudokuPanel);
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
