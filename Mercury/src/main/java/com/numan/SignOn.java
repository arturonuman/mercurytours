package com.numan;

public class SignOn {
	
	static String url = "http://newtours.demoaut.com/mercurysignon.php";
	static String title = "Sign-on: Mercury Tours";
	
	static String inpUsername = "name=userName";
	static String inpPassword = "name=password";
	static String btnLogin = "name=login";
	
	
	public void login(String username, String password) {
		Framework.setText(inpUsername, username);
		Framework.setText(inpPassword, password);
		Framework.click(btnLogin);
	}

}
