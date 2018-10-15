package com.numan;

import java.time.Month;
import java.util.List;

import com.numan.SelectFlight.FlightWay;

public class BookFlight {

	
	static String url = "http://newtours.demoaut.com/mercurypurchase.php";
	static String title = "Book a Flight: Mercury Tours";
	
	static String tblReturnFlight = "xpath=//td[@bgcolor=\"#003399\"]/font[contains(text(),\"FLIGHT\")]";
	static String lblDepartureFlightNumberBasedPrice = 
			"xpath=//td[.//b[contains(text(),\"{F}\")]]/following-sibling::td[2]/font";
	static String lblReturnFlightNumberBasedPrice =
			"xpath=//td[./font/font/font//b[contains(text(),\"{F}\")]]/following-sibling::td[2]/font";
	static String lblFlightTaxes = 
			"xpath=//td[./font[contains(text(),\"Taxes\")]]/following-sibling::td/font";
	static String lblFlightTotal = 
			"xpath=//td[./font[contains(text(),\"Total Price\")]]/following-sibling::td//b";
	
	
	static String inpPassFirst = "name=passFirst{#}";
	static String inpPassLast = "name=passLast{#}";
	static String inpPassMeal = "name=pass.{#}.meal";
	
	static String selCreditCard = "name=creditCard";
	static String inpCardNumber = "name=creditnumber";
	static String selCardExpMonth = "name=cc_exp_dt_mn";
	static String selCardExpYear = "name=cc_exp_dt_yr";
	static String inpCardFirstName = "name=cc_frst_name";
	static String inpCardMiddleName = "name=cc_mid_name";
	static String inpCardLastName = "name=cc_last_name";
	static String ckbTicketLess = "name=ticketLess";
	static String btnPurchase = "name=buyFlights";
	
	
	enum Meal {
		NOPREFERENCE, BLAND, DIABETIC, HINDU, KOSHER, LOWCALORIE, LOWCHOLESTEROL, LOWSODIUM, MUSLIM, VEGETARIAN;
	}
	
	enum CreditCard {
		AMERICANEXPRESS, MASTERCARD, VISA, DISCOVER, DINERSCLUB, CARTEBLANCHE;  
	}
	
	public void addPassenger(int passengerNumber, String firstName, String lastName, Meal meal) {
		String passFirstLocator = inpPassFirst
				.replace("{#}", Integer.toString(passengerNumber-1));
		String passLastLocator = inpPassLast
				.replace("{#}", Integer.toString(passengerNumber-1));
		String passMealLocator = inpPassMeal
				.replace("{#}", Integer.toString(passengerNumber-1));
		
		Framework.setText(passFirstLocator, firstName);
		Framework.setText(passLastLocator, lastName);
		Framework.setSelectByIndex(passMealLocator, meal.ordinal());
	}
	
	public void addPassengerBasedCreditCard(int passengerNumber, CreditCard cardType,
			String cardNumber, Month month, int year) {
		String passFirstLocator = inpPassFirst
				.replace("{#}", Integer.toString(passengerNumber-1));
		String passLastLocator = inpPassLast
				.replace("{#}", Integer.toString(passengerNumber-1));
		String firstName = Framework.getText(passFirstLocator);
		String lastName = Framework.getText(passLastLocator);
		
		addCard(cardType, cardNumber, month, year);
		addCardName(firstName, "", lastName);
	}
	
	public void addCard(CreditCard cardType, String cardNumber, Month month, int year) {
		Framework.setSelectByIndex(selCreditCard, cardType.ordinal());
		Framework.setText(inpCardNumber, cardNumber);
		Framework.setSelectByIndex(selCardExpMonth, month.ordinal()+1);
		Framework.setSelectByValue(selCardExpYear, Integer.toString(year));
	}
	
	public void addCardName(String firstName, String middleName, String lastName) {
		Framework.setText(inpCardFirstName, firstName);
		Framework.setText(inpCardMiddleName, middleName);
		Framework.setText(inpCardLastName, lastName);
	}
	
	public void purchaseFlights() {
		Framework.click(btnPurchase);
	}
	
	public boolean doFlightPriceMatch(FlightWay flightWay, int flightNumber, String price) {
		String locator = "";
		if (flightWay.toString().equals("DEPARTURE")) {
			locator = lblDepartureFlightNumberBasedPrice;
		} else {
			locator = lblReturnFlightNumberBasedPrice;
		}
		locator = locator.replace("{F}", Integer.toString(flightNumber));
		return Framework.getText(locator).equals(price);
	}

	public boolean doTotalFlightPriceMatch(String departurePrice, String returnPrice, int passengers) {
		int taxPrice = Integer.parseInt(Framework.getText(lblFlightTaxes).replace("$", ""));
		int totalBookedPrice = Integer.parseInt(Framework.getText(lblFlightTotal).replace("$", ""));
		int subtotalSelectedPrice = (Integer.parseInt(departurePrice) + Integer.parseInt(returnPrice)) * passengers;
		int totalSelectedPrice = taxPrice + subtotalSelectedPrice;
		return totalBookedPrice == totalSelectedPrice;
	}

	public boolean isReturnFlightPresent() {
		return Framework.getElements(tblReturnFlight).size() == 2;
	}

	public boolean isPurchaseButtonEnabled() {
		return Framework.getElement(btnPurchase).isEnabled();
	}

	public void fillBookFlight(String passengersList, int passengerCardOwner, String cardType, String cardNumber,
			String expMonth, int expYear) {
		List<String> listOfPassengers = List.of(passengersList.split("-"));
		for(int i=1 ; i<=listOfPassengers.size() ; i++) {
			List<String> passengerDetails = List.of(listOfPassengers.get(i-1).split("_"));
			addPassenger(i, passengerDetails.get(0), passengerDetails.get(1), Meal.valueOf(passengerDetails.get(2)));
		}
		addPassengerBasedCreditCard(passengerCardOwner, CreditCard.valueOf(cardType), 
				cardNumber, Month.valueOf(expMonth), expYear);
		
	}

}
