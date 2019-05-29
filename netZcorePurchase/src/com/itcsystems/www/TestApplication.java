package com.itcsystems.www;

import java.io.File;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class TestApplication {
	
	WebDriver driver;
	ExtentReports report;
	ExtentTest logger;
	
	@Parameters({"location", "webDriver", "url"})
	@BeforeTest(groups = {"Initialization"})
	public void initializeChrome(String location, String webDriver, String url) {
		
		report = new ExtentReports(location);
		report.loadConfig(new File(System.getProperty("user.dir")+"\\extent-config.xml"));	
		logger = report.startTest("URL/ WEB ADDRESS TEST");
		
		//set chrome driver and open page
		System.setProperty("webdriver.chrome.driver", webDriver);
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		logger.log(LogStatus.PASS, "Chrome browser Started.");		
		driver.get(url);
		logger.log(LogStatus.PASS, "Web address inserted.");		
		logger.log(LogStatus.INFO, "Localhost server is running.");		
		report.flush();
	}
	
	//Login test
	@Parameters({"cardNumber", "password"})
	@Test(priority=2, groups = {"Initialization"})
	public void validateLoginPage(String cardNumber, String password) {
		logger = report.startTest("LOGIN");
				
		//Entering values in the textbox
		WebElement c = driver.findElement(By.xpath("//a[@id='ctl00_lgnView_cpLogin_lnkStudentWeb']"));
		c.click();
		WebElement a = driver.findElement(By.xpath("//input[@id='ctl00_lgnView_cpLogin_lgnLogin_UserName']"));
		a.sendKeys(cardNumber);	
		WebElement element = driver.findElement(By.xpath("//input[@id='ctl00_lgnView_cpLogin_lgnLogin_Password']"));
		element.sendKeys(password);		
		logger.log(LogStatus.PASS, "User name and password completed!");
		
		WebElement b = driver.findElement(By.xpath("//input[@id='ctl00_lgnView_cpLogin_lgnLogin_btnLogin']"));
		b.click();		
		logger.log(LogStatus.PASS, "netZcore Purchase login successful.");
		report.flush();
	}
	
	//Check page title
	@Test(priority=3, groups = {"UX"})
	public void checkPageTitle() {
			logger = report.startTest("Title check");
			logger.log(LogStatus.INFO, "Check Page title");
			String title = driver.getTitle();
			//System.out.println("Page title:"+title);
			Assert.assertTrue(title.contains("TITC Systems - netZcore Purchase"));
			logger.log(LogStatus.PASS, "Title check completed.");
			report.flush();
		}
	
	//check if alert is present
	public boolean isAlertPresent() 
	{ 
	    try 
	    { 
	        driver.switchTo().alert(); 
	        return true; 
	    }    
	    catch (NoAlertPresentException Ex) 
	    { 
	        return false; 
	    }   
	} 
	
	//Check Account summary
	@Test(priority=4, groups = {"Transaction"})
	public void checkAccountSummary() {
		logger = report.startTest("ACCOUNT SUMMARY");			
		WebElement c = driver.findElement(By.xpath("//a[@title='Account summary']"));
		c.click();		
		logger.log(LogStatus.PASS, "Account summary");
		report.flush();
	}
	
	//Check Transaction History
	@Test(priority = 5, groups = {"Transaction"})
	public void checkTransactionHistory() {
		logger = report.startTest("TRANSACTION HISTORY");		
		WebElement c = driver.findElement(By.xpath("//a[@title='Transaction history']"));
		c.click();		
		logger.log(LogStatus.PASS, "Transaction History");
		report.flush();
	}
	
	//Add money functionality test
	@Test(priority=16, groups = {"T"})
	public void addMoney() throws InterruptedException {
		
		Thread.sleep(7000);
		
		logger = report.startTest("ADD MONEY");		
		
		WebElement d = driver.findElement(By.xpath("//a[@title='Add Money']"));
		d.click();
		//driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		WebElement e = driver.findElement(By.xpath("//input[@id='ctl00_lgnView_cpMain_txtAmount']"));
		e.sendKeys("1");	
		logger.log(LogStatus.PASS, "Amount added.");
				
		WebElement f = driver.findElement(By.xpath("//div[@id='ctl00_lgnView_cpMain_pnlPaymentMethod']//span[2]"));
		f.click();
		
		logger.log(LogStatus.PASS, "Payment method selected.");
		WebElement g = driver.findElement(By.xpath("//input[@id='ctl00_lgnView_cpMain_btnNext']"));
		g.click();
		
		logger.log(LogStatus.PASS, "Next button clicked.");
		WebElement h = driver.findElement(By.xpath("//input[@id=\'ctl00_lgnView_cpMain_ctl01_btnNext\']"));
		h.click();
		
		logger.log(LogStatus.PASS, "Next button clicked.");
		WebElement i = driver.findElement(By.xpath("//input[@id=\"cc_radio\"]"));
		i.click();
		
		logger.log(LogStatus.INFO, "Credit card holder name entered.");
		WebElement j = driver.findElement(By.xpath("//input[@id=\'cardholder\']"));
		j.sendKeys("Nikhil");
		
		logger.log(LogStatus.INFO, "Credit card number entered.");
		WebElement k = driver.findElement(By.xpath("//input[@id=\'pan\']"));
		k.sendKeys("4030000010001234");
		
		logger.log(LogStatus.INFO, "Expiry date entered.");
		WebElement l = driver.findElement(By.xpath("//input[@id=\'exp_date\']"));
		l.sendKeys("0121");
		
		logger.log(LogStatus.PASS, "Process transaction.");
		WebElement m = driver.findElement(By.xpath("//input[@id=\'buttonProcessCC\']"));
		m.click();			
			
		//confirm to logout
		//logger.log(LogStatus.INFO, "Alert confirmation accepted.");
		//driver.switchTo().alert().accept();
		//driver.quit();
		
		logger.log(LogStatus.PASS, "Add money functionality completed.");
		report.flush();
	}
	
	//Purchase Mealplan test
	@Test(priority=15, groups = {"T"})
	public void purchaseMealplan() throws InterruptedException {
		
		logger = report.startTest("MEAL PLANS");			
		WebElement d = driver.findElement(By.xpath("//a[@title='Meal Plans']"));
		d.click();
		
		//driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		WebElement e = driver.findElement(By.xpath("//input[@id=\'ctl00_lgnView_cpMain_lvPlans_ctrl0_btnSelect\']"));
		e.click();			
		
		//check if mealplan is already present.
		if(isAlertPresent()) {
			
			
			//Alert alert = driver.switchTo().alert();
		
			//if mealplan is already present
			if(driver.switchTo().alert().getText().contains("Selected plan already purchased.")) {
			   logger.log(LogStatus.FAIL, "Selected plan already purchased.");	
			   //alert.accept();
			   driver.switchTo().alert().accept();
			} 
		
		} else {
			
			logger.log(LogStatus.PASS, "Next button clicked.");			
			WebElement h = driver.findElement(By.xpath("//input[@id=\'ctl00_lgnView_cpMain_ctl01_btnNext\']"));
			h.click();
			
			logger.log(LogStatus.PASS, "Next button clicked.");
			WebElement i = driver.findElement(By.xpath("//input[@id=\"cc_radio\"]"));
			i.click();
			
			logger.log(LogStatus.PASS, "Credit card holder name entered.");
			WebElement j = driver.findElement(By.xpath("//input[@id=\'cardholder\']"));
			j.sendKeys("Nikhil");
			
			logger.log(LogStatus.PASS, "Credit card number entered.");
			WebElement k = driver.findElement(By.xpath("//input[@id=\'pan\']"));
			k.sendKeys("4030000010001234");
			
			logger.log(LogStatus.PASS, "Expiry date entered.");
			WebElement l = driver.findElement(By.xpath("//input[@id=\'exp_date\']"));
			l.sendKeys("0121");
			
			logger.log(LogStatus.PASS, "Process transaction.");
			WebElement m = driver.findElement(By.xpath("//input[@id=\'buttonProcessCC\']"));
			m.click();	
			report.flush();
		}
	}	

	//Parking
	@Test(priority=20, groups = {"Th"})
	public void purchaseParkingPlan() {
		logger = report.startTest("PARKING");		
		WebElement c = driver.findElement(By.xpath("//a[@title='Parking']"));
		c.click();		
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//WebElement e = driver.findElement(By.id("ctl00_lgnView_cpMain_lblMessage"));
		WebElement e = driver.findElement(By.id("ctl00_lgnView_cpMain_lblError"));	
		
		if((e.getText().contains("There was an error retrieving Parking data."))) {
			
			WebElement m = driver.findElement(By.id("ctl00_lgnView_cpMain_lblError"));			
			logger.log(LogStatus.INFO, m.getText());
			report.flush();
			
		} else {
			
		}	
	}
	
	//Pay Fines
	@Test(priority=20, groups = {"Tp"})
	public void payFines() {
		logger = report.startTest("PAY FINES");		
		WebElement c = driver.findElement(By.xpath("//a[@title='Pay Fines']"));
		c.click();		
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//WebElement e = driver.findElement(By.id("ctl00_lgnView_cpMain_lblMessage"));
		WebElement e = driver.findElement(By.id("ctl00_lgnView_cpMain_chkLstFine"));	
		
		if((e.getText().contains("You have no pending fines."))) {
			
			WebElement m = driver.findElement(By.id("ctl00_lgnView_cpMain_chkLstFine"));			
			logger.log(LogStatus.INFO, m.getText());
			report.flush();
			
		} else {
			
		}	
	}	
	
	//Balance refund
	@Test(priority=8, groups = {"Transaction"})
	public void validateBalanceRefund() {
		
		logger = report.startTest("BALANCE REFUND");			
		WebElement c = driver.findElement(By.xpath("//a[@title='Balance Refund']"));
		c.click();
		
		//WebElement e = driver.findElement(By.id("ctl00_lgnView_cpMain_lblMessage"));
		WebElement e = driver.findElement(By.className("confirmMsg"));	
				
		if((e.getText().contains("No account eligible for a refund."))) {
			
			WebElement m = driver.findElement(By.id("ctl00_lgnView_cpMain_lblMessage"));			
			logger.log(LogStatus.INFO, m.getText());
			report.flush();
			
		} else {
			
			Select account = new Select(driver.findElement(By.xpath("//select[@id=\'ctl00_lgnView_cpMain_ddlAccount\']")));
			account.selectByValue("1");
			logger.log(LogStatus.PASS, "Account selected.");
			
			WebElement d = driver.findElement(By.xpath("//input[@id=\'ctl00_lgnView_cpMain_btnRefund\']"));
			d.click();
			logger.log(LogStatus.PASS, "Refund button clicked");	
				
			//Accept 
			driver.switchTo().alert().accept();	
			logger.log(LogStatus.PASS, "Confirm balance refund.");
			
			WebElement ms = driver.findElement(By.xpath("//span[@id=\"ctl00_lgnView_cpMain_lblMessage\"]"));			
			logger.log(LogStatus.INFO, ms.getText());
			report.flush();
		}
	}
	
	//Balance Transfer
	@Test(priority=9, groups = {"Transaction"})
	public void validateBalanceTransfer() {
		logger = report.startTest("BALANCE TRANSFER");
		
		WebElement d = driver.findElement(By.xpath("//a[@title='Balance Transfer']"));
		d.click();
		logger.log(LogStatus.PASS, "Balance Transfer link clicked.");
		report.flush();
	}
	
	//Pay Fines
	@Test(priority=20, groups = {"Tc"})
	public void checkTermsAndConditions() {
		logger = report.startTest("TERMS & CONDITIONS");		
		WebElement c = driver.findElement(By.xpath("//a[@title='Terms & Conditions']"));
		c.click();	
		
		logger.log(LogStatus.PASS, "Terms & Conditions checked.");
		report.flush();			
	}	
	//Take screenshot
	@AfterMethod(alwaysRun = true)
	public void getScreenshots(ITestResult result) {
		
		if(result.getStatus() == ITestResult.FAILURE) {
			String screenshot_path = Screenshot.captureScreenshot(driver, result.getName());
			String image = logger.addScreenCapture(screenshot_path);
			logger.log(LogStatus.FAIL, "Screenshot", image);
		}
				
	}			
}