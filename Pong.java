import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class Pong implements ActionListener, KeyListener, MouseMotionListener{
	JFrame theframe;
	PongPanel thepanel;
	Timer thetimer;
	
	public void mouseMoved(MouseEvent evt){
		
		if(evt.getY() < thepanel.intP2Y){
			thepanel.blnP2Up = true;
		}else if(evt.getY() > thepanel.intP2Y + thepanel.intP2H){
			thepanel.blnP2Down = true;
		}else if(evt.getY() >= thepanel.intP2Y && evt.getY() <= thepanel.intP2Y + thepanel.intP2H){
			thepanel.blnP2Up = false;
			thepanel.blnP2Down = false;
		}
	}
	public void mouseDragged(MouseEvent evt){
		
	}
	
	public void actionPerformed(ActionEvent evt){
		if(evt.getSource() == thetimer){
			thepanel.repaint();
		}
	}
	public void keyReleased(KeyEvent evt){
		if(evt.getKeyChar() == 's'){
			thepanel.blnP1Down = false;
		}else if(evt.getKeyChar() == 'w'){
			thepanel.blnP1Up = false;
		}
	}
	public void keyPressed(KeyEvent evt){
		if(evt.getKeyChar() == 's'){
			thepanel.blnP1Down = true;
		}else if(evt.getKeyChar() == 'w'){
			thepanel.blnP1Up = true;
		}
		
	}
	public void keyTyped(KeyEvent evt){
		
	}
	
	public Pong(){
		theframe = new JFrame("Pong!");
		thepanel = new PongPanel();
		thepanel.setLayout(null);
		thepanel.setPreferredSize(new Dimension(800, 400));
		thepanel.addMouseMotionListener(this);
		thepanel.addKeyListener(this);
		thepanel.setFocusable(true);
		
		theframe.setContentPane(thepanel);
		theframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		theframe.pack();
		theframe.setVisible(true);
		thetimer = new Timer(1000/60, this);
		thetimer.start();
	
	}
	////////////////////// PONG PANEL
	
	public class PongPanel extends JPanel{
		int intBallX = 400;
		int intBallY = 100;
		int intBallW = 40;
		int intBallH = 40;
		int intBallXDelta = 2;
		int intBallYDelta = 2;
		int intP1X = 40;
		int intP1Y = 40;
		int intP1W = 40;
		int intP1H = 120;
		int intP2X = 720;
		int intP2Y = 40;
		int intP2W = 40;
		int intP2H = 120;
		boolean blnP1Up = false;
		boolean blnP1Down = false;
		boolean blnP2Up = false;
		boolean blnP2Down = false;
		int intP1Delta = 4;
		int intP2Delta = 4;
		int intP1Score = 0;
		int intP2Score = 0;
		InputStream is = null;
		Font font = null;
	
		public void paintComponent(Graphics g){
			
			// Drawing
			g.setFont(font);
			g.setColor(Color.BLACK);
			g.fillRect(0,0,800,400);
			g.setColor(Color.WHITE);
			g.fillOval(intBallX, intBallY, intBallW, intBallH);
			g.setColor(Color.YELLOW);
			g.fillRect(intP1X, intP1Y, intP1W, intP1H);
			g.setColor(Color.RED);
			g.fillRect(intP2X, intP2Y, intP2W, intP2H);
			g.setColor(Color.WHITE);
			g.drawString(intP1Score + "", 50, 50);
			g.drawString(intP2Score + "", 800-75, 50);
			
			// Creating collision rectangles
			Rectangle ballrect = new Rectangle(intBallX, intBallY, intBallW, intBallH);
			Rectangle p1rect = new Rectangle(intP1X, intP1Y, intP1W, intP1H);
			Rectangle p2rect = new Rectangle(intP2X, intP2Y, intP2W, intP2H);
			
			// Math Logic
			if(ballrect.intersects(p1rect)){
				// Need to put ball at the next closest "edge"
				if(intBallX <= intP1X + intP1W && intBallX > intP1X + intP1W - (intP1W/2)){
					intBallX = intP1X + intP1W;
					intBallXDelta = intBallXDelta * -1;
				}else if(intBallX + intBallW >= intP1X && intBallX + intBallW < intP1X + (intP1W/2)){
					intBallX = intP1X - intBallW;
					intBallXDelta = intBallXDelta * -1;

				}else if(intBallY <= intP1Y + intP1H && intBallY > intP1Y + intP1H - (intP1H/2)){
					intBallY = intP1Y + intP1H;
					intBallYDelta = intBallYDelta * -1;
				}else if(intBallY + intBallH >= intP1Y && intBallY + intBallH < intP1Y + (intP1H/2)){
					intBallY = intP1Y - intBallH;
					intBallYDelta = intBallYDelta * -1;
					
				}
				
				
			}
			if(ballrect.intersects(p2rect)){
				if(intBallX <= intP2X + intP2W && intBallX > intP2X + intP2W - (intP2W/2)){
					intBallX = intP2X + intP2W;
					intBallXDelta = intBallXDelta * -1;
				}else if(intBallX + intBallW >= intP2X && intBallX + intBallW < intP2X + (intP2W/2)){
					intBallX = intP2X - intBallW;
					intBallXDelta = intBallXDelta * -1;

				}else if(intBallY <= intP2Y + intP2H && intBallY > intP2Y + intP2H - (intP2H/2)){
					intBallY = intP2Y + intP2H;
					intBallYDelta = intBallYDelta * -1;
				}else if(intBallY + intBallH >= intP2Y && intBallY + intBallH < intP2Y + (intP2H/2)){
					intBallY = intP2Y - intBallH;
					intBallYDelta = intBallYDelta * -1;
					
				}
			}
			intBallX = intBallX + intBallXDelta;
			intBallY = intBallY + intBallYDelta;
			if(intBallX <= 0){
				intP2Score++;
				intBallX = -1;
				if(intBallXDelta > 0){
					intBallXDelta++;
				}else{
					intBallXDelta--;
				}
				if(intBallYDelta > 0){
					intBallYDelta++;
				}else{
					intBallYDelta--;
				}
				intP1Delta++;
				intP2Delta++;
			}
			if(intBallX >= 800 - intBallH){
				intP1Score++;
				intBallX = 800 - intBallH + 1;
				if(intBallXDelta > 0){
					intBallXDelta++;
				}else{
					intBallXDelta--;
				}
				if(intBallYDelta > 0){
					intBallYDelta++;
				}else{
					intBallYDelta--;
				}
				intP1Delta++;
				intP2Delta++;
			}
			
			if(intBallX > 800-intBallW || intBallX < 0){
				intBallXDelta = intBallXDelta * -1;
			}
			if(intBallY > 400-intBallH || intBallY < 0){
				intBallYDelta = intBallYDelta * -1;
			}
			if(blnP1Down){
				intP1Y = intP1Y + intP1Delta;
			}
			if(blnP1Up){
				intP1Y = intP1Y - intP1Delta;
			}
			if(blnP2Down){
				intP2Y = intP2Y + intP2Delta;
			}
			if(blnP2Up){
				intP2Y = intP2Y - intP2Delta;
			}
			

		}

		public PongPanel(){
			super();
			try{
			 font = new Font("Roboto-Regular.ttf", Font.PLAIN, 40);
			}catch(Exception e){
				System.out.println("Unable to load font");
			}	
		}

	}
	///////////////////// Main Method
	public static void main(String[] args){
		new Pong();
	}



}
