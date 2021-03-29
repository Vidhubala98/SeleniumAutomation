package testbase;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import org.testng.Reporter;
import org.testng.annotations.*;
import org.testng.ITestResult;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class TestBase {

	public static Properties dprop;
	public static ExtentReports extent;
	public static ExtentTest extentTest;
	public static ExtentHtmlReporter htmlReporter;
	public static String filename;


	public TestBase() 
	{		
		FileInputStream input = null;

		// Loading data.properties
		try 
		{
			dprop = new Properties();
			input = new FileInputStream(
					System.getProperty("user.dir") + "//src//main//java//loaddata//data.properties");
			dprop.load(input);

		} 
		catch (FileNotFoundException e) 
		{
			System.out.println("Data file not found");
		} 
		catch (IOException e) {
		} 

		filename=dprop.getProperty("FILENAME");

	}

	@BeforeTest
	public void setExtent() 
	{

		htmlReporter=new ExtentHtmlReporter(System.getProperty("user.dir") + "/"+filename+"_ExtentReport.html");
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);

		htmlReporter.config().setDocumentTitle(filename+" Test Execution");
		htmlReporter.config().setReportName(filename);
		htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
		htmlReporter.config().setTheme(Theme.STANDARD);
	}

	@BeforeMethod
	public void setup() 
	{
		System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"//chromedriver_win32//chromedriver.exe");
		Reporter.log("=============Browser Session Started========", true);
	}

	@AfterMethod
	public void closeApplication(ITestResult result) 
	{
		System.out.println("IN close method");

		if (result.getStatus() == ITestResult.FAILURE) 
		{
			extentTest.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " - Test Case Failed", ExtentColor.RED)); // to add name in extent report
			extentTest.log(Status.FAIL, MarkupHelper.createLabel(result.getThrowable() + " - Test Case Failed", ExtentColor.RED)); // to add error/exception in
		} 
		else if (result.getStatus() == ITestResult.SKIP) 
		{
			extentTest.log(Status.SKIP,  MarkupHelper.createLabel(result.getName() + " - Test Case Skipped", ExtentColor.ORANGE));
		} 
		else if (result.getStatus() == ITestResult.SUCCESS) 
		{
			extentTest.log(Status.PASS,  MarkupHelper.createLabel(result.getName() + " - Test Case Passed", ExtentColor.GREEN));
		}
		Reporter.log("=============Browser Session Ended========", true);

	}

	@AfterTest
	public void flushReport() {
		extent.flush();
	}

	@AfterSuite
	public void endReport()
	{
	}
}



