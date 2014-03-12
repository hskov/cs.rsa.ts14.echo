package cs.rsa.ts14.echo;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import cs.rsa.ts14.Golf.statemachine.InitialState;
import cs.rsa.ts14.doubles.FaultyLineTypeClassifierStrategy;
import cs.rsa.ts14.echo.doubles.EmptyBuilderStub;
import cs.rsa.ts14.framework.LineType;
import cs.rsa.ts14.framework.TimesagLineProcessor;
import cs.rsa.ts14.standard.StandardTimesagLineProcessor;

/**
 * @author henning
 * This JUnit test case is implementing the integration tests between the 
 * StandardTimesagLineProcessor and the statemachine implementation from the
 * Golf group. 
 * 
 */
public class TopDownIntegration {

	private TimesagLineProcessor processor;
	
	@Before
	public void setup() {
		
		/** 
		 * Create an instance of StandardTimesagLineProcessor and provide it with references to:
		 *  - Statemachine implementation from the Golf group (start in InitialState)
		 *  - Stub for a LineTypeClassifierStrategy. 
		 *  - Stub for an implementation of the builder interface.
		 */
		processor = new StandardTimesagLineProcessor(
			new FaultyLineTypeClassifierStrategy(), 
			new EmptyBuilderStub(), 
			new InitialState()); 
	}
	
	@Test
	public void testIllegalStateTransition() {
		// The input is chosen so that FaultyLineTypeClassifierStrategy() returns LineType.EMPTY_LINE
		String input = "";
		
		// The value of lastError should only change when statemachine has entered the IllegalState.
		String lastError = processor.lastError();
		
		// We are allowed to have two consecutive empty lines 
		LineType lineType = processor.process(input);
		assertEquals(lineType, LineType.EMPTY_LINE);
		assertEquals(lastError, processor.lastError());
		
		lineType = processor.process(input);
		assertEquals(lineType, LineType.EMPTY_LINE);
		assertEquals(lastError, processor.lastError());
		
		// The third occurrence of an empty line should result in LineType.INVALID_SEQUENCE
		lineType = processor.process(input);
		assertEquals(LineType.INVALID_SEQUENCE, lineType);
		assertEquals("Illegal to have three consecutive empty lines.", processor.lastError());
	}

}
