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
	
	public Parameter<Double> getThreshold()			{return this.threshold;}
	public Parameter<Double> getGain() 				{return this.gain; }
	public  Parameter<Double> getRatio() 				{return this.ratio; }
	
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
	 * @return
	 */
	public double getValue(double v)
	{
		
		
		double rValue = v; 
		double exceedThreshold; 
		/* compression applied to postive values*/
		if (v >0)		
		{
			exceedThreshold = v - this.threshold.getValue(); 
			if (exceedThreshold > 0)
			{
				rValue = this.threshold.getValue() + exceedThreshold/this.ratio.getValue();
			}
		}

		/* compression applied to negative values*/ 
		else
		{
			exceedThreshold = v + this.threshold.getValue(); 
			if (exceedThreshold< 0)
			{
				rValue = this.threshold.getValue() *-1 + exceedThreshold/this.ratio.getValue(); 
			}
		}
		
		
		return rValue * this.gain.getValue(); 
	}
	
	
	

}
