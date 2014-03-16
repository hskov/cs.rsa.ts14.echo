package cs.rsa.ts14.hotel;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cs.rsa.ts14.hotel.doubles.ReportDataSpy;


public class TestTransportModeOverviewBuilder {

	// For inspection of UUT
	private ReportDataSpy spy;
	private TransportModeOverviewBuilder builder; 
	
	@Before
	public void setUp() throws Exception {
		// Default setup starting from scratch every time
		spy = new ReportDataSpy(new ReportDataImpl());
		builder = new TransportModeOverviewBuilder(spy);
		builder.buildBegin();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void T1_BuildWeekDaySpecification_accepts_mon_car() {
		builder.buildWeekDaySpecification("Mon", "Ca");
		// Tjekker at lige præcist den forventede værdi er rørt
		assertEquals(1, spy.getValueFor(TransportMode.CAR));
		// ...og at alle andre værdier IKKE er rørt, hvilket er et implicit krav
		assertEquals(1, spy.timesAddValueToIsCalled);
	}
	
	@Test
	public void T2_BuildWeekDaySpecification_accepts_tue_bi() {
		builder.buildWeekDaySpecification("Tue", "Bi");
		assertEquals(1, spy.getValueFor(TransportMode.BICYCLE));
		assertEquals(1, spy.timesAddValueToIsCalled);
	}

	@Test
	public void T3_BuildWeekDaySpecification_accepts_wed_pu() {
		builder.buildWeekDaySpecification("Wed", "Pu");
		assertEquals(1, spy.getValueFor(TransportMode.PUBLIC_TRANSPORTATION));
		assertEquals(1, spy.timesAddValueToIsCalled);
	}

	@Test
	public void T4_BuildWeekDaySpecification_accepts_thu_tr() {
		builder.buildWeekDaySpecification("Thu", "Tr");
		assertEquals(1, spy.getValueFor(TransportMode.TRAVELLING));
		assertEquals(1, spy.timesAddValueToIsCalled);
	}
	
	@Test
	public void T5_BuildWeekDaySpecification_accepts_fri_ho() {
		builder.buildWeekDaySpecification("Fri", "Ho");
		assertEquals(1, spy.getValueFor(TransportMode.HOME));
		assertEquals(1, spy.timesAddValueToIsCalled);
	}

	@Test
	public void T6_BuildWeekDaySpecification_accepts_sat_no() {
		builder.buildWeekDaySpecification("Sat", "No");
		assertEquals(1, spy.getValueFor(TransportMode.NON_WORKING_DAYS));
		assertEquals(1, spy.timesAddValueToIsCalled);
	}
	
	@Test
	public void T7_BuildWeekDaySpecification_accepts_sun_foo() {
		builder.buildWeekDaySpecification("Sun", "Foo");
		assertEquals(1, spy.getValueFor(TransportMode.UNKNOWN));
		assertEquals(1, spy.timesAddValueToIsCalled);
	}
	
	@Test
	public void T8_BuildWeekDaySpecification_does_not_accept_unknown_weekdays() {
		builder.buildWeekDaySpecification("Fun", "Ca");
		assertEquals(0, spy.getValueFor(TransportMode.CAR));
		assertEquals(0, spy.timesAddValueToIsCalled);
	}

	@Test
	public void T9_BuildBegin_clears_all_data() {
		spy.addValueTo(TransportMode.CAR, 1);
		spy.addValueTo(TransportMode.BICYCLE, 2);
		spy.addValueTo(TransportMode.PUBLIC_TRANSPORTATION, 3);
		spy.addValueTo(TransportMode.TRAVELLING, 4);
		spy.addValueTo(TransportMode.HOME, 5);
		spy.addValueTo(TransportMode.NON_WORKING_DAYS, 6);
		spy.addValueTo(TransportMode.UNKNOWN, 7);
		spy.setResult("Test");

		builder.buildBegin();

		assertEquals(0, spy.getValueFor(TransportMode.CAR));
		assertEquals(0, spy.getValueFor(TransportMode.BICYCLE));
		assertEquals(0, spy.getValueFor(TransportMode.PUBLIC_TRANSPORTATION));
		assertEquals(0, spy.getValueFor(TransportMode.TRAVELLING));
		assertEquals(0, spy.getValueFor(TransportMode.HOME));
		assertEquals(0, spy.getValueFor(TransportMode.NON_WORKING_DAYS));
		assertEquals(0, spy.getValueFor(TransportMode.UNKNOWN));
		
		assertNull(spy.getResult()); 
	}

	@Test
	public void T10_BuildEnd_builds_a_report_string_and_sets_it_as_result() {
		spy.addValueTo(TransportMode.CAR, 1);
		spy.addValueTo(TransportMode.BICYCLE, 2);
		spy.addValueTo(TransportMode.PUBLIC_TRANSPORTATION, 3);
		spy.addValueTo(TransportMode.TRAVELLING, 4);
		spy.addValueTo(TransportMode.HOME, 5);
		spy.addValueTo(TransportMode.NON_WORKING_DAYS, 6);
		spy.addValueTo(TransportMode.UNKNOWN, 7);
		spy.setResult(null);

		builder.buildEnd();
		assertNotNull(spy.getResult()); 
	}
	
	@Test
	public void T11_BuildWeekDaySpecification_does_not_accept_null_as_weekday() {
		builder.buildWeekDaySpecification(null, "Ca");
		assertEquals(0, spy.getValueFor(TransportMode.CAR));
		assertEquals(0, spy.timesAddValueToIsCalled);
	}

	@Test
	public void T12_BuildEnd_builds_a_report_without_weekday_input() {
		builder.buildEnd();
		assertNotNull(spy.getResult()); 
	}
	
	@Test
	public void T13_BuildEnd_builds_a_report_containing_correct_values_in_expected_sequence() {
		spy.addValueTo(TransportMode.CAR, 1);
		spy.addValueTo(TransportMode.BICYCLE, 20);
		spy.addValueTo(TransportMode.PUBLIC_TRANSPORTATION, 300);
		spy.addValueTo(TransportMode.TRAVELLING, 4);
		spy.addValueTo(TransportMode.HOME, 50);
		spy.addValueTo(TransportMode.NON_WORKING_DAYS, 600);
		spy.addValueTo(TransportMode.UNKNOWN, 7);
		spy.setResult(null);

		builder.buildEnd();
		assertNotNull(spy.getResult()); 

		// Pattern tjekker hele strengen og melder kun ok hvis de korrekte data (og kun disse) findes i den korrekte rækkefølge 
		String pattern = String.format("^\\D*%d\\D*%d\\D*%d\\D*%d\\D*%d\\D*%d\\D*%d\\D*$", 1, 20, 300, 4, 50, 600, 7);
		assertTrue(spy.getResult().matches(pattern));
	}

	@Test
	public void T14_BuildWeekDaySpecification_handles_several_adds_to_the_same_transport_mode() {
		builder.buildWeekDaySpecification("Sun", "Foo");
		builder.buildWeekDaySpecification("Sun", "Bar");
		assertEquals(2, spy.getValueFor(TransportMode.UNKNOWN));
		assertEquals(2, spy.timesAddValueToIsCalled);
	}

}
