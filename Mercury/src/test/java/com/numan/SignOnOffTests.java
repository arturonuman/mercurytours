package com.numan;
/**
 * This Test Set exercises the Sign-On functionality
 * from Home and SignOn pages, as well as the Sign-Off
 * functionality.
 * @author Arturo Nunez
 */
import static org.junit.Assert.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SignOnOffTests {

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
	public void signOnFromHomePage() {
		Mercury.signOn().login(Mercury.username, Mercury.password);
		assertTrue("Sign-On Failed. User not logged into FlighFinder page", Framework.isAt(FlightFinder.url));
	}

	@Test
	public void signOnFromSignOnPage() {
		Mercury.navigateTo().signOn();
		Mercury.signOn().login(Mercury.username, Mercury.password);
		assertTrue("Sign-On Failed. User not logged into FlighFinder page", Framework.isAt(FlightFinder.url));
	}

	@Test
	public void signOff() {
		Mercury.signOn().login(Mercury.username, Mercury.password);
		Mercury.navigateTo().signOnff();
		assertTrue("Sign-Off Failed. User not redirected to SignOn page", Framework.isAt(SignOn.url));
	}

}
