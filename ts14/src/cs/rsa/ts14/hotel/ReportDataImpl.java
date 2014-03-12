package cs.rsa.ts14.hotel;

import java.util.HashMap;
import java.util.Map;


class ReportDataImpl implements ReportData {
	
	private Map<TransportMode, Integer> transportModeValues;
	private String result;
	
	
	public ReportDataImpl() {
		reset();
	}
	

	@Override
	public void reset() {
		transportModeValues = new HashMap<TransportMode, Integer>();
		result = null;
	}

	@Override
	public int getValueFor(TransportMode mode) {
		if (transportModeValues.containsKey(mode)) {
			return transportModeValues.get(mode).intValue();
		}
		return 0;
	}


	@Override
	public void addValueTo(TransportMode mode, int value) {
		Integer newValue = value;
		if (transportModeValues.containsKey(mode)) {
			newValue += transportModeValues.get(mode);
		}
		transportModeValues.put(mode, newValue);
	}

	@Override
	public String getResult() {
		return result;
	}

	@Override
	public void setResult(String result) {
		this.result = result;
	}

}
