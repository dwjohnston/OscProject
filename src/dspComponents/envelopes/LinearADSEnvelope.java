package dspComponents.envelopes;

import dspComponents.Parameter;

public class LinearADSEnvelope extends Envelope {

	private Parameter<Double> attack; 
	private Parameter<Double> decay; 
	private Parameter<Double> sustain; 

	public LinearADSEnvelope(String name, double attackMaxTime, double decayMaxTime)
	{
		super(name); 
		this.attack = new Parameter<Double>(Double.class, 0.0, attackMaxTime, 0.0, "Attack");
		this.decay = new Parameter<Double> (Double.class, 0.0, decayMaxTime, 0.0, "Decay"); 
		this.sustain = new Parameter<Double> (Double.class, 0.0, 1.0, 1.0, "Sustain");  		
	}

	public Parameter<Double> getAttack()
	{ return this.attack;}
	public Parameter<Double> getDecay()
	{ return this.decay; }
	public Parameter<Double> getSustain()
	{ return this.sustain; }

	public double getValue(double t)
	{
		/*attack phase*/
		if (t< this.getAttack().getValue())
		{
			return (t/this.getAttack().getValue()); 
		}
		/*decay phase*/
		else if (t< this.getAttack().getValue() + this.getDecay().getValue())
		{
			double timeSinceDecayPhase = t - this.getAttack().getValue(); 
			double amountDecayed = (timeSinceDecayPhase/this.getDecay().getValue()); 
			
			double amountToDecay = 1- this.getSustain().getValue();
			
			return 1 - amountToDecay*amountDecayed; 
			
			
			
		}
		/*sustain phase*/
		else 
		{
			return this.getSustain().getValue(); 
		}



	}

}
