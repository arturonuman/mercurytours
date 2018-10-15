package com.numan;

public class Mercury {

	static String url = "http://newtours.demoaut.com";
	static String title = "Welcome: Mercury Tours";
	
	static String username = "mercury";
	static String password = "mercury";
	
	public static void loadPage() {
		Framework.goTo(url);
	}
	
	public static NavigateTo navigateTo() {
		return new NavigateTo();
	}
	
	public static SignOn signOn() {
		return new SignOn();
	}
	
	public static FlightFinder flightFinder() {
		return new FlightFinder();
	}
	
	public static SelectFlight selectFlight() {
		return new SelectFlight();
	}
	
	public static BookFlight bookFlight() {
		return new BookFlight();
	}

	public static FlightConfirmation flightConfirmation() {
		return new FlightConfirmation();
	}
}
