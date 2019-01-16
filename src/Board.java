import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.geom.*;
import java.util.concurrent.TimeUnit;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Board extends JPanel{
	
    public static boolean[][] grid;
    static int time = 0;
    int cCount = 0;
    int cyc = 0;
    int fx;
    int fy;
    public static boolean pause = false;
    boolean mdown = false;
    
    public Board() {
        grid = new boolean[200][200];
        
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[0].length; y++) {
                if (Math.random() < 0.4) {
                    grid[x][y] = true;
                }
            }
        }
        
        addMouseMotionListener(new MouseMotionAdapter() { 
            public void mouseDragged(MouseEvent me) { 
            	grid[(fx/5)][(fy/5)] = true;
            } 
          }); 
        
		KeyListener listener = new MyKeyListener();
		addKeyListener(listener);
		setFocusable(true);
    }
    
	public class MyKeyListener implements KeyListener {
		@Override
		public void keyTyped(KeyEvent e) {
		}

		@Override
		public void keyPressed(KeyEvent e) {
			pause = !pause;
		}

		@Override
		public void keyReleased(KeyEvent e) {
		}
	}
    
    public int cellCount() {
    	cCount = 0;
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[0].length; y++) {
                if (grid[x][y] == true) {
                    cCount++;
                }
            }
        }
    	return cCount;
    }

    private static void step() {
    	if (pause == false) {
	        boolean[][] new_grid = new boolean[200][200];
	        int count;
	        for (int x = 0; x < grid.length; x++) {
	            for (int y = 0; y < grid[0].length; y++) {
	                count = 0;
	                if (x - 1 >= 0 && y - 1 > 0 && grid[x - 1][y - 1]) {
	                    count++;
	                }
	                if (y - 1 >= 0 && grid[x][y - 1]) {
	                    count++;
	                }
	                if (x + 1 <= 199 && y - 1 >= 0 && grid[x + 1][y - 1]) {
	                    count++;
	                }
	                if (x - 1 >= 0 && grid[x - 1][y]) {
	                    count++;
	                }
	                if (x + 1 <= 199 && grid[x + 1][y]) {
	                    count++;
	                }
	                if (x - 1 >= 0 && y + 1 <= 199 && grid[x - 1][y + 1]) {
	                    count++;
	                }
	                if (y + 1 <= 199 && grid[x][y + 1]) {
	                    count++;
	                }
	                if (x + 1 <= 199 && y + 1 <= 199 && grid[x + 1][y + 1]) {
	                    count++;
	                }
	                if ((count == 2 || count == 3) && grid[x][y]) {
	                    new_grid[x][y] = true;
	                }
	                if (count == 3 && !grid[x][y]) {
	                    new_grid[x][y] = true;
	                }
	            }
	        }
	        grid = new_grid;
    	}
    }
	
	public void paint(Graphics g){
		
		for(int x = 0;x < grid.length;x++){ //GAME FILL
			for(int y = 0;y < grid.length;y++){
				if(grid[x][y]==true){
					Rectangle r1 = new Rectangle(x*5,y*5,5,5);
					Graphics2D g2 = (Graphics2D) g;
					g2.setColor(Color.WHITE);
					g2.fill(r1);
				}else if(grid[x][y]==false){
					Rectangle r = new Rectangle(x*5,y*5,5,5);
					Graphics2D g1 = (Graphics2D) g;
					g1.setColor(Color.BLACK);
					g1.fill(r);
				}
			}
		}
		
		Font bold = new Font("bold", Font.BOLD, 18);
		Font def = new Font("def", Font.PLAIN, 18);
		Graphics2D g3 = (Graphics2D) g;
		int mx = (int) MouseInfo.getPointerInfo().getLocation().getX();
		int my = (int) MouseInfo.getPointerInfo().getLocation().getY();
		int wx = getLocationOnScreen().x;
		int wy = getLocationOnScreen().y;
		fx = mx - wx;
		fy = my - wy;
		
		if (fy <= 0) {
			fy = 0;
		}
		if (fx <= 0) {
			fx = 0;
		}
		if (fy > 1000) {
			fy = 999;
		}
		if (fx > 1000) {
			fx = 999;
		}
		
		g3.setColor(Color.DARK_GRAY);
		Rectangle bounds = new Rectangle(1000,0,100,1000);
		g3.fill(bounds);
		g3.setColor(Color.WHITE);
		g3.setFont(bold);
		g3.drawString("Time", 1005, 20);
		g3.drawString("Cell Count", 1005, 70);
		g3.drawString("Mouse Pos", 1005, 120);
		g3.setFont(def);
		g3.drawString(String.valueOf(time/25)+"s", 1005, 45);
		g3.drawString(String.valueOf(cellCount()), 1005, 95);
		g3.drawString(String.valueOf(fx/5),1005,145);
		g3.drawString(String.valueOf(fy/5),1005,170);
		
		try {
			TimeUnit.MILLISECONDS.sleep(20);
			time++;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		step();
		repaint();
	}
}