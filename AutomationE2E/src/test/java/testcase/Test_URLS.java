package testcase;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import pageobjects.AddToCartPage;
import pageobjects.Common;
import pageobjects.HomePage;
import pageobjects.LoginPage;
import pageobjects.SearchPage;
import testbase.TestBase;

public class Test_URLS extends TestBase {

	int numOfUrls=Integer.parseInt(dprop.getProperty("NUMOFURLS"));

	@DataProvider(name = "Data1", parallel=true)
	public Object[][] createData1URLs() {
		Object obj1[][]= new Object[numOfUrls][5];
		int num=0;
		for(int i=1;i<=numOfUrls;i++)
		{

			String url="Url"+String.valueOf(i);
			String username="Username"+String.valueOf(i);
			String password="Password"+String.valueOf(i);
			String company="Company"+String.valueOf(i);
			String title="Title"+String.valueOf(i);			
			obj1[num][0]=dprop.getProperty(url);
			obj1[num][1]=dprop.getProperty(username);
			obj1[num][2]=dprop.getProperty(password);
			obj1[num][3]=dprop.getProperty(title);
			obj1[num][4]=dprop.getProperty(company);
			num++;

		}
		return obj1;
	} 

	@Test(dataProvider="Data1")
	public void TestURLs(String url, String username, String password, String title, String company) throws InterruptedException 
	{
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("--incognito");
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
		chromeOptions.addArguments("window-size=1400,800");
		WebDriver driver=new ChromeDriver(chromeOptions);

		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(180, TimeUnit.SECONDS);
		driver.get(url);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		extentTest = extent.createTest(company);
		LoginPage lp=PageFactory.initElements(driver, LoginPage.class);
		HomePage hp=PageFactory.initElements(driver, HomePage.class);
		Common c=PageFactory.initElements(driver, Common.class);
		SearchPage sp=PageFactory.initElements(driver, SearchPage.class);
		AddToCartPage ap=PageFactory.initElements(driver,AddToCartPage.class);
		SoftAssert softAssertion= new SoftAssert();

		System.err.println("Running " + this + " on thread [" + Thread.currentThread().getId() + "]");
		try
		{
			//Login
			lp.login(username,password);
			Thread.sleep(3000);

			//Dashboard Verification
			String loginStatus=driver.getTitle();
			Assert.assertEquals(loginStatus,title ,"Title Mismatch");
			Assert.assertTrue(lp.CheckUser(), "Username Mismatch");
			Assert.assertTrue(hp.LogoVerification(), "Logo Not Displayed");

			//HomePage Menu Verification

			//AmazonPay
			String Amazonpay=hp.Amazonpayverifiaction();
			c.takescreenshot(driver,filename, "_AmazonPay" );		
			softAssertion.assertEquals(Amazonpay,"Amazon Pay","Amazon Pay Menu Verifiaction Failed");
			driver.navigate().back();

			//YourAmazon
			String YourAmazon=hp.YourAmazonverifiaction();
			c.takescreenshot(driver,filename, "_YourAmazon");		
			softAssertion.assertEquals(YourAmazon,"Your Amazon.in","Your Amazon Menu Verifiaction Failed");

			//BestSellers
			String BestSellers=hp.BestSellersverifiaction();
			c.takescreenshot(driver,filename, "_BestSellers");		
			softAssertion.assertEquals(BestSellers,"Amazon.in Bestsellers: The most popular items on Amazon","BestSellers Menu Verifiaction Failed");

			//Today's Deals
			String TodayDeals=hp.TodayDealsverifiaction();
			c.takescreenshot(driver,filename, "_TodayDeals");		
			softAssertion.assertEquals(TodayDeals,"Amazon.in Today's Deals: Great Savings. Every Day.","TodayDeals Menu Verifiaction Failed");

			//Pantry
			String Pantry=hp.Pantryverifiaction();
			c.takescreenshot(driver,filename, "_Pantry");		
			softAssertion.assertEquals(Pantry,"Amazon Pantry: The Online Grocery Shopping Store- Shop Daily Grocery Items and Get delivered in Next Day- Amazon.in","Pantry Menu Verifiaction Failed");

			//Buy Again
			String BuyAgain=hp.BuyAgainverifiaction();
			c.takescreenshot(driver,filename, "_BuyAgain");		
			softAssertion.assertEquals(BuyAgain,"Buy Again","BuyAgain Menu Verifiaction Failed");

			//New Releases
			String NewReleases=hp.NewReleasesverifiaction();
			c.takescreenshot(driver,filename, "NewReleases");		
			softAssertion.assertEquals(NewReleases,"Amazon.in Hot New Releases: The bestselling new and future releases on Amazon","NewReleases Menu Verifiaction Failed");

			//Electronics
			String Electronics=hp.Electronicsverifiaction();
			c.takescreenshot(driver,filename, "Electronics");		
			softAssertion.assertEquals(Electronics,"Electronics Store: Buy Electronics products Online at Best Prices in India at Amazon.in","Electronics Menu Verifiaction Failed");

			//Books
			String Books=hp.Booksverifiaction();
			c.takescreenshot(driver,filename, "Books");		
			softAssertion.assertEquals(Books,"Book Store Online : Buy Books Online at Best Prices in India | Books Shopping @ Amazon.in","Books Menu Verifiaction Failed");

			//Prime
			String Prime=hp.Primeverifiaction();
			c.takescreenshot(driver,filename, "Prime");		
			softAssertion.assertEquals(Prime,"Amazon.in: Amazon Prime","Prime Menu Verifiaction Failed");

			//Mobiles
			String Mobiles=hp.Mobileverifiaction();
			c.takescreenshot(driver,filename, "Mobiles");		
			softAssertion.assertEquals(Mobiles,"Mobile Phones: Buy New Mobiles Online at Best Prices in India | Buy Cell Phones Online - Amazon.in","Mobiles Menu Verifiaction Failed");

            //Language
			boolean status=hp.language();
			if(!status)
				hp.chooselanguage();
			driver.navigate().back();
			
			//Search Categories Selection
			sp.selectCategories();
			
			//Search
			sp.Search();
			
			//Search Filtering
			sp.SearchFilter();
			
			//Book Selection
			sp.Selectbook();
			
			//Add to Cart
			int initialcount=ap.checkinitialcount();
			ap.addtocart();
			int finalcount=ap.checkinitialcount();
			softAssertion.assertEquals(initialcount+1,finalcount,"Add To Cart Failed");
			
			//SignOut			
			Set<String> windows=driver.getWindowHandles();
			driver.switchTo().window(windows.toArray()[0].toString());
			hp.Signout();	
			

			String logoutStatus=driver.getTitle();
			softAssertion.assertEquals(logoutStatus,"Amazon Sign In" ,"Signout Failed");
			

			driver.quit();
		}


		finally 
		{
			softAssertion.assertAll();
		}
	}






}
