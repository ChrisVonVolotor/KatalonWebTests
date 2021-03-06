package com.qa.quickstart.net.phptravels;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;

import com.qa.quickstart.net.phptravels.pages.*;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class tests {
	public ChromeDriver driver;
	static ExtentReports extent;

	@Before
	public void setup() {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Admin\\Documents\\Development\\chromedriver_win32\\chromedriver.exe");
		driver = new ChromeDriver();
		extent= new ExtentReports("C:\\Users\\Admin\\Documents\\WebAutomaticTesting\\phptravels\\net.phptravels\\Reports\\extent.html", false);
	}
	
	@Test
	public void pomTest() {
		ExtentTest test1 = extent.startTest("Book Seats");
		Actions action = new Actions(driver);

		String url = "https://www.phptravels.net/";
		driver.navigate().to(url);
		driver.manage().window().maximize();

		action.pause(2000).build().perform();

		homePage home = PageFactory.initElements(driver, homePage.class);
		test1.log(LogStatus.INFO, "Setting search paramaters for Hols");
		home.setCity(driver);
		home.setDates(driver);
		home.setAdults3(driver);
		home.submit();

		action.pause(2000).build().perform();
		test1.log(LogStatus.INFO, "Selecting hotel");

		resultPage result = PageFactory.initElements(driver, resultPage.class);
		result.submit();
		
		action.pause(2000).build().perform();

		hotelPage hotel = PageFactory.initElements(driver, hotelPage.class);
		test1.log(LogStatus.INFO, "Booking Room");
		hotel.submit(driver);		
		
		try {
			assertEquals("https://www.phptravels.net/hotels/book/Grand-Plaza-Apartments?adults=2&child=0&checkin=21%2F06%2F2018&checkout=27%2F06%2F2018&roomid=16&roomscount=1",driver.getCurrentUrl());
			test1.log(LogStatus.PASS, "Just Need To Pay");
		}catch(AssertionError e) {
			test1.log(LogStatus.FAIL, "oof, ouch, owie my code");
			fail();
		}finally {
			test1.log(LogStatus.INFO, "Current URL: " + driver.getCurrentUrl());
			extent.endTest(test1);
		}
		
		
		
	}

	
	@After
	public void tearDown() {
		driver.close();
		driver.quit();
		extent.flush();
	}
}
