package com.numan;

import com.numan.SelectFlight.FlightWay;

public class FlightConfirmation {
	
	static String url = "http://newtours.demoaut.com/mercurypurchase2.php";
	static String title = "Flight Confirmation: Mercury Tours";
	
	static String lblFlightConfitmationDetails = "xpath=//td[@class=\"frame_header_info\"]/font";
	static String lblFlightPassengersDetails = "xpath=//td[@class=\"data_left\"]/font[contains(text(),\"passenger\")]";
	static String lblBilledToDetails = "xpath=//font[contains(text(),\"{N}\")]";
	static String lblTotalTaxes = 
			"xpath=//tr[./td/b/font/font/font[contains(text(),\"Taxes\")]]/td[2]//font[contains(text(),\"USD\")]";
	static String lblTotalPrice = 
			"xpath=//tr[./td/b/font/font/b/font[contains(text(),\"Total\")]]/td[2]//font[contains(text(),\"USD\")]";
	
	static String btnLogOut = "xpath=//a[./img[@src=\"/images/forms/Logout.gif\"]]";
	static String btnBackToHome = "xpath=//a[./img[@src=\"/images/forms/home.gif\"]]";
	static String btnBackToFlights = "xpath=//a[./img[@src=\"/images/forms/backtoflights.gif\"]]";

	public void logOut() {
		Framework.click(btnLogOut);
	}
	
	public void backToHome() {
		Framework.click(btnBackToHome);
	}
	
	public void backToFlights() {
		Framework.click(btnBackToFlights);
	}

	public boolean doNumberOfPassengersMatch(int passengers) {
		return Framework.getText(lblFlightPassengersDetails).contains(Integer.toString(passengers));
	}

	public boolean doTotalFlightPriceMatch(String departurePrice, String returnPrice, int passengers) {
		int totalTax = Integer.parseInt(Framework.getText(lblTotalTaxes).replace("$", "").replace("USD", "").trim());
		int totalPrice = Integer.parseInt(Framework.getText(lblTotalPrice).replace("$", "").replace("USD", "").trim());
		int subtotalConfirmedPrice = (Integer.parseInt(departurePrice) + Integer.parseInt(returnPrice)) * passengers;
		int totalConfirmedPrice = totalTax + subtotalConfirmedPrice;
		return totalPrice == totalConfirmedPrice;
	}

	public boolean doFlightNumberMatch(FlightWay departure, int departureFlightNumber) {
		return Framework.getElements(lblFlightConfitmationDetails).get(departure.ordinal()).getText().contains(Integer.toString(departureFlightNumber));
		
	}

}
