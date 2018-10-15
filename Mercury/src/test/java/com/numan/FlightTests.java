package com.numan;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import com.numan.SelectFlight.FlightWay;
/**
 * This Test Set exercises the Booking Flight functionality
 * with an external data source that allows parameterization.
 * Test focused on data validation.
 * @author Arturo Nunez
 */
class FlightTests {

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
		Mercury.navigateTo().flights();
	}

	@AfterEach
	public void tearDown() throws Exception {
	}
	
	@DisplayName("Parameterized Book Flights")
	@ParameterizedTest(name = "\"{1} {0}\" flights")
	@CsvFileSource(resources = "flights.csv")
	public void bookFlights(String flightType, int passengers,
			String fromCity, String fromMonth, int fromDate,
			String toCity, String toMonth, int toDate,
			String svcClass, String airline,
			int departureFlightNumber, int returnFlightNumber,
			String passengersList, int passengerCardOwner,
			String cardType, String cardNumber, String expMonth, int expYear) throws InterruptedException {
		String departurePrice;
		String returnPrice;
		
		// Fill FLIGHT FINDER page
		Mercury.flightFinder().fillFlightFinder(flightType, passengers,
				fromCity, toCity, fromMonth, fromDate,toMonth, toDate, svcClass, airline);
		Mercury.flightFinder().clickContinue();

		// Fill SELECT FLIGHT page and get Prices
		departurePrice = Mercury.selectFlight()
				.selectFlightBasedOnFlightNumber(flightType, FlightWay.DEPARTURE, departureFlightNumber);
		returnPrice = Mercury.selectFlight()
				.selectFlightBasedOnFlightNumber(flightType, FlightWay.RETURN, returnFlightNumber);
		Mercury.selectFlight().clickContinue();
		
		// ASSERTIONS: Flight prices and Total price
		assertTrue("Book Departure Flight Price does not match with Selected",
				Mercury.bookFlight().doFlightPriceMatch(FlightWay.DEPARTURE, departureFlightNumber, departurePrice));
		if(flightType.equals("ROUND")) {
			assertTrue("Book Return Flight Price does not match with Selected",
					Mercury.bookFlight().doFlightPriceMatch(FlightWay.RETURN, returnFlightNumber, returnPrice));
		}
		assertTrue("Book TOTAL Flight Price does not match with Selected",
				Mercury.bookFlight().doTotalFlightPriceMatch(departurePrice, returnPrice, passengers));
		
		// Fill Passengers
		Mercury.bookFlight().fillBookFlight(passengersList, passengerCardOwner, cardType, cardNumber, expMonth, expYear);
		Mercury.bookFlight().purchaseFlights();
		
		// ASSERTIONS: Flight Number, Number of Passengers, Total Price
		assertTrue("Confirmation Departure Flight Number does not match with Selected",
				Mercury.flightConfirmation().doFlightNumberMatch(FlightWay.DEPARTURE, departureFlightNumber));
		if(flightType.equals("ROUND")) {
			assertTrue("Confirmation Return Flight Number does not match with Selected",
					Mercury.flightConfirmation().doFlightNumberMatch(FlightWay.RETURN, returnFlightNumber));
		}
		assertTrue("Number of Passengers does not match with Selected",
				Mercury.flightConfirmation().doNumberOfPassengersMatch(passengers));
		assertTrue("Confirmation TOTAL Flight Price does not match with Selected",
				Mercury.flightConfirmation().doTotalFlightPriceMatch(departurePrice, returnPrice, passengers));
	}

}
