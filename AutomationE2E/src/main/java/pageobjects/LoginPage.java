package pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

	WebDriver driver;
	WebDriverWait wait;
	int seconds=25;
	public LoginPage(WebDriver driver)
	{
		this.driver=driver;
		wait=new WebDriverWait(driver,seconds);
	}

	@FindBy(xpath="(//a[@data-nav-role='signin'])[1]")
	WebElement Mousehover;
	@FindBy(xpath="//input[@type='email']")
	WebElement Username;
	@FindBy(xpath="//input[@id='continue']")
	WebElement Continue;
	@FindBy(xpath="//input[@type='password']")
	WebElement Password;
	@FindBy(xpath="//input[@id='signInSubmit']")
	WebElement Signin;
	@FindBy(xpath="//span[contains(text(),'Hello, ')]")
	WebElement checkuser;

	public void login(String username,String password) throws InterruptedException
	{
		Actions action = new Actions(driver);
		action.moveToElement(Mousehover).build().perform();
		wait.until(ExpectedConditions.elementToBeClickable(Mousehover)).click();
		Thread.sleep(3000);
		if(driver.getTitle().equals("Amazon Sign In"))
		{
			Username.click();
			Username.sendKeys(username);
			Continue.click();
			Password.click();
			Password.sendKeys(password);
			Signin.click();
		}

	}
	 public boolean CheckUser()
	 {
		String username=checkuser.getText();
		if(username.contains("QA"))
			return true;
		else 
			return false;
		
		
	 }

}
