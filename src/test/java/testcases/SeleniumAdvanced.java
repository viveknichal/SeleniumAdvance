package testcases;

import Utilities.DriverFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SeleniumAdvanced {

	WebDriver driver = null;
	public static String hubURL = "https://hub.lambdatest.com/wd/hub";

	@BeforeTest
	@Parameters({"browserName", "platformName", "browserVersion"})
	public void setupBrowser(String browserName, String platformName, String browserVersion) throws MalformedURLException {
		setUp(browserName, platformName, browserVersion);
		this.driver = driver();
	}

	@Test
	public void seleniumAdvanced_TC1() throws MalformedURLException, InterruptedException {
		List<String> windowHandles = new ArrayList<>();

		//Navigate to Url
		driver.get("https://www.lambdatest.com/");
		driver.manage().window().maximize();

		//Step  2
		new WebDriverWait(driver, Duration.ofSeconds(10)).until((ExpectedCondition<Boolean>) wd ->
				((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));

		WebElement integrationElement = driver.findElement(By.xpath("//a[text()='Explore all Integrations']"));

		//Step 3
		scrollIntoViewWithOffSet(integrationElement);

		//Step 4
		integrationElement.sendKeys(Keys.CONTROL, Keys.RETURN);


		// Get all the window handles
		//saving the window handles in array list
		String currentWindowHandle = driver.getWindowHandle();
		System.out.println("Current Window Handles: "+ currentWindowHandle);
		// Print all window handles
		for (String handle : driver.getWindowHandles()) {
			System.out.println("Printing Window Handles of the open window: "+ handle);
			windowHandles.add(handle);
			if(!handle.equals(currentWindowHandle)){
				driver.switchTo().window(handle);
				break;
			}
		}
		windowHandles.clear();


		//Step 6
		String url = "https://www.lambdatest.com/integrations";
		Assert.assertEquals(url,driver.getCurrentUrl());

		//Step 7
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		WebElement codeLessAut = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Codeless Automation']")));
		scrollIntoView(codeLessAut);
		codeLessAut.click();
		Thread.sleep(5000);

		//Step 8
		WebElement TestingWhiz1 = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h3[text()='Testing Whiz']")));
		scrollIntoView(TestingWhiz1);
		WebElement testingWhiz = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[text()='Integrate Testing Whiz with LambdaTest']")));

		testingWhiz.click();

		//Step 9
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h1[text()='TestingWhiz Integration With LambdaTest']")));
		String text = "Running Automation Tests Using TestingWhiz LambdaTest | LambdaTest";
		Assert.assertEquals(text,driver.getTitle());

		//Step 10
		driver.close();

		driver.switchTo().window(currentWindowHandle);
		//Step 11
        windowHandles.addAll(driver.getWindowHandles());
		System.out.println("Size of Current window count "+ windowHandles.size());

		//Step 12
		driver.get("https://www.lambdatest.com/blog");
		Thread.sleep(5000);

		//Step 13
		WebElement communityElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//a[text()='Community'])[1]")));
		communityElement.click();
	}

	@AfterTest
	public void tearDown() {

		//Step 14
		driver.quit();
	}

	public void setUp(String browser, String platFormName, String browserVersion) throws MalformedURLException {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("browserName", browser);
		capabilities.setCapability("browserVersion", browserVersion);
		Map<String, Object> ltOptions = new HashMap<>();
		ltOptions.put("user", "viveknichal14");
		ltOptions.put("accessKey", "bASgKNLjJD104uE23tE8CoJgAkOHqQbAciqQwdZbhcARtDMptD");
		ltOptions.put("build", "LambdaTestCertification_SeleniumAdvanced_Vivek");
		ltOptions.put("name", this.getClass().getName());
		ltOptions.put("platformName", platFormName);
		ltOptions.put("seCdp", true);
		ltOptions.put("selenium_version", "4.0.0");
		capabilities.setCapability("LT:Options", ltOptions);

		DriverFactory.getInstances().setDriver(new RemoteWebDriver(new URL(hubURL), capabilities));
	}

	public WebDriver driver() {
		return DriverFactory.getInstances().getDriver();
	}

	private void scrollIntoView(WebElement element){
		// Scroll the element into view
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
	}

	private void scrollIntoViewWithOffSet(WebElement element){
		int yOffset = -100; // Adjust this value based on your requirements
		String script = "arguments[0].scrollIntoView(true); window.scrollBy(0, arguments[1]);";
		((JavascriptExecutor) driver).executeScript(script, element, yOffset);
	}
}
