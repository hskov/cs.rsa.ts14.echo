package cs.rsa.ts14.hotel;

public interface ReportData {

	/**
	 * Resets ReportData to a state like it was just instantiated
	 */
	void reset();

	/**
	 * Returns the current value for a given transport mode 
	 * @param mode
	 * @return
	 */
	int getValueFor(TransportMode mode);
	
	/**
	 * Adds a given value to a given transport mode 
	 * @param mode
	 * @param value
	 */
	void addValueTo(TransportMode mode, int value);
	
	/**
	 * Returns the report built when buildEnd was called
	 * @return
	 */
	String getResult();

	/**
	 * Used to set the report when it's built 
	 * @param result
	 */
	void setResult(String result);
	
}
