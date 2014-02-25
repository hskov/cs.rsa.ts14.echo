package cs.rsa.ts14.echo;

import org.junit.*; 

import cs.rsa.ts14.doubles.*;
import cs.rsa.ts14.framework.*;
import cs.rsa.ts14.standard.StandardTimesagLineProcessor;
import static org.junit.Assert.*; 


public class TestLineTypeClassifier {
	
	private TimesagLineProcessor processor;
	private LineTypeClassifierStrategy classifier;
	private LineSequenceState sequenceState;
	private SpyWorkloadBuilder builder;
	private String line;
	
	@Before
	public void setup() {
	   
	classifier = new implLineTypeClassifierStrategy();
	builder = new SpyWorkloadBuilder();
	sequenceState = new LineSequenceStateStub();
	processor = 
	        new StandardTimesagLineProcessor( classifier, builder, sequenceState );
	}
	
	/* Test cases for evaluating implementation of empty lines 
	 * and comment (Echo)
	 */
	
	/*Test Case ID 101: Empty line with no spaces and tabs 
	 * should be accepted as valid input and categorized as 
	 * empty line 
	 */
	@Test  
	  public void shouldAcceptEmptyLineNoSpacesTabs() {
	    line = "";
	    LineType theLineType = processor.process(line);
	    assertEquals(LineType.EMPTY_LINE, theLineType);
	}
	  
	/*Test Case ID 102: Empty line with spaces and tabs should 
	 * be accepted as valid input and categorized as empty line
	 */
	@Test  
	   public void shouldAcceptEmptyLineWithTabsAndSpace() {
		 line = "    \t\t   ";
		 LineType theLineType = processor.process(line);
		 assertEquals(LineType.EMPTY_LINE, theLineType);
	}  
	
	/*Test Case ID 103: Standard comment line should be recognized 
	 * as valid input and categorized as comment
	 */
	@Test  
	   public void shouldAcceptCommentLineWithHashTag() {
		 line = "# Comment Line ##";
		 LineType theLineType = processor.process(line);
		 assertEquals(LineType.COMMENT_LINE, theLineType);
	}  
	
	/*Test Case ID 104: Comment line with preceding space should 
	*be accepted as valid input and categorized as comment.
	*/
	@Test  
		public void shouldAcceptCommentLineWithSpaceTabHashTag() {
		  line = "  \t   # Comment with spaces";
		  LineType theLineType = processor.process(line);
		  assertEquals(LineType.COMMENT_LINE, theLineType);
	}  
	
	/*
	Empty lines and comment - Test Case ID 105
	@Test  
		public void shouldNotAcceptAssignmentAsCommentLine() {
		   line = "HoursOvertime = 10";
		   LineType theLineType = processor.process(line);
		   assertEquals(LineType.COMMENT_LINE, theLineType);
	}  
    */ 
	
	/*Test Case ID 201: Assignment-line with correct semantic and 
	 * syntax should be validated and categorized as assignment line
	*/
	@Test  
		public void shouldAcceptWellformedAssignmentLine() {
		  line = "HoursOvertime = 115.7";
		  LineType theLineType = processor.process(line);
		  assertEquals(LineType.ASSIGNMENT_LINE, theLineType);
	}
	
	/*Test Case ID 202:
	 * 
	 */
	@Test  
		public void shouldDismissAssignmentLineWithoutHours() {
		  line = "HoursOvertime = ";
	      LineType theLineType = processor.process(line);
	      assertEquals(LineType.INVALID_LINE, theLineType);
	}
	
	/*Test Case ID 203:
	 * 
	 */
	@Test  
		public void shouldDismissAssignmentLineWithWrongHourFormat() {
		  line = "HoursOvertime = 10 17";
		  LineType theLineType = processor.process(line);
		  assertEquals(LineType.INVALID_LINE, theLineType);
	}
	
	/*Test Case ID 204:
	 * 
	 */
	@Test  
		public void shouldDismissAssignmentLineWithWrongLabel() {
		  line = "Hours24 = 24.0";
	      LineType theLineType = processor.process(line);
	      assertEquals(LineType.INVALID_LINE, theLineType);
	}
	
	/*Test Case ID 205:
	 * 
	 */
	@Test  
		public void shouldDismissAssignmentLineWithoutAssignment() {
		  line = "HoursOvertime + 17.9";
		  LineType theLineType = processor.process(line);
		  assertEquals(LineType.INVALID_LINE, theLineType);
	}
	
	/*Test Case ID 206:
	 * 
	 */
	@Test  
		public void shouldDismissAssignmentLineWithHoursAsString() {
		  line = "HoursOvertime = eleventeen";
	      LineType theLineType = processor.process(line);
	      assertEquals(LineType.INVALID_LINE, theLineType);
	}
	
  
  
