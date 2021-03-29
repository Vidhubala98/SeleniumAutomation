package pageobjects;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class HomePage {

	WebDriver driver;
	WebDriverWait wait;
	int seconds=30;

	public HomePage(WebDriver driver)
	{
		this.driver=driver;
		wait=new WebDriverWait(driver,seconds);

	}

	@FindBy(xpath="//a[@aria-label='Amazon']")
	WebElement logo;
	@FindBy(xpath="//a[@aria-label='Amazon']")
	WebElement logoEdited;
	@FindBy(xpath="//a[text()='Amazon Pay']")
	WebElement Amazonpay;
	@FindBy(xpath="//a[@id='nav-your-amazon']")
	WebElement YourAmazon;
	@FindBy(xpath="//a[text()='Best Sellers']")
	WebElement BestSellers;
	@FindBy(xpath="(//a[contains(text(),'Deals')])[1]")
	WebElement TodayDeals;
	@FindBy(xpath="//a[text()='Pantry']")
	WebElement Pantry;
	@FindBy(xpath="//a[text()='Buy Again']")
	WebElement BuyAgain;
	@FindBy(xpath="//a[text()='New Releases']")
	WebElement NewReleases;
	@FindBy(xpath="//a[text()='Electronics']")
	WebElement Electronics;
	@FindBy(xpath="//a[text()='Books']")
	WebElement Books;
	@FindBy(xpath="//span[text()='Prime']/ancestor::a")
	WebElement Prime;
	@FindBy(xpath="//a[text()='Mobiles']")
	WebElement Mobiles;
	@FindBy(xpath="//div[@class='navFooterBackToTop']//span")
	WebElement BacktoTop;
	@FindBy(xpath="//a[@aria-label='Choose a language for shopping.']")
	WebElement Language;
	@FindBy(xpath="//input[@value='en_IN']")
	WebElement English;
	@FindBy(xpath="(//input[@type='submit'])[last()]")
	WebElement SaveChanges;
	@FindBy(xpath="//input[@value='hi_IN']")
	WebElement Hindi;
	@FindBy(xpath="//a[@aria-label='Open Menu']//i")
	WebElement AllMenu;
	@FindBy(xpath="//a[text()='Sign Out']")
	WebElement SignOut;



	public boolean LogoVerification()
	{
		if(logo.isDisplayed())
			return true;
		else 
			return false;
	}
	public String Amazonpayverifiaction() throws InterruptedException
	{
		wait.until(ExpectedConditions.elementToBeClickable(Amazonpay)).click();
		Thread.sleep(2000);
		String title=driver.getTitle();
		return title;
	}

	public String YourAmazonverifiaction() throws InterruptedException
	{
		wait.until(ExpectedConditions.elementToBeClickable(YourAmazon)).click();
		Thread.sleep(2000);
		String title=driver.getTitle();
		return title;		
	}

	public String BestSellersverifiaction() throws InterruptedException
	{
		wait.until(ExpectedConditions.elementToBeClickable(BestSellers)).click();
		Thread.sleep(2000);
		String title=driver.getTitle();
		return title;		
	}

	public String TodayDealsverifiaction() throws InterruptedException
	{
		wait.until(ExpectedConditions.elementToBeClickable(TodayDeals)).click();
		Thread.sleep(2000);
		String title=driver.getTitle();
		return title;		
	}
	public String Pantryverifiaction() throws InterruptedException
	{
		wait.until(ExpectedConditions.elementToBeClickable(Pantry)).click();
		Thread.sleep(2000);
		String title=driver.getTitle();
		return title;		
	}
	public String BuyAgainverifiaction() throws InterruptedException
	{
		wait.until(ExpectedConditions.elementToBeClickable(BuyAgain)).click();
		Thread.sleep(2000);
		String title=driver.getTitle();
		return title;		
	}
	public String NewReleasesverifiaction() throws InterruptedException
	{
		wait.until(ExpectedConditions.elementToBeClickable(NewReleases)).click();
		Thread.sleep(2000);
		String title=driver.getTitle();
		return title;		
	}
	public String Electronicsverifiaction() throws InterruptedException
	{
		wait.until(ExpectedConditions.elementToBeClickable(Electronics)).click();
		Thread.sleep(2000);
		String title=driver.getTitle();
		return title;		
	}
	public String Booksverifiaction() throws InterruptedException
	{
		wait.until(ExpectedConditions.elementToBeClickable(Books)).click();
		Thread.sleep(2000);
		String title=driver.getTitle();
		return title;		
	}
	public String Primeverifiaction() throws InterruptedException
	{
		wait.until(ExpectedConditions.elementToBeClickable(Prime)).click();
		Thread.sleep(2000);
		String title=driver.getTitle();
		return title;		
	}
	public String Mobileverifiaction() throws InterruptedException
	{
		wait.until(ExpectedConditions.elementToBeClickable(Mobiles)).click();
		Thread.sleep(2000);
		String title=driver.getTitle();
		return title;		
	}

	/*public void BackToTop() throws InterruptedException
	{
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", BacktoTop);
		Thread.sleep(2000); 
		handleElementNotCickable(BacktoTop);

	}*/

	public boolean language() throws InterruptedException
	{	
		wait.until(ExpectedConditions.elementToBeClickable(Language)).click();
		Thread.sleep(2000);
		wait.until(ExpectedConditions.titleContains("Change Language Settings"));
		if(English.isSelected())
			return true;
		else
			return false;
	}

	public void chooselanguage()
	{
		handleElementNotCickable(English);
		SaveChanges.click();
	}
	
	public void Signout() throws InterruptedException
	{
		wait.until(ExpectedConditions.elementToBeClickable(AllMenu)).click();
		Actions act = new Actions(driver);
		Thread.sleep(2000);
		((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", SignOut);
		Thread.sleep(2000);
		act.click(SignOut).build().perform();
		Thread.sleep(2000);
	}

	public void handleElementNotCickable(WebElement element)  {
		try {
			Thread.sleep(2000);
			wait.until(ExpectedConditions.elementToBeClickable(element)).click();
			Thread.sleep(2000);
		} catch (Exception e) {//To handle element is not clickable at point(x,y)
			new Actions(driver).moveToElement(element).click().perform(); 

		}
		try {Thread.sleep(2000);}
		catch(InterruptedException e){e.printStackTrace();}
	}
}
