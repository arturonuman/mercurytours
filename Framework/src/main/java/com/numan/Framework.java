package com.numan;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class Framework {
	
	static WebDriver driver = new ChromeDriver();
	
	public static void close() {
		driver.close();
	}
	
	public static void quit() {
		driver.quit();
	}
	
	public static void goTo(String url) {
		driver.get(url);
	}
	
	public static boolean isAt(String url) {
		// return driver.getCurrentUrl().equals(url);
		// Changed to contains as during execution the current url is getting osCID value appended:
		// http://newtours.demoaut.com/mercurysignon.php?osCsid=e28b46cb3ae87678c32200ca652a8f7d
		return driver.getCurrentUrl().contains(url);
	}
	
	public static void deleteAllCookies() {
		driver.manage().deleteAllCookies();
	}
	
	public static WebElement getElement(String locator) {
		String method = null;
		String selector = null;
		try {
			method = locator.split("=", 2)[0];
			selector = locator.split("=", 2)[1];
			switch (method.toLowerCase()) {
			case "id":
				return driver.findElement(By.id(selector));
			case "name":
				return driver.findElement(By.name(selector));
			case "linktext":
				return driver.findElement(By.linkText(selector));
			case "partiallinktext":
				return driver.findElement(By.partialLinkText(selector));
			case "class":
				return driver.findElement(By.className(selector));
			case "css":
				return driver.findElement(By.cssSelector(selector));
			case "xpath":
				return driver.findElement(By.xpath(selector));
			default:
				throw new InvalidArgumentException("Invalid locator method: '" + method + "'");
			}
		} catch (NoSuchElementException e) {
			throw new NoSuchElementException("Unable to locate element with method: '" + method + "' and selector: '" + selector + "'");
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new InvalidArgumentException("Invalid locator definition: '" + locator + "'");
		}
	}
	
	public static List<WebElement> getElements(String locator) {
		String method = null;
		String selector = null;
		try {
			method = locator.split("=", 2)[0];
			selector = locator.split("=", 2)[1];
			switch (method.toLowerCase()) {
			case "id":
				return driver.findElements(By.id(selector));
			case "name":
				return driver.findElements(By.name(selector));
			case "linktext":
				return driver.findElements(By.linkText(selector));
			case "partiallinktext":
				return driver.findElements(By.partialLinkText(selector));
			case "class":
				return driver.findElements(By.className(selector));
			case "css":
				return driver.findElements(By.cssSelector(selector));
			case "xpath":
				return driver.findElements(By.xpath(selector));
			default:
				throw new InvalidArgumentException("Invalid locator method: '" + method + "'");
			}	
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new InvalidArgumentException("Invalid locator definition: '" + locator + "'");
		}
	}

	public static boolean isEnabled(String locator) {
		return getElement(locator).isEnabled();
	}
	
	public static boolean isPresent(String locator) {
		try {
			getElement(locator);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
		
	}

	public static boolean isTitle(String title) {
		return driver.getTitle().equals(title);
	}
	
	public static void click(String locator) {
		getElement(locator).click();
	}
	
	public static void setText(String locator, String text) {
		getElement(locator).sendKeys(text);
	}
	
	public static String getText(String locator) {
		String text = getElement(locator).getText();
		if (text.equals("")) {
			text = getElementsAttribute(locator, "value");
		}
		return text;
	}
	
	public static String getElementsAttribute(String locator, String attribute) {
		return getElement(locator).getAttribute(attribute);
	}

	public static void clickCheckBoxByIndex(String locator, int index) {
		getElements(locator).get(index).click();
	}

	public static void setSelectByIndex(String locator, int index) {
		Select selElement = new Select(getElement(locator));
		selElement.selectByIndex(index);
	}

	public static void setSelectByValue(String locator, String value) {
		Select selElement = new Select(getElement(locator));
		selElement.selectByValue(value);
	}

	public static void setSelectByText(String locator, String text) {
		Select selElement = new Select(getElement(locator));
		selElement.selectByVisibleText(text);
	}
	
}
