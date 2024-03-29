package projectMain;
import dspComponents.Compressor;
import dspComponents.Osc;
import dspComponents.Parameter;
import dspComponents.Redux;
import dspComponents.envelopes.Envelope;
import dspComponents.envelopes.LinearADSEnvelope;


/**
 * The logic base for our DSP program. 
 * Parameter arguements, and DSP order is defined here. 
 * @author dave
 *
 */
public class Engine {
	
	private Osc osc1;
	private Compressor compressor1; 
	private int sampleRate;
	private Redux redux1; 
	
	public void refresh()
	{
		compressor1.refresh();
	}
	
	
	
	
	public Engine(int sampleRate)
	{
		this.sampleRate = sampleRate;
		
		/*Oscillator*/
		final Parameter<Double> oscFreq = new Parameter<Double>(Double.class, 1.0, 100.0, 1.0, "Freq",3);
		final Parameter<Double> oscAmp = new Parameter<Double> (Double.class, 0.0, 1.0, 0.8, "Amp"); 
		final Parameter<Double> oscPhase = new Parameter<Double> (Double.class, -180.0, 180.0, 0.0, "Phase"); 
		final Parameter<Double> oscWidth = new Parameter<Double> (Double.class, 0.0, 1.0, 0.5, "Width"); 
		final Parameter<Double> oscSlope = new Parameter<Double> (Double.class, 0.0, 1.0, 0.5, "Slope"); 
		final Parameter<Double> oscCurve = new Parameter<Double> (Double.class, 0.0, 1.0, 0.5, "Curve"); 
		final Parameter<Integer> oscFourier = new Parameter<Integer> (Integer.class, 2, 64, 2, "Fourier");
		final Envelope oscEnv = new LinearADSEnvelope("Osc1 Amplitude Envelope", 0.5, 0.5); 
		this.osc1 = new Osc("Oscillator 1", oscAmp, oscFreq, oscPhase, oscCurve, oscWidth, oscSlope, oscFourier, oscEnv);
		
		/*Compressor*/
		final Parameter<Double> compThreshold = new Parameter<Double>(Double.class, 0.0, 1.0, 1.0, "Threshold");
		final Parameter<Double> compRatio = new Parameter<Double> (Double.class, 1.0, 100.0, 2.0, "Ratio", 6);
		final Parameter<Double> compGain = new Parameter<Double> (Double.class, 1.0, 4.0, 1.0, "Gain");
		final Parameter<Double> compAttack = new Parameter<Double>(Double.class, 0.0, 1.0, 0.0, "Attack");
		final Parameter<Double> compRelease = new Parameter<Double>(Double.class, 0.0, 1.0, 0.0, "Release"); 
		this.compressor1 = new Compressor("Compressor 1", compThreshold, compRatio, compGain, compAttack, compRelease); 
				
		/*Redux*/
		final Parameter<Double> reduxSampleRate = new Parameter<Double>(Double.class, 2.0, (double)sampleRate, (double)sampleRate, "Sample Rate", 5); 
		final Parameter<Integer> reduxBitDepth = new Parameter<Integer>(Integer.class, 1, 8, 8, "Bit Depth");
		this.redux1 = new Redux("Redux 1", reduxSampleRate, reduxBitDepth); 
		
		
		
	}
	
	/** Getter methods*/
	public Osc getOsc1()			{return this.osc1; }
	public Compressor getCompressor1() { return this.compressor1;}  
	public Redux getRedux1() { return this.redux1; }
	
	/**
	 * Returns the final outputed value after all DSP has been applied, for a given time value. 
	 * @param t sample number that's passed in.
	 * @return
	 */
	public double getValue(int t)
	{
		double time = (double)t/sampleRate; 
	
		double value = osc1.getValue(time); 
		value = compressor1.getValue(value, time);
		value = redux1.getValue(t, value); 
		return value; 
	
	}
	
	
}
