package cs.rsa.ts14.hotel.doubles;

import cs.rsa.ts14.hotel.ReportData;
import cs.rsa.ts14.hotel.TransportMode;

/**
 * Delegates all calls to the real implementation and sniffs in the process 
 */
public class ReportDataSpy implements ReportData {

	public ReportData report;

	// Logning af antal kald imod metoden addValueTo
	public int timesAddValueToIsCalled; 
	
	
	public ReportDataSpy(ReportData reportDataToSpyOn) {
		this.report = reportDataToSpyOn;
		timesAddValueToIsCalled = 0;
	}
	
	@Override
	public void reset() {
		report.reset();
	}

	@Override
	public int getValueFor(TransportMode mode) {
		return report.getValueFor(mode);
	}

	@Override
	public void addValueTo(TransportMode mode, int value) {
		timesAddValueToIsCalled++;
		report.addValueTo(mode, value);
	}

	@Override
	public String getResult() {
		return report.getResult();
	}

	@Override
	public void setResult(String result) {
		report.setResult(result);
	}

}
