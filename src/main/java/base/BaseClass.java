package base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import io.github.bonigarcia.wdm.WebDriverManager;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class BaseClass {
	public static ExtentReports reports;
	public static WebDriver driver;
	public static ExtentTest test;
	String dateName = new SimpleDateFormat("yyyy_MM_dd hh-mm-ss").format(new Date());

	@BeforeSuite
	public void configBS() {
		// db connect
		
	}

	@BeforeClass
	public void launchBrowser() throws Throwable {
		String browserName = FileUtility.getPropertyValue("browser");
		if (browserName.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		} else if (browserName.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		} else {
			System.out.println("browser name is invalid: " + browserName);
		}
		driver.get(FileUtility.getPropertyValue("url"));
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@BeforeMethod
	public void login() {
		driver.findElement(By.name("user_name")).sendKeys("admin");
		driver.findElement(By.name("user_password")).sendKeys("admin", Keys.ENTER);
	}

	@AfterMethod
	public void logOut() {
	
		WebElement ele = driver.findElement(By.xpath("//img[contains(@src,'/images/user.PNG')]"));
		Actions actions = new Actions(driver);
		actions.moveToElement(ele).build().perform();
		driver.findElement(By.linkText("Sign Out")).click();

	}

	@AfterClass
	public void quitBrowser() {
		driver.quit();
	}

	@AfterSuite
	public void configAS() {
		// db disconnect
		
	}

}