  /*Test Case ID 301:
   * 
	 */
	@Test  
		public void shouldAcceptWellformedWeekLineWithMinHours() {
		  line = "Week 1 :      0  :     0";
	      LineType theLineType = processor.process(line);
	      assertEquals(LineType.WEEK_SPECIFICATION, theLineType);
	}
  
  /*Test Case ID 302:
	 * 
	 */
	@Test  
		public void shouldDismissWeekLineWithoutLastToken() {
		  line = "Week 1 :      0  :";
	      LineType theLineType = processor.process(line);
	      assertEquals(LineType.INVALID_LINE, theLineType);
	}
  
  /*Test Case ID 303:
	 * 
	 */
	@Test  
		public void shouldDismissWeekLineWithExtraToken() {
		  line = "Week 1 :      0  :     0   0";
	      LineType theLineType = processor.process(line);
	      assertEquals(LineType.INVALID_LINE, theLineType);
	}
  
  /*Test Case ID 304:
	 * 
	 */
	@Test  
		public void shouldDismissWeekLineWithoutWeekNumberAsInteger() {
		  line = "Week x :      0  :      0";
	      LineType theLineType = processor.process(line);
	      assertEquals(LineType.INVALID_LINE, theLineType);
	}
  
  /*Test Case ID 305:
	 * 
	 */
	@Test  
		public void shouldDismissWeekLineWithoutDaysWorkedAsNumericValue() {
		  line = "Week 1 :      Y  :      0";
	      LineType theLineType = processor.process(line);
	      assertEquals(LineType.INVALID_LINE, theLineType);
	}
  
   /*Test Case ID 306:
	 * 
	 */
	@Test  
		public void shouldDismissWeekLineWithoutDaysOnVacationAsNumericValue() {
		  line = "Week 1 :      0  :      +";
	      LineType theLineType = processor.process(line);
	      assertEquals(LineType.INVALID_LINE, theLineType);
	}
  
  
   /*Test Case ID 307:
	 * 
	 */
	@Test  
		public void shouldDismissWeekLineWithoutWellformedFirstToken() {
		  line = "Wee 1 :         1 :     1";
	      LineType theLineType = processor.process(line);
	      assertEquals(LineType.INVALID_LINE, theLineType);
	}
  
  /*Test Case ID 308:
	 * 
	 */
	@Test  
		public void shouldDismissWeekLineWithoutWellformedThirdToken() {
		  line = "Week 3 ^      1 :      1";
	      LineType theLineType = processor.process(line);
	      assertEquals(LineType.INVALID_LINE, theLineType);
	}
  
   /*Test Case ID 309:
	 * 
	 */
	@Test  
		public void shouldDismissWeekLineWithoutWellformedFifthToken() {
		  line = "Week 3 :       1 .      1";
	      LineType theLineType = processor.process(line);
	      assertEquals(LineType.INVALID_LINE, theLineType);
	}
  
   /*Test Case ID 310:
   * 
	 */
	@Test  
		public void shouldAcceptWellformedWeekLineWithMaxHours() {
		  line = "Week 52  :     5 :     5";
	      LineType theLineType = processor.process(line);
	      assertEquals(LineType.WEEK_SPECIFICATION, theLineType);
	}
  
  /*Test Case ID 311:
	 * 
	 */
	@Test  
		public void shouldDismissWeekLineWithInvalidWeekNumberZero() {
		  line = "Week 0 :      2 :       2";
	      LineType theLineType = processor.process(line);
	      assertEquals(LineType.INVALID_LINE, theLineType);
	}
  
  /*Test Case ID 312:
	 * 
	 */
	@Test  
		public void shouldDismissWeekLineWithInvalidWeekNumberFiftyThree() {
		  line = "Week 53 :   2 :       2";
	      LineType theLineType = processor.process(line);
	      assertEquals(LineType.INVALID_LINE, theLineType);
	}
  
  /*Test Case ID 313:
	 * 
	 */
	@Test  
		public void shouldDismissWeekLineWithInvalidFourthTokenNegativeValue() {
		  line = "Week 7 :     -1 :     1";
	      LineType theLineType = processor.process(line);
	      assertEquals(LineType.INVALID_LINE, theLineType);
	}
  
