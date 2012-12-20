package guiComponents;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import projectMain.Engine;
import projectMain.Gui;

/**
 * Our waveform output drawing area. 
 * @author dave
 *
 */
public class Output extends JPanel{


	private Engine engine;  
	public Output (Engine e)
	{
		this.engine = e; 
	}

	/**
	 * redraws the output. 
	 */
	public void refresh()
	{

		this.invalidate(); 
		this.repaint(); 
	}


	/*ints used for drawing the waveform*/
	private int x1; 
	private int x2; 
	private int y1; 
	private int y2;

	private void drawWaveForm(Graphics2D g, int i)
	{
		g.setColor(Color.black);

		/*find where the amplitude is at for the given horizontal pixel*/
		x2 = i; 
		y2 = (int)(Gui.OUTPUT_HEIGHT*0.5*engine.getValue(i)*-1+Gui.OUTPUT_HEIGHT/2); 

		/*draw a line between current and previous points*/           	
		g.drawLine(x1,y1, x2,y2); 
		x1 = x2;
		y1 = y2;		

	}

	/*ints used for drawing the envelope*/ 
	private int envx1; 
	private int envx2; 
	private int envy1; 
	private int envy2; 

	private void drawEnvelope(Graphics2D g, int i)
	{
		g.setColor(Color.red);

		/*find where the amplitude is at for the given horizontal pixel*/
		envx2 = i; 
		envy2 = (int)(Gui.OUTPUT_HEIGHT*0.5*engine.getCompressor1().getEnvF().getValue(engine.getValue(i))*-1+Gui.OUTPUT_HEIGHT/2); 

		//System.out.printf("%d\n", envy2);
		
		/*draw a line between current and previous points*/           	
		g.drawLine(envx1,envy1, envx2,envy2); 
		envx1 = envx2;
		envy1 = envy2;	

	}
	
	private void drawThreshold(Graphics2D g)
	{
		g.setColor(Color.blue); 
		
		int height = (int)(Gui.OUTPUT_HEIGHT/2 - Gui.OUTPUT_HEIGHT*0.5*engine.getCompressor1().getThreshold().getValue() );
		g.drawLine(0, height, Gui.OUTPUT_WIDTH, height); 
		
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		/**@TODO: Is it inefficient to be changing color every turn?
		 * Perhaps make two seperate graphics objects*
		 */
		Graphics2D g2d = (Graphics2D) g;

		x1 = 0; 
		y1 = (int)engine.getValue(0); 

		envx1 = 0;
		envy1 = (int)engine.getCompressor1().getEnvF().getValue(0); 

		/*draw everything*/
		for (int i = 0; i<Gui.OUTPUT_WIDTH; i++)
		{
			drawWaveForm(g2d, i);
			drawEnvelope(g2d, i); 

		}
		drawThreshold(g2d); 
		

	}
}

