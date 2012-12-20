package dspComponents;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.collections.Buffer;
import org.apache.commons.collections.buffer.CircularFifoBuffer;

public class EnvelopeFollower {
	
	final int BUFFER_SIZE = 200;
	final double SMOOTHING_EXPONENT = 1.01;
	final double SMOOTHING_MULTIPLIER = 0.99; 
	
	
	
	Buffer buffer;
	Buffer maxBuffer; 
	
	public void reset()
	{
		buffer = new CircularFifoBuffer(BUFFER_SIZE);
		maxBuffer = new CircularFifoBuffer(BUFFER_SIZE);
	}
	
	public EnvelopeFollower()
	{
		this.reset(); 
	}
	
	public double getValue(double t)
	{
		buffer.add(Math.abs(t)); 
				
		Iterator<Double> itr = buffer.iterator();		
		double max = 0; 
		while (itr.hasNext())
		{
			double value = itr.next(); 
			
			if (value> max) max = value; 
			else max = Math.pow((max*SMOOTHING_MULTIPLIER),  SMOOTHING_EXPONENT); 
		
		}
//		maxBuffer.add(max); 
//		Iterator<Double> maxItr = maxBuffer.iterator();
//		double sum = 0; 
//		while (maxItr.hasNext())
//		{
//			double vmax = maxItr.next();
//			
//			sum += vmax *vmax; 
//		}
//		sum = sum/ maxBuffer.size(); 
//		sum = Math.sqrt(sum); 
//		
//		return sum; 
		
		return max; 
	}
	
	

}
