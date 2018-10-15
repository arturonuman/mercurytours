package com.numan;

public class SelectFlight {
	
	
	static String url = "http://newtours.demoaut.com/mercuryreservation2.php";
	static String title = "Select a Flight: Mercury Tours";
	
	static String tblReturn = "xpath=//font[@color=\"#FF9900\" and contains(text(),\"RETURN\")]"
;	static String ckbPriceBasedFlight =
			"xpath=//form/table[{#}]//tr[.//b[contains(text(),\"{$}\")]]/preceding-sibling::tr[1]//input";
	static String ckbTimeBasedFlight = 
			"xpath=//form/table[{#}]//tr[.//font[contains(text(),\"{@}\")]]/td/input";
	static String ckbFlightNumberBasedFlight = 
			"xpath=//form/table[{#}]//tr[.//b[contains(text(),\"{F}\")]]/td/input";
	static String lblFlightNumberBasedPrice = 
			"xpath=//form/table[{#}]//tr[.//b[contains(text(),\"{F}\")]]/following-sibling::tr[1]//b";
	static String btnContinue = "name=reserveFlights";
	
	
	enum FlightWay {
		DEPARTURE, RETURN;
	}
	
	public void selectFlightBasedOnPrice(FlightWay flightWay, int price) {
		String locator = ckbPriceBasedFlight
				.replace("{#}", Integer.toString(flightWay.ordinal()+1))
				.replace("{$}", Integer.toString(price));
		Framework.click(locator);
	}
	
	public void selectFlightBasedOnTime(FlightWay flightWay, String time) {
		String locator = ckbTimeBasedFlight
				.replace("{#}", Integer.toString(flightWay.ordinal()+1))
				.replace("{@}", time);
		Framework.click(locator);
	}
	
	public String selectFlightBasedOnFlightNumber(String flightType, FlightWay flightWay, int flightNumber) {
		if (flightType.equals("ROUND") || flightWay.toString().equals("DEPARTURE") ) {
			String locator = ckbFlightNumberBasedFlight
					.replace("{#}", Integer.toString(flightWay.ordinal()+1))
					.replace("{F}", Integer.toString(flightNumber));
			Framework.click(locator);
			return getPriceFromFlight(flightWay, flightNumber);
		} else {
			return "0";
		}
	}
	
	public String getPriceFromFlight(FlightWay flightWay, int flightNumber) {
		String locator = lblFlightNumberBasedPrice
				.replace("{#}", Integer.toString(flightWay.ordinal()+1))
				.replace("{F}", Integer.toString(flightNumber));
		return Framework.getText(locator)
				.replace("Price:", "")
				.replace("$", "")
				.trim();
	}

	public void clickContinue() {
		Framework.click(btnContinue);
	}

	public boolean isReturnTablePresent() {
		return Framework.isPresent(tblReturn);
	}

}
