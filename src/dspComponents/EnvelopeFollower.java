package dspComponents;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.collections.Buffer;
import org.apache.commons.collections.buffer.CircularFifoBuffer;

public class EnvelopeFollower {
	
	final int bufferSize = 200; 
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
		buffer.add(t); 
		
		Iterator<Double> itr = buffer.iterator();
		double sum = 0;
		while (itr.hasNext())
		{
			double value = itr.next(); 
			sum += Math.pow(value, 2.0); 
		}

		sum = sum/buffer.size(); 
		sum = Math.sqrt(sum); 

		return sum; 
		
	}
	
	

}
