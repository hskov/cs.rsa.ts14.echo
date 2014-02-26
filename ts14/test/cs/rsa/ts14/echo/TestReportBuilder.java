package cs.rsa.ts14.echo;

import org.junit.*; 

import cs.rsa.ts14.doubles.*;
import cs.rsa.ts14.framework.*;
import cs.rsa.ts14.standard.StandardTimesagLineProcessor;
import static org.junit.Assert.*; 


public class TestReportBuilder {
	
	private TimesagLineProcessor processor;
	private LineTypeClassifierStrategy classifier;
	private LineSequenceState sequenceState;
	private WeekOverviewBuilder builder;
	private String newline = System.getProperty("line.separator"); 
	
	@Before
	public void setup() {
	   
	classifier = new implLineTypeClassifierStrategy();
	builder = new WeekOverviewBuilder();
	sequenceState = new LineSequenceStateStub();
	processor = 
	        new StandardTimesagLineProcessor( classifier, builder, sequenceState );
	}
	
	@Test  
	  public void testBuildOfWeeklyOverview() {
		builder.buildBegin();
		builder.buildAssignment("HoursOvertime", 55.5);
		builder.buildWeekSpecification(9, 3, 0);
		builder.buildWorkSpecification("saip", "-", 7);
		builder.buildWorkSpecification("es", "-", 5);
		builder.buildWorkSpecification("terna", "-", 7);
		builder.buildWeekSpecification(10, 0, 0);
		builder.buildWorkSpecification("es", "-", 7);
		builder.buildEnd();
		
		String expectedOutput = "=== Week Overview ===" + newline;
		expectedOutput += "Week   9 :   12.0 hours   (  3 Wdays of   4.0 d=45.3)" + newline;
		expectedOutput += "Week  10 :    7.0 hours   (  0 Wdays of   0.0 d=52.3)";
		
		assertEquals(builder.getResult(), expectedOutput);
	}
}