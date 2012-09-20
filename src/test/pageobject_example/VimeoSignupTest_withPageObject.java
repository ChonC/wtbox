package test.pageobject_example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


import test.pageobject_example.pages.VimeoProfilePage;
import test.pageobject_example.pages.VimeoSignupPage;
import wtbox.pages.PageBase;



/**
 * Testing Vimeo.com Sign up pages with Page Objects.  
 * 
 * @author Chon Chung
 */
public class VimeoSignupTest_withPageObject {

	private static WebDriver driver;

	//testing input data: 
	private String first_and_LastName; 
	private String email; 
	private String password; 
	

	/** Initialized class properties before excuting this class. */ 
	@BeforeClass
	public static void initialize(){
    	//If your Firefox is not installed in the default location 
    	//for your particular operating system:
    	//you need to Set the firefox binary path property.
		//@todo remove this before commit to github
		System.setProperty("webdriver.firefox.bin", 
        "C:\\Programs\\Mozilla Firefox\\firefox.exe");
		
		
		driver = new FirefoxDriver();

		driver.manage().timeouts().implicitlyWait(9, TimeUnit.SECONDS); //set implicitlyWait
		
	}
	
	/**
	 * Set up test properties before each test. 
	 **/
	@Before
	public void setup() {
		driver.get(VimeoSignupPage.URL); 
		getTestingData(); 
		//delete all previous cookies set by the page 
        driver.manage().deleteAllCookies();  
	}
	
	@Test
	public void testSignupSuccess(){
		VimeoSignupPage signupPage = new VimeoSignupPage(driver); 

		//enter data
		signupPage.enterData(first_and_LastName, 
							 email, password);

		//submit form
		signupPage.submitForm(); 
		//Go to the profile page
		VimeoProfilePage profilePage = signupPage.gotoProfilePage(); 
		
		//Get the profile name
		String userName = profilePage.getProfileName();  
		
		//Success: validate the displayed Name
		assertEquals("The user name should be equal.", first_and_LastName, userName); 
				
		//logout for the next test. 
		profilePage.logOut(); 
	}
	
	

	

	@Test
	public void testSignupInvalidEmailErrors(){
		//invalid-format-email address
		String inValidEmail = "chon.email.com"; 

		VimeoSignupPage signupPage = new VimeoSignupPage(driver); 

		//enter data
		signupPage.enterData(first_and_LastName, 
				             inValidEmail, password);

		//submit form
		signupPage.submitForm(); 
				
		//verify the Valid Email error message
		assertTrue("Sign up page requires valid email input.", 
				   signupPage.getErrorMessageRequired_ValidEmail().contains("Please enter a valid email address"));


	}
	
	@AfterClass
	public static void tearDown(){
		driver.quit();   
	}
	
	/** Get the testing data. 
	 *  You could make this method to access external testing data 
	 *  from Database or Excel files. 
	 *  But for a simple testing, I am generating dummy data. */
	private void getTestingData(){		
		int randomID = getRandomNumber(1, 999);
		
		//generate dummy data for this testing
		first_and_LastName = "Chon" + randomID + " " + "Chung"; 
		email = "chonchung" + randomID + "@yahoo.com"; 
		password = "testpass" + randomID; 
	}
	
	/** Generate random ID for testing.*/
	private int getRandomNumber(int min, int max){
		return (min + (int)(Math.random() * ((max - min) + 1))); 
	}
}
