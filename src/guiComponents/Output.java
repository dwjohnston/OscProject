package guiComponents;
import java.awt.BasicStroke;
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
    	/*find where the amplitude is at for the given horizontal pixel*/
    	x2 = i; 
    	y2 = (int)(Gui.OUTPUT_HEIGHT*0.5*engine.getValue(i)*-1+Gui.OUTPUT_HEIGHT/2); 
    	
    	/*draw a line between current and previous points*/           	
    	g.drawLine(x1,y1, x2,y2); 
    	x1 = x2;
    	y1 = y2; 	
    	
		
		
	}
	
	public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;     
            
             x1 = 0; 
             y1 = (int)engine.getValue(0); 
            
            /*draw the wave*/
            for (int i = 0; i<Gui.OUTPUT_WIDTH; i++)
            {
            	drawWaveForm(g2d, i); 
            }
       
    }
}

