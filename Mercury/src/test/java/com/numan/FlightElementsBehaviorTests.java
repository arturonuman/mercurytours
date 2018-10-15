package com.numan;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.Month;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.numan.BookFlight.CreditCard;
import com.numan.BookFlight.Meal;
import com.numan.FlightFinder.FlightType;
/**
 * This Test Set exercises the Booking Flight flow
 * focusing on web elements' behavior.
 * @author Arturo Nunez
 */
class FlightElementsBehaviorTests {

	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
		Mercury.loadPage();
		Mercury.signOn().login(Mercury.username, Mercury.password);
	}

	@AfterAll
	public static void tearDownAfterClass() throws Exception {
		Framework.close();
		Framework.quit();
	}

	@BeforeEach
	public void setUp() throws Exception {
	}

	@AfterEach
	public void tearDown() throws Exception {
	}

	@Test
	public void roundFlightFinderBehavior() {
		Mercury.navigateTo().flights();
		Mercury.flightFinder().selectTypeAndPassengers(FlightType.ROUND, 1);
		assertTrue("Returning Month/Day should be ENABLED on a Round Flight",
				Mercury.flightFinder().areReturningMonthDayEnabled());
	}
	
	@Test
	public void oneWayFlightFinderBehavior() {
		Mercury.navigateTo().flights();
		Mercury.flightFinder().selectTypeAndPassengers(FlightType.ONEWAY, 1);
		assertFalse("Returning Month/Day should be DISABLED on a One Way Flight",
				Mercury.flightFinder().areReturningMonthDayEnabled());
	}
	
	@Test
	public void roundSelectFlightBehavior() {
		Mercury.navigateTo().flights();
		Mercury.flightFinder().selectTypeAndPassengers(FlightType.ONEWAY, 1);
		Mercury.flightFinder().clickContinue();
		assertTrue("Return flights table should be present on a Round Flight",
				Mercury.selectFlight().isReturnTablePresent());
	}
	
	@Test
	public void oneWaySelectFlightBehavior() {
		Mercury.navigateTo().flights();
		Mercury.flightFinder().selectTypeAndPassengers(FlightType.ONEWAY, 1);
		Mercury.flightFinder().clickContinue();
		assertFalse("Returning Month/Day should not be present on a One Way Flight",
				Mercury.selectFlight().isReturnTablePresent());
	}

	@Test
	public void roundBookFlightBehavior() {
		Mercury.navigateTo().flights();
		Mercury.flightFinder().selectTypeAndPassengers(FlightType.ONEWAY, 1);
		Mercury.flightFinder().clickContinue();
		Mercury.selectFlight().clickContinue();
		assertTrue("Return flights table should be present on a Round Flight",
				Mercury.bookFlight().isReturnFlightPresent());
	}
	
	@Test
	public void oneWayBookFlightBehavior() {
		Mercury.navigateTo().flights();
		Mercury.flightFinder().selectTypeAndPassengers(FlightType.ONEWAY, 1);
		Mercury.flightFinder().clickContinue();
		Mercury.selectFlight().clickContinue();
		assertFalse("Return flights table should not be present on a One Way Flight",
				Mercury.bookFlight().isReturnFlightPresent());
	}
	
	@Test
	public void purchaseButtonWithoutMandatoryDataBookFlightBehavior() {
		Mercury.navigateTo().flights();
		Mercury.flightFinder().selectTypeAndPassengers(FlightType.ONEWAY, 1);
		Mercury.flightFinder().clickContinue();
		Mercury.selectFlight().clickContinue();
		assertFalse("Purchase button should be disabled without mandatory data",
				Mercury.bookFlight().isPurchaseButtonEnabled());
	}
	
	@Test
	public void purchaseButtonWithMandatoryDataBookFlightBehavior() {
		Mercury.navigateTo().flights();
		Mercury.flightFinder().selectTypeAndPassengers(FlightType.ONEWAY, 1);
		Mercury.flightFinder().clickContinue();
		Mercury.selectFlight().clickContinue();
		Mercury.bookFlight().addPassenger(1, "dummy", "user", Meal.BLAND);
		Mercury.bookFlight().addPassengerBasedCreditCard(1, CreditCard.AMERICANEXPRESS, "123456", Month.APRIL, 2005);
		assertTrue("Purchase button should be enabled with mandatory data",
				Mercury.bookFlight().isPurchaseButtonEnabled());
	}
}
