package pageobjects;

import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AddToCartPage {

	WebDriver driver;
	WebDriverWait wait;
	int seconds=25;
	 
	public AddToCartPage(WebDriver driver)
	{
		this.driver=driver;
		wait=new WebDriverWait(driver,seconds);
	}
	
	@FindBy(xpath="//input[@id='add-to-cart-button']")
	WebElement addtocart;
	@FindBy(xpath="//span[@id='nav-cart-count']")
	WebElement Count;
	
	public void addtocart() throws InterruptedException
	{
		Thread.sleep(2000);
		Set<String> windows=driver.getWindowHandles();
		Thread.sleep(1000);
		driver.switchTo().window(windows.toArray()[windows.size()-1].toString()); 
		wait.until(ExpectedConditions.elementToBeClickable(addtocart)).click();
	}
	
	public int checkinitialcount()
	{
		String count=Count.getText();
		int actualcount=Integer.parseInt(count);
		return actualcount;
	}
	
	public void delete()
	{
		
	}
	
}
