package pageobjects;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;


public class Common {
	
	WebDriver driver;
	WebDriverWait wait;
	int Seconds=25;
	public Common(WebDriver driver) 
	{
		this.driver = driver;
		wait=new WebDriverWait(driver, Seconds);
	}

	
	public void takescreenshot(WebDriver driver, String filename, String name) throws InterruptedException {
			Thread.sleep(2000);
			//Convert web driver object to TakeScreenshot
	       TakesScreenshot scrShot =((TakesScreenshot)driver);
	       //Call getScreenshotAs method to create image file
	       File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
	       //Move image file to new destination
	       String logFileName = new SimpleDateFormat("yyyyMMdd_HHmmssSSS'.png'").format(new Date());
	       File DestFile=new File(System.getProperty("user.dir")+"//target//screenshotof"+filename+"_"+name+"_"+logFileName);
	       //Copy file at destination
	       try {
	            FileUtils.copyFile(SrcFile, DestFile);
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
		
	}
	

}
