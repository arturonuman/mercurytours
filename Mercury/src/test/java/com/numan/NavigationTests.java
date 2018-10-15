package com.numan;

import static org.junit.Assert.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
/**
 * This Test Set exercises the navigation flows/paths
 * and verifies the pages' URLs and Titles.
 * @author Arturo Nunez
 */
class NavigationTests {

	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	public static void tearDownAfterClass() throws Exception {
		Framework.close();
		Framework.quit();
	}

	@BeforeEach
	public void setUp() throws Exception {
		Mercury.loadPage();
	}

	@AfterEach
	public void tearDown() throws Exception {
		Framework.deleteAllCookies();
	}

	@Test
	public void loadHomePage() {
		assertTrue("HomePage URL does not match", Framework.isAt(Mercury.url));
		assertTrue("HomePage Title does not match", Framework.isTitle(Mercury.title));
	}
	
	@Test
	public void loadSignOnPage() {
		Mercury.navigateTo().signOn();
		assertTrue("SignOn URL does not match", Framework.isAt(SignOn.url));
		assertTrue("SignOn Title does not match", Framework.isTitle(SignOn.title));
	}
	
	@Test
	public void loadFlightFinder() {
		Mercury.navigateTo().signOn();
		Mercury.signOn().login(Mercury.username, Mercury.password);
		assertTrue("FlightFinder URL does not match", Framework.isAt(FlightFinder.url));
		assertTrue("FlightFinder Title does not match", Framework.isTitle(FlightFinder.title));
	}
	
	@Test
	public void loadSelectFlight() {
		Mercury.navigateTo().signOn();
		Mercury.signOn().login(Mercury.username, Mercury.password);
		Mercury.flightFinder().clickContinue();
		assertTrue("SelectFlight URL does not match", Framework.isAt(SelectFlight.url));
		assertTrue("SelectFlight Title does not match", Framework.isTitle(SelectFlight.title));
	}
	
	@Test
	public void loadBookFlight() {
		Mercury.navigateTo().signOn();
		Mercury.signOn().login(Mercury.username, Mercury.password);
		Mercury.flightFinder().clickContinue();
		Mercury.selectFlight().clickContinue();
		assertTrue("BookFlight URL does not match", Framework.isAt(BookFlight.url));
		assertTrue("BookFlight Title does not match", Framework.isTitle(BookFlight.title));
	}
	
	@Test
	public void loadConfirmFlight() {
		Mercury.navigateTo().signOn();
		Mercury.signOn().login(Mercury.username, Mercury.password);
		Mercury.flightFinder().clickContinue();
		Mercury.selectFlight().clickContinue();
		Mercury.bookFlight().purchaseFlights();
		assertTrue("ConfirmFlight URL does not match", Framework.isAt(FlightConfirmation.url));
		assertTrue("ConfirmFlight Title does not match", Framework.isTitle(FlightConfirmation.title));
	}
	
	@Test
	public void logOutFromConfirmFlight() {
		Mercury.navigateTo().signOn();
		Mercury.signOn().login(Mercury.username, Mercury.password);
		Mercury.flightFinder().clickContinue();
		Mercury.selectFlight().clickContinue();
		Mercury.bookFlight().purchaseFlights();
		Mercury.flightConfirmation().logOut();
		assertTrue("SignOn URL does not match", Framework.isAt(SignOn.url));
		assertTrue("SignOn Title does not match", Framework.isTitle(SignOn.title));
	}
	
	@Test
	public void backToHomeFromConfirmFlight() {
		Mercury.navigateTo().signOn();
		Mercury.signOn().login(Mercury.username, Mercury.password);
		Mercury.flightFinder().clickContinue();
		Mercury.selectFlight().clickContinue();
		Mercury.bookFlight().purchaseFlights();
		Mercury.flightConfirmation().backToHome();
		assertTrue("HomePage URL does not match", Framework.isAt(Mercury.url));
		assertTrue("HomePage Title does not match", Framework.isTitle(Mercury.title));
	}
	
	@Test
	public void backToFlightsFromConfirmFlight() {
		Mercury.navigateTo().signOn();
		Mercury.signOn().login(Mercury.username, Mercury.password);
		Mercury.flightFinder().clickContinue();
		Mercury.selectFlight().clickContinue();
		Mercury.bookFlight().purchaseFlights();
		Mercury.flightConfirmation().backToFlights();
		assertTrue("HomePage URL does not match", Framework.isAt(FlightFinder.url));
		assertTrue("HomePage Title does not match", Framework.isTitle(FlightFinder.title));
	}

}
