package test.pageobject_example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


import test.pageobject_example.pages.VimeoLogoutPage;
import test.pageobject_example.pages.VimeoProfilePage;
import test.pageobject_example.pages.VimeoSignupPage;
import test.pageobject_example.pages.VimeoJoinPage;
import wtbox.util.WaitTool;



/**
 * Testing Vimeo.com Sign up pages with Page Object Pattern.  
 * This class shows how to use page objects for testing.  
 * Compare with "VimeoSignupTest.java" class, and see how to use page objects. 
 * 
 * By using page objects, we do not have to remember all the page logic 
 * (each input or element id, name, or css-selector) to write test.  
 * And Page to Page navigation looks more clear. 
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
	public static void initializeClass(){
	
		driver = new FirefoxDriver();

		
	}
	
	/**
	 * Set up test properties before each test. 
	 **/
	@Before
	public void setupTest() {
		//Get the testing data
		getTestingData(); 
		//delete all previous cookies set by the page sign up process
        	driver.manage().deleteAllCookies();  
		//Set implicitlyWait
		WaitTool.setImplicitWait(driver, 30); 
	}
	
	/**
	 * Test the signup page with valide inputs data.
	 * Expected result: Successfully signed up, 
	 *                  and profile page displayed with first_and_LastName
	 */
	@Test
	public void testSignupSuccess(){
		VimeoJoinPage joinPage = new VimeoJoinPage(driver); 
		//go to page
		joinPage.open(); 
		
		//enter data
		joinPage.enterFirst_and_LastName(first_and_LastName); 
		joinPage.enterEmail(email); 
		joinPage.enterPassword(password); 
		joinPage.clickAcceptTOS(); 

		//submit form
		VimeoSignupPage signupPage = joinPage.submitForm(); 
		
		//Verify signup success: signup page title should be equal
		assertTrue("Sign up successful" , signupPage.isPageLoad()); 
		
		//Go to the profile page and get the profile name
		VimeoProfilePage profilePage = signupPage.clickProfileLink(); 
		String userName = profilePage.getProfileName();  
		
		//Verify data: the Profile user Name should be equal 
		assertEquals("The user name should be equal.", first_and_LastName, userName); 
				
		//logout for the next test. 
		VimeoLogoutPage.logOut(driver); 
	}
	
	

	
	/**
	 * Test the signup page with invalid-format-email address.
	 * Expected result: "Please enter a valid email address" displayed 
	 */
	@Test
	public void testSignupInvalidEmailErrors(){
		//invalid-format-email address
		String inValidEmail = "chon.email.com"; 

		VimeoJoinPage joinPage = new VimeoJoinPage(driver); 
		//go to page
		joinPage.open(); 

		//enter data
		joinPage.enterFirst_and_LastName(first_and_LastName); 
		joinPage.enterEmail(inValidEmail); 
		joinPage.enterPassword(password); 
		joinPage.clickAcceptTOS(); 

		//submit form
		joinPage.submitForm(); 
				
		//Verify: the Valid Email error message displayed
		assertTrue("Verify: the Valid Email error message displayed.", 
				   joinPage.verifyErrorMessageRequired_ValidEmail_displayed());


		//logout for the next test. 
		VimeoLogoutPage.logOut(driver); 
	}
	
	/** 
	 *  Test the signup page with missing password. 
	 *  Expected result: "Please enter your password" displayed 
	 */
	@Test
	public void testSignupNo_password(){
		VimeoJoinPage joinPage = new VimeoJoinPage(driver); 
		//go to page
		joinPage.open(); 

		//enter data
		joinPage.enterFirst_and_LastName(first_and_LastName); 
		joinPage.enterEmail(email); 
		
		//No password input
		
		joinPage.clickAcceptTOS(); 

		//submit form
		joinPage.submitForm(); 

		//Verify the require password message displayed
		assertTrue("Verify the require password message displayed.", 
				   joinPage.verifyErrorMessageRequired_Password_displayed());

				
		//logout for the next test. 
		VimeoLogoutPage.logOut(driver); 
	}
	
	
	
	@AfterClass
	public static void tearDown(){
		driver.quit();   
	}
	
	/** Get the testing data. 
	 *  You could make this method to access external testing data 
	 *  from Database or Excel files. 
	 *  But for this simple test, I am generating dummy data. */
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
/**
 * Further reading: 
 * 1. Selenium webdriver page object: 
 * 		http://stackoverflow.com/questions/10315894/selenium-webdriver-page-object
 * 2. Using Page Objects with Selenium and Web Driver 2.0
 * 		http://www.summa-tech.com/blog/2011/10/10/using-page-objects-with-selenium-and-web-driver-20/
 * 3. PageFactory
 * 		http://code.google.com/p/selenium/wiki/PageFactory
 * 4. Ben Burton's WebDriver Best Practices 
 * 		Video -- http://vimeo.com/44133409
 *      http://benburton.github.com/presentations/webdriver-best-practices/ 
 * 		
 */