   /*Test Case ID 314:
	 * 
	 */
	@Test  
		public void shouldDismissWeekLineWithInvalidSixthTokenNegativeValue() {
		  line = "Week 7 :      1 :    -1";
	      LineType theLineType = processor.process(line);
	      assertEquals(LineType.INVALID_LINE, theLineType);
	}
  
   
   /*Test Case ID 315:
	 * 
	 */
	@Test  
		public void shouldDismissWeekLineWithInvalidFourthTokenOverMax() {
		  line = "Week 40 :    6 :     4";
	      LineType theLineType = processor.process(line);
	      assertEquals(LineType.INVALID_LINE, theLineType);
	}
  
   
   /*Test Case ID 316:
	 * 
	 */
	@Test  
		public void shouldDismissWeekLineWithInvalidSixthTokenOverMax() {
		  line = "Week 40 :    4 :     6";
	      LineType theLineType = processor.process(line);
	      assertEquals(LineType.INVALID_LINE, theLineType);
	}
  
   
   /*Test Case ID 401:
	 * 
	 */
	@Test  
		public void shouldAcceptWellformedWeekdayLineWithMondayBike() {
		  line = "Mon    Bi";
	      LineType theLineType = processor.process(line);
	      assertEquals(LineType.WEEKDAY_SPECIFICATION, theLineType);
	}
  
   /*Test Case ID 402:
	 * 
	 */
	@Test  
		public void shouldAcceptWellformedWeekdayLineWithTuesdayCarStuff() {
		  line = "Tue    Ca    Stuff";
	      LineType theLineType = processor.process(line);
	      assertEquals(LineType.WEEKDAY_SPECIFICATION, theLineType);
	}
  
  /*Test Case ID 403:
	 * 
	 */
	@Test  
		public void shouldDismissFaultyWeekdayLineWithOnlyOneToken() {
		  line = "Mon";
	      LineType theLineType = processor.process(line);
	      assertEquals(LineType.INVALID_LINE, theLineType);
	}
   /*Test Case ID 404:
	 * 
	 */
	@Test  
		public void shouldAcceptWellformedWeekdayLineWithWednesdayPublicTransport() {
		  line = "Wed    Pu";
	      LineType theLineType = processor.process(line);
	      assertEquals(LineType.WEEKDAY_SPECIFICATION, theLineType);
	}
  
   /*Test Case ID 405:
	 * 
	 */
	@Test  
		public void shouldAcceptWellformedWeekdayLineWithThursdayTrain() {
		  line = "Thu    Tr";
	      LineType theLineType = processor.process(line);
	      assertEquals(LineType.WEEKDAY_SPECIFICATION, theLineType);
	}
  
   
   /*Test Case ID 406:
	 * 
	 */
	@Test  
		public void shouldAcceptWellformedWeekdayLineWithFridayNonWorkDay() {
		  line = "Fri    No";
	      LineType theLineType = processor.process(line);
	      assertEquals(LineType.WEEKDAY_SPECIFICATION, theLineType);
	}
  
    /*Test Case ID 407:
	 * 
	 */
	@Test  
		public void shouldAcceptWellformedWeekdayLineWithSaturdayHome() {
		  line = "Sat    Ho";
	      LineType theLineType = processor.process(line);
	      assertEquals(LineType.WEEKDAY_SPECIFICATION, theLineType);
	}
  
    /*Test Case ID 408:
	 * 
	 */
	@Test  
		public void shouldAcceptWellformedWeekdayLineWithSundayBike() {
		  line = "Sun    Bi";
	      LineType theLineType = processor.process(line);
	      assertEquals(LineType.WEEKDAY_SPECIFICATION, theLineType);
	}
  
    /*Test Case ID 409:
	 * 
	 */
	@Test  
		public void shouldDismissFaultyWeekdayLineWithIllegalFirstToken() {
		  line = "abc    Bi";
	      LineType theLineType = processor.process(line);
	      assertEquals(LineType.INVALID_LINE, theLineType);
	}
  
  
    /*Test Case ID 410:
	 * 
	 */
	@Test  
		public void shouldDismissFaultyWeekdayLineWithIllegalSecondToken() {
		  line = "Sun    ab";
	      LineType theLineType = processor.process(line);
	      assertEquals(LineType.INVALID_LINE, theLineType);
	}
  
    /*Test Case ID 501:
	 * 
	 */
	@Test  
		public void shouldAcceptWellformedWorkLineWithSpaceStringAndInteger() {
		  line = " mtt  plan  2";
	      LineType theLineType = processor.process(line);
	      assertEquals(LineType.INVALID_LINE, theLineType);
	}
  
    /*Test Case ID 501:
	 * 
	 */
	@Test  
		public void shouldAcceptWellformedWorkLineWithSpaceCharAndDouble() {
		  line = " adm  - 0.5";
	      LineType theLineType = processor.process(line);
	      assertEquals(LineType.INVALID_LINE, theLineType);
	}
  
  
}
