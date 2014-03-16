package cs.rsa.ts14.echo;

import static org.junit.Assert.*;

import java.io.Console;

import org.junit.Before;
import org.junit.Test;

import cs.rsa.ts14.Golf.statemachine.InitialState;
import cs.rsa.ts14.echo.implLineTypeClassifierStrategy;
import cs.rsa.ts14.framework.TimesagLineProcessor;
import cs.rsa.ts14.foxtrot.CategoryReportImpl;
import cs.rsa.ts14.standard.StandardTimesagLineProcessor;

public class BottomUpIntegrationCategoryReport {

	private TimesagLineProcessor processor;
	
	@Before
	public void setup() {
		
		/** 
		 * Create an instance of StandardTimesagLineProcessor and provide it with references to:
		 *  - State machine implementation from the Golf group (start in InitialState) that has already been integrated using the top-down pattern.
		 *  - Our (echo) already integrated LineTypeClassifierStrategy implementaion. 
		 *  - Foxtrot report builder implementation.
		 */
		processor = new StandardTimesagLineProcessor(
			new implLineTypeClassifierStrategy(), 
			new CategoryReportImpl(), 
			new InitialState());
	}
	
	@Test
	public void testCategoryReportIntegration() {
		processor.beginProcess();
		processor.process("# Timesag system for NN 2013");
		
		processor.process("	");
		processor.process("# ===================================== Karakteristika for år");
		processor.process("HoursOvertime = 502.2");
		processor.process("Year = 2013");
		processor.process("");
		processor.process("# ============================================================");
		processor.process("");
		processor.process("Week 1 :	3	:	0");
		processor.process("");
		processor.process("Wed    		Ca				8.00");
		processor.process("	adm		-		1");
		processor.process("	sa		exam		3	forb question");
		processor.process("");
		processor.process("	sa		exam		1");
		processor.process("");
		processor.process("	sa		exam		2");
		processor.process("Thu		No		");
		processor.process("	syg		-		4.5");
		processor.process("	sa		exam		3");
		processor.process("Fri		Ho				8-");
		processor.process("	sa		exam		1");
		processor.process("	n4c		-		1");
		processor.process("	itevmd		-		1");
		processor.process("	mtt		-		1");
		processor.process("");
		processor.process("	mtt		plan		2");
		processor.process("	saip		plan		1.5");
		processor.process("");
		processor.process("");
		processor.process("Week 2 :	5	:	0");
		processor.process("");
		processor.process("Mon		Ca				8.00-");
		processor.process("	sa		exam		4");
		processor.process("	");
		processor.process("	sa		exam		2");
		processor.process("");
		processor.process("	adm		-		1");
		processor.process("Tue		Ca				8.30");
		processor.process("	sa		exam		3.5");
		processor.process("	");
		processor.process("	sa		exam		2.5");
		processor.process("	adm		-		0.5");
		processor.process("	itevmd		-		0.5");
		processor.process("");
		processor.process("Wed		Ca				8.00");
		processor.process("	adm		-		0.5");
		processor.process("	sa		exam		3.5");
		processor.process("");
		processor.process("	sa		exam		3");
		processor.process("	itevmd		-		0.5");
		processor.process("	");
		processor.process("Thu		Ca				8.00");
		processor.process("	adm		-		0.5");
		processor.process("	sa		exam		3.5");
		processor.process("");
		processor.process("	sa		exam		4");
		processor.process("	");
		processor.process("Fri		Ca				8.00-16.30");
		processor.process("	adm		-		0.5");
        processor.process("	censor		-		0.5");
        processor.process("	n4c		e2e		3");
        processor.process("");
        processor.process("	sa		protokol	2");
        processor.process("	saip		-		0.5");
        processor.process("	itevmd		-		0.5");
        processor.process("	sa		social		1.5");
        processor.process("");
        processor.process("Sat		No");
        processor.process("	sa		hotciv		1	rette hotciv kode til");
        processor.process("");
        processor.process("	es		litt		1.5");
        processor.process("");
        processor.process("Week 3 :	5	:	0");
        processor.process("");
        processor.process("Mon		Ca				8.30-15.30");
        processor.process("	adm		-		0.5");
        processor.process("	es		-		0.5");
        processor.process("	sa		-		1");
        processor.process("	adm		-		1.5");
        processor.process("");
        processor.process("	book2		errata		1");
        processor.process("	es		-		2.5");
        processor.process("");
        processor.process("");
        processor.process("	terna		course		1.5");
        processor.process("");
		
		processor.endProcess();
		String report = processor.getReport();
		
		
	}
}
