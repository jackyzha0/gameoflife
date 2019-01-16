import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.TimeUnit;

public class Main extends JFrame {

    public Main()
    {	
    	int bounds = 100;
    	add(new Board());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize((1005+bounds),1029);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }
     
    public static void main(String[] args) {
    	new Main();
//		try {
//			TimeUnit.SECONDS.sleep(3);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
    	
//		new initGUI();
    }
}