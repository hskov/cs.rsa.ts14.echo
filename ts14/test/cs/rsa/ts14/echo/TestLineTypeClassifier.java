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
	
}
