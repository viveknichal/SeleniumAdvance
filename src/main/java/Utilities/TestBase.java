package Utilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class TestBase {
	public static String hubURL = "https://hub.lambdatest.com/wd/hub";
    
	public synchronized void setUp(String browser) throws MalformedURLException {
		DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserName", browser);
        capabilities.setCapability("browserVersion", "112");
        HashMap<String, Object> ltOptions = new HashMap<>();
        ltOptions.put("user", "shashank_chouhan");
        ltOptions.put("accessKey", "zLM4lUVQ6IsxIf9wBTbgV6U2TQ9OM0yAIeaUMrH3AQnv9F0Nck");
        ltOptions.put("build", "LambdaTestCertification_SeleniumJava_Shashank");
        ltOptions.put("name", this.getClass().getName());
        ltOptions.put("platformName", "Windows 10");
        ltOptions.put("seCdp", true);
        ltOptions.put("selenium_version", "4.0.0");
        capabilities.setCapability("LT:Options", ltOptions);

        DriverFactory.getInstances().setDriver(new RemoteWebDriver(new URL(hubURL), capabilities));
	}
	
	public WebDriver driver() {
		return DriverFactory.getInstances().getDriver();
	}
}
