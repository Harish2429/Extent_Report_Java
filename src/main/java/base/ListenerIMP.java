package base;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ListenerIMP extends BaseClass implements ITestListener {

	@Override
	public void onTestStart(ITestResult result) {

	}

	@Override
	public void onTestSuccess(ITestResult result) {
		test.log(Status.PASS, result.getMethod().getMethodName() + "is pass");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		test.log(Status.FAIL, result.getMethod().getMethodName() + "is failed");
		test.log(Status.FAIL, result.getThrowable());

		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		String destination = System.getProperty("user.dir") + "/Screenshots/" + result.getMethod().getMethodName() + " "
				+ dateName + ".png";
		String destination1 = ("./Screenshots/" + result.getMethod().getMethodName() + " " + dateName + ".png");
		System.out.println(destination);
		System.out.println(destination1);
		File finalDestination = new File(destination);
		try {
			FileUtils.copyFile(source, finalDestination);
		} catch (IOException e) {
			e.printStackTrace();
		}
		test.addScreenCaptureFromPath(destination, result.getMethod().getMethodName());

	}

	@Override
	public void onTestSkipped(ITestResult result) {
		test.log(Status.SKIP, result.getMethod().getMethodName() + " is Skipped");
		test.log(Status.SKIP, result.getThrowable());
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

	}

	@Override
	public void onTestFailedWithTimeout(ITestResult result) {

	}

	@Override
	public void onStart(ITestContext context) {
		File file = new File("./reports/extentreport" + " " + dateName);
		ExtentSparkReporter sparkReporter = new ExtentSparkReporter(file);
		sparkReporter.config().setTheme(Theme.DARK);
		sparkReporter.config().setDocumentTitle("Vtiger Automation");
		sparkReporter.config().setReportName("Execution Report");
		reports = new ExtentReports();
		reports.attachReporter(sparkReporter);
		reports.setSystemInfo("OS", "Windows10");
		reports.setSystemInfo("url", "http://localhost:8888");
		reports.setSystemInfo("Reporter name", "Nithesh");
	}

	@Override
	public void onFinish(ITestContext context) {
		reports.flush();
	}

}
