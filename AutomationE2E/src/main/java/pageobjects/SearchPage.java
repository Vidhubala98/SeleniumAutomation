package pageobjects;


import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SearchPage {
	
	WebDriver driver;
	WebDriverWait wait;
	int seconds=25;
	String search="The Monk Who Sold His Ferrari";
	
	public SearchPage(WebDriver driver)
	{
		this.driver=driver;
		wait=new WebDriverWait(driver,seconds);
	}
	
	@FindBy(xpath="//select[@title='Search in']")
	WebElement Categories;
	@FindBy(xpath="//input[@aria-label='Search']")
	WebElement Search;
	@FindBy(xpath="//input[@value='Go']")
	WebElement Go;
	@FindBy(xpath="(//span[text()='Get It in 2 Days']/preceding::div//label//i)[last()]")
	WebElement Delivery;
	@FindBy(xpath="//span[text()='Literature & Fiction']")
	WebElement Department;
	@FindBy(xpath="//i[contains(@class,'a-star-medium-4')]")
	WebElement Rating;
	@FindBy(xpath="//a[@class='a-link-normal a-text-normal']")
	List<WebElement> Sort;
	

	public void selectCategories()
	{
		Select select=new Select(Categories);
		select.selectByVisibleText("Books");		
	}
	
	public void Search() throws InterruptedException
	{
		wait.until(ExpectedConditions.elementToBeClickable(Search)).click();
		Search.sendKeys(search);
		Go.click();
		Thread.sleep(3000);
	}
	
	public void SearchFilter() throws InterruptedException
	{
		wait.until(ExpectedConditions.elementToBeClickable(Delivery)).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.elementToBeClickable(Department)).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.elementToBeClickable(Rating)).click();
		Thread.sleep(1000);
		
	}
	
	public void Selectbook() throws InterruptedException
	{
		for(int i=1;i<=Sort.size();i++)
		{
			WebElement text=driver.findElement(By.xpath("(//a[@class='a-link-normal a-text-normal']//span)["+i+"]"));
			String bookname=text.getText();
			if(bookname.equals(search))
			{
				WebElement Book=driver.findElement(By.xpath("(//a[@class='a-link-normal a-text-normal'])["+i+"]"));
				Book.click();
			}
		}
	}

}
