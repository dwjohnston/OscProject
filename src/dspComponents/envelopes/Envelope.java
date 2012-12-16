package dspComponents.envelopes;

import dspComponents.Component;

public abstract  class Envelope extends Component {
	
	public Envelope(String name)
	{
		super(name); 
	}
	
	public abstract double getValue(double t); 
}
