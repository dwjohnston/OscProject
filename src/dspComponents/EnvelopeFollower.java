package dspComponents;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.collections.Buffer;
import org.apache.commons.collections.buffer.CircularFifoBuffer;

public class EnvelopeFollower {
	
	final int bufferSize = 20; 
	Buffer buffer; 
	
	public void reset()
	{
		buffer = new CircularFifoBuffer(bufferSize);
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
		
		}

		return max; 
		
	}
	
	

}
