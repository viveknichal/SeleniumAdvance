package Utilities;

import org.openqa.selenium.WebDriver;

public class DriverFactory {
	
	private DriverFactory() {
		
	}
	
	private static final DriverFactory instance = new DriverFactory();
	
	public static DriverFactory getInstances() {
		return instance;
	}
	
	ThreadLocal<WebDriver> driver = new ThreadLocal<>();
	
	public WebDriver getDriver() {
		return driver.get();
	}
	
	public void setDriver(WebDriver params) {
		 driver.set(params);
	}

}
