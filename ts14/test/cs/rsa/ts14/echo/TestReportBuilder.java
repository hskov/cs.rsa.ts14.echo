package cs.rsa.ts14.echo;

import org.junit.*; 

import cs.rsa.ts14.doubles.*;
import cs.rsa.ts14.framework.*;
import cs.rsa.ts14.standard.StandardTimesagLineProcessor;
import static org.junit.Assert.*; 
//import java.util.regex.Pattern;
//import java.util.regex.Matcher;


public class TestReportBuilder {
	
	private TimesagLineProcessor processor;
	private LineTypeClassifierStrategy classifier;
	private LineSequenceState sequenceState;
	private WeekOverviewBuilder builder;
	private String line;
	
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
		
		//System.out.println(builder.getResult());
		
		line = builder.getResult();
		String[] parts = line.split("\n");
		String REGEX = "^Week\\s[1-9][0-2]*";
		//Pattern p1 = Pattern.compile(REGEX); 
		
		//Kan man bruge assertEquals til test med regex???
		assertEquals(parts[0], "=== Week Overview ===");
		//assertEquals(parts[1], REGEX);
	}
}