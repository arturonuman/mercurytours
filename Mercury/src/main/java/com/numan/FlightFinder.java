package com.numan;

import java.time.Month;

public class FlightFinder {
	

	static String url = "http://newtours.demoaut.com/mercuryreservation.php";
	static String title = "Find a Flight: Mercury Tours:";
	
	static String ckbType = "name=tripType";
	static String selPassengers = "name=passCount";
	static String selFrom = "name=fromPort";
	static String selFromMonth = "name=fromMonth";
	static String selFromDay = "name=fromDay";
	static String selTo = "name=toPort";
	static String selToMonth = "name=toMonth";
	static String selToDay = "name=toDay";
	static String ckbClass = "name=servClass";
	static String selAirline = "name=airline";
	static String btnContinue = "name=findFlights";
	
	enum FlightType { 
	    ROUND, ONEWAY; 
	}
	enum ServiceClass {
		ECONOMY, BUSINESS, FIRST;
	}
	enum AirLine {
		NOPREFERENCE, BLUESKIES, UNIFIED, PANGEA;
	}
	enum Cities {
		ACAPULCO, FRANKFURT, LONDON, NEWYORK, PARIS, PORTLAND, SANFRANCISCO, SEATTLE, SYDNEY, ZURICH;
	}

	public void selectTypeAndPassengers(FlightType type, int number) {
		Framework.clickCheckBoxByIndex(ckbType, type.ordinal());
		Framework.setSelectByValue(selPassengers, Integer.toString(number));
	}
	
	public void selectFromToCities(Cities departing, Cities arriving) {
		Framework.setSelectByIndex(selFrom, departing.ordinal());
		Framework.setSelectByIndex(selTo, arriving.ordinal());
	}
	
	public void selectFromDate(Month month, int number) {
		Framework.setSelectByIndex(selFromMonth, month.ordinal());
		Framework.setSelectByValue(selFromDay, Integer.toString(number));
	}
	
	public void selectToDate(Month month, int number) {
		Framework.setSelectByIndex(selToMonth, month.ordinal());
		Framework.setSelectByValue(selToDay, Integer.toString(number));
	}

	public void selectPreferences(ServiceClass servClass, AirLine airline) {
		Framework.clickCheckBoxByIndex(ckbClass, servClass.ordinal());
		Framework.setSelectByIndex(selAirline, airline.ordinal());
	}
	
	public void clickContinue() {
		Framework.click(btnContinue);
	}

	public boolean areReturningMonthDayEnabled() {
		return Framework.isEnabled(selToMonth) && Framework.isEnabled(selToDay);
	}

	public void fillFlightFinder(String flightType, int passengers, String fromCity, String toCity, String fromMonth,
			int fromDate, String toMonth, int toDate, String svcClass, String airline) {
		selectTypeAndPassengers(FlightType.valueOf(flightType), passengers);
		selectFromToCities(Cities.valueOf(fromCity), Cities.valueOf(toCity));
		selectFromDate(Month.valueOf(fromMonth), fromDate);
		if(flightType.equals("ROUND")) {
			selectToDate(Month.valueOf(toMonth), toDate);
		}
		selectPreferences(ServiceClass.valueOf(svcClass), AirLine.valueOf(airline));
	}

}
