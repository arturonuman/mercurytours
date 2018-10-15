package com.numan;

public class NavigateTo {

	static String lnkSignOn = "linktext=SIGN-ON";
	static String lnkSignOff = "linktext=SIGN-OFF";
	static String lnkHome = "linktext=Home";
	static String lnkFlights = "linktext=Flights";

	
	public void home() {
		Framework.click(lnkHome);
	}
	
	public void flights() {
		Framework.click(lnkFlights);
	}
	
	public void signOn() {
		Framework.click(lnkSignOn);
	}
	
	public void signOnff() {
		Framework.click(lnkSignOff);
	}
}
