package dspComponents;


/**A compressor component. Values above the threshold are compressed by the given ratio. 
 * This is currently a zero-attack, zero-release compressor. 
 * TODO: add attack and release funtionality. 
 *  
 * @author dave
 *
 *
 */
public class Compressor extends Component {

	private Parameter<Double> threshold;
	private Parameter<Double> gain; 
	private Parameter<Double> ratio;
	
	private Parameter<Double> attack;
	private Parameter<Double> release; 
	
	public Parameter<Double> getAttack() {return this.attack;}
	public Parameter<Double> getRelease() {return this.release;}
	
	private boolean triggered = false; //whether the attack phase has been triggered or not.
	private boolean released = false; 
	private double triggerTime = 0; 
	private double max =0;
	private double newGain = 1;
	
	
	public Parameter<Double> getThreshold()			{return this.threshold;}
	public Parameter<Double> getGain() 				{return this.gain; }
	public  Parameter<Double> getRatio() 				{return this.ratio; }
	
	public void refresh()
	{
		triggered = false;
		released = false; 
	}
	
	public Compressor(String name, Parameter<Double> threshold, Parameter<Double> ratio, Parameter<Double> gain, Parameter<Double> attack, Parameter<Double> release)
	{
		super(name); 
		this.threshold = threshold; 
		this.ratio = ratio; 
		this.gain = gain;
		this.attack = attack;
		this.release = release; 
	}
	
	
	
	
	/**
	 * Takes an input value applies compression to it. Returns the compressed value. 
	 * @param v
	 * @param t 
	 * @return
	 */
	
	/* I don't like that the compressor requires the time to be passed in, it should have it's own internal clock*/ 
	public double getValue(double v, double t)
	{
		
		
		if (Math.abs(v)>this.getThreshold().getValue() && !triggered)
		{
			triggered =true;
			triggerTime = t; 
				
		}
		
		if (triggered)
		{
			if (Math.abs(v) > max)
			{
				max = Math.abs(v);
				
			}	
			
			double timeSinceTrigger = t - triggerTime;
			double ratioToUse = 0;
			if (this.getAttack().getValue() !=0)
			{
				ratioToUse = (timeSinceTrigger/this.getAttack().getValue())*this.getRatio().getValue();
			}
			else ratioToUse = this.getRatio().getValue();
			
			double m2 = (max - this.getThreshold().getValue())/this.getRatio().getValue();
			newGain = (max - m2)/max;
			
			//newGain = 1 - ((max + this.getThreshold().getValue())
				//	/(max * ratioToUse));
			
			double rValue = newGain * v; 
			return rValue;
			
		}
		
		return v; 
		
		
		
		
		
		
//		/*hack to make the trigger reset when it redraws*/ 
//		if (t< triggerTime && triggered) 
//		{
//			triggered = false; 
//			triggerTime = 0; 
//		}
//		
//		if (v>this.getThreshold().getValue() && !triggered)
//			{
//			triggered = true;
//			triggerTime = t;  
//			}
//		
//		if (v>triggerTime+this.getAttack().getValue())
//		{
//			triggered = false; 
//		}
//		
//		double attackRatioToUse;
//		if (this.getAttack().getValue() !=0)
//		{
//			attackRatioToUse = ((t-triggerTime)/this.getAttack().getValue())*this.getRatio().getValue();
//		}
//		else attackRatioToUse = this.getRatio().getValue(); 
//		
//		
//		if (t == 0)
//			System.out.println("******************"); 
//		if (t<0.1)
//		{
//			System.out.printf("Time = %f\n", t); 
//			System.out.printf("triggerTime = %f\n", triggerTime);
//			System.out.printf("attackRatioToUse = %f\n", attackRatioToUse); 
//		}
//		
//		if (!triggered && !released)
//			return v; 
//		
//		if (triggered)
//		{
//			double ratioToUse = ((t-triggerTime)/this.getAttack().getValue())*this.getRatio().getValue();
//			return v/ratioToUse; 
//		}
//		
//		
		
			
		
//		double rValue = v; 
//		double exceedThreshold; 
//		/* compression applied to postive values*/
//		if (v >0)		
//		{
//			exceedThreshold = v - this.threshold.getValue(); 
//			if (exceedThreshold > 0)
//			{
//				rValue = this.threshold.getValue() + exceedThreshold/attackRatioToUse; 
//			}
//		}
//
//		/* compression applied to negative values*/ 
//		else
//		{
//			exceedThreshold = v + this.threshold.getValue(); 
//			if (exceedThreshold< 0)
//			{
//				rValue = this.threshold.getValue() *-1 + exceedThreshold/attackRatioToUse; 
//			}
//		}
//		
		
		//return v * this.gain.getValue(); 
	}
	
	
	

}
