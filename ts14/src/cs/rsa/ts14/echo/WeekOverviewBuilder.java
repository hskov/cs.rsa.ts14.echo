/**
 * 
 */
package cs.rsa.ts14.echo;

import cs.rsa.ts14.framework.ClassType;
import cs.rsa.ts14.framework.ReportBuilder;
import cs.rsa.ts14.standard.ClassMap;

/**
 * @author henning
 *
 */
public class WeekOverviewBuilder implements ReportBuilder {

	// This class is used internally in WeekOverviewBuilder to keep track of the week data
	private class WeekData {
		
		public WeekData() {
			clear();
		}
		
		void clear() {
			weekNumber = 0;
			hoursWorked = 0;
			workDays = 0;
		}

		// The number of the week
		public int weekNumber;
		// The number of hours worked this week
		public double hoursWorked;
		// The number of working days this week
		public int workDays;
	}
	
	private String report = "";
	private double HoursOvertime;
	private WeekData  weekData = new WeekData(); 
	
	@Override
	public void buildBegin() {
		// Clear report and start it with the headline
		this.report = "=== Week Overview ===";
		this.HoursOvertime = 0;
		this.weekData.clear();
	}

	@Override
	public void buildWeekSpecification(int weekNo, int countWorkdays,
			int countUsedVacationdays) {
		// Put previously entered WeekData in the Report
		update();
		
		this.weekData.clear();
		this.weekData.weekNumber = weekNo;
		this.weekData.workDays   = countWorkdays;
	}

	@Override
	public void buildWorkSpecification(String category, String subCategory,
			double hours) {
		// Add the hours worked to the current week data if category is not ClassType.CONSULENT
		if (ClassMap.mapCategoryToClass( category ) != ClassType.CONSULENT)
		{
			this.weekData.hoursWorked += hours;
		}
	}

	@Override
	public void buildWeekDaySpecification(String weekDay, String transportMode) {
		// This data is not needed by the Week Overview Builder
	}

	@Override
	public void buildAssignment(String variable, double value) {
		// So far we only know the internal variable called "HoursOvertime"
		if (0 == variable.compareToIgnoreCase("HoursOvertime")) {
			this.HoursOvertime = value;
		}
	}

	@Override
	public void buildEnd() {
		update();
	}

	private void update() {
		// If any week data has been provided, add it to the report.
		if (this.weekData.weekNumber != 0) {
			// Accumulate Overtime from week data
			this.HoursOvertime = this.HoursOvertime + this.weekData.hoursWorked - this.weekData.workDays * 7.4;
			// Calculate average hours per day. (Avoid divide by zero)
			double avgHoursPerDay = 0;
			if (this.weekData.workDays > 0) {
				avgHoursPerDay = this.weekData.hoursWorked / this.weekData.workDays;
			}
			
			// Format a complete week string and append it to the report.
			String str = String.format("%nWeek %3d : %6.1f hours   ( %2d Wdays of %5.1f d=%3.1f)", 
				this.weekData.weekNumber, 
				this.weekData.hoursWorked, 
				this.weekData.workDays, 
				avgHoursPerDay, 
				this.HoursOvertime);
			
			report = report + str;
		}
	}

	@Override
	public String getResult() {
		return report;
	}

}
