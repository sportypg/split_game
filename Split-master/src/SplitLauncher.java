
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class SplitLauncher {

	private static Vector dimensions;
	public static int W, H;
	public static void main(String[] args) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		dimensions = new Vector((int) screenSize.getWidth(), (int) screenSize.getHeight());
		promptDims();
		new SplitLauncher().startGame();
	}
	
	private static void promptDims() {
		int width = promptWidth();
		if(width != 0) {
			int height = promptHeight();
			if(width >= 300 && height >= 300 && width <= dimensions.getX() && height <= dimensions.getY()) {
				W = width;
				H = height;
			}
			else {
				promptDims();
			}
		}
		else {
			W = dimensions.getX();
			H = dimensions.getY();
		}
	}
	
	private static int promptWidth() {
		int width = 100;
		String input = JOptionPane.showInputDialog("Enter 300 <= Width <= Screen Size over 300 or 0 for Full Screen");
		try { 
			width = Integer.parseInt(input);
		}
		catch (NumberFormatException e) {
		}
		
		return width;
	}
	private static int promptHeight() {
		int height = 100;
		String input = JOptionPane.showInputDialog("Enter 300 <= Height <= Screen Size over 300 or 0 for Full Screen");
		try { 
			height = Integer.parseInt(input);
		}
		catch (NumberFormatException e) {
		}
		
		return height;
	}

	private void startGame() {
		System.setProperty("sun.java2d.opengl", "true");
		JFrame splitFrame = new JFrame("SPLIT!");
		splitFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SplitPanel splitPanel = new SplitPanel(W, H);
		splitFrame.add(splitPanel);
		splitFrame.pack();
		splitFrame.setVisible(true);
	}	
}