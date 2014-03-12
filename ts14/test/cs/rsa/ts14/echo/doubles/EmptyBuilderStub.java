package cs.rsa.ts14.echo.doubles;

import cs.rsa.ts14.framework.ReportBuilder;

/**
 * @author henning
 * 
 * This class is an empty implementation of the ReportBuilder interface. The purpose of
 * this class is to be used as a stub in the TopDown integration test. 
 */
public class EmptyBuilderStub implements ReportBuilder {

	@Override
	public void buildBegin() {
		// TODO Auto-generated method stub

	}

	@Override
	public void buildWeekSpecification(int weekNo, int countWorkdays,
			int countUsedVacationdays) {
		// TODO Auto-generated method stub

	}

	@Override
	public void buildWorkSpecification(String category, String subCategory,
			double hours) {
		// TODO Auto-generated method stub

	}

	@Override
	public void buildWeekDaySpecification(String weekDay, String transportMode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void buildAssignment(String variable, double value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void buildEnd() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getResult() {
		// TODO Auto-generated method stub
		return "";
	}

}
