package cs.rsa.ts14.hotel;

import cs.rsa.ts14.framework.ReportBuilder;

public class TransportModeOverviewBuilder implements ReportBuilder {

	private ReportData reportData;
	
	public TransportModeOverviewBuilder() {
		this.reportData = new ReportDataImpl();
	}
	
	
	/**
	 * Constructor for test use. Input must be instantiated. 
	 * @param reportData
	 */
	TransportModeOverviewBuilder(ReportData reportData) {
		this.reportData = reportData;  
	}
	
	
	@Override
	public void buildBegin() {
		this.reportData.reset();
	}

	@Override
	public void buildWeekSpecification(int weekNo, int countWorkdays, int countUsedVacationdays) {
		// No use for this report
	}

	@Override
	public void buildWorkSpecification(String category, String subCategory, double hours) {
		// No use for this report
	}

	@Override
	public void buildWeekDaySpecification(String weekDay, String transportMode) {
		if (isValidWeekDay(weekDay)) {
			reportData.addValueTo(TransportMode.getByCode(transportMode), 1);
		}
	}

	// Kunne evt. flyttes ud som en selvstændig strategi, evt. sammen med andre lignende tjek i projektet
	private boolean isValidWeekDay(String weekDay) {
		String pattern = "^(Mon|Tue|Wed|Thu|Fri|Sat|Sun)$";
		return weekDay != null && weekDay.matches( pattern );
	}
	
	
	@Override
	public void buildAssignment(String variable, double value) {
		// No use for this report
	}

	@Override
	public void buildEnd() {
		int ca = reportData.getValueFor(TransportMode.CAR);
		int bi = reportData.getValueFor(TransportMode.BICYCLE);
		int pu = reportData.getValueFor(TransportMode.PUBLIC_TRANSPORTATION);
		int tr = reportData.getValueFor(TransportMode.TRAVELLING);
		int ho = reportData.getValueFor(TransportMode.HOME);
		int no = reportData.getValueFor(TransportMode.NON_WORKING_DAYS);
		int un = reportData.getValueFor(TransportMode.UNKNOWN);
		StringBuilder report = new StringBuilder();
		report.append("-------------------------------------------------------------------------\n");
		report.append(String.format("| Car:   %3d     Bicycle:  %3d     Public:   %3d    Traveling:  %3d     |\n", ca, bi, pu, tr));
		report.append(String.format("| Home:  %3d    NonWDays:  %3d     Unknown:  %3d                        |\n", ho, no, un));
		report.append("-------------------------------------------------------------------------\n");
		reportData.setResult(report.toString());
	}

	@Override
	public String getResult() {
		return reportData.getResult();
	}

}
