package test.pageobject_example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


/**
 * Testing Vimeo.com Sign up pages.  This class does not use Page Object pattern.  
 * Compare it with “VimeoSignupTest_withPageObject.java” class, which uses Page Object pattern.  
 * 
 * @author Chon Chung
 *
 */
public class VimeoSignupTest {

	private static WebDriver driver;
	private static final String INPUT_TYPE_SUBMIT = "input[type=submit][value=Join]";	


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
		getTestingData(); 
		//delete all previous cookies set by the page 
        	driver.manage().deleteAllCookies();  
        	//set implicitlyWait
		driver.manage().timeouts().implicitlyWait(9, TimeUnit.SECONDS); 
	}
	
	@Test
	public void testSignupSuccess(){
		//go to join page
		driver.get("http://vimeo.com/join"); 
		
		//enter data
		driver.findElement(By.id("name")).sendKeys(first_and_LastName);
		driver.findElement(By.id("email")).sendKeys(email);
		driver.findElement(By.id("password")).sendKeys(password);
		driver.findElement(By.id("tos")).click(); //"I accept" check box 

		//submit form
		driver.findElement(By.cssSelector(INPUT_TYPE_SUBMIT)).click();
		

		//Verify signup success: signup page title should be "You just joined Vimeo. Nice work!"
		String pageTitle = driver.getTitle(); 
		assertTrue("Verify: Sign up successful" , pageTitle.equalsIgnoreCase("You just joined Vimeo. Nice work!")); 
		
		//Clicking "Me" button, and go to the profile page
		driver.findElement(By.xpath("//*[@id='menu']/li[1]/a")).click(); 
		//Get the profile name: <span>name</span> 
		String userName = driver.findElement(By.xpath("//*[@id='profile']/div[2]/h1/span")).getText(); 
		
		//Verify data: the Profile user Name should be equal
		assertEquals("Verify: the Profile user Name should be equal", first_and_LastName, userName); 
		
		
		//logout for the next test. 
		driver.get("https://vimeo.com/log_out"); 
	}
	

	

	/**
	 * Test the signup page with invalid-format-email address.
	 * Expected result: "Please enter a valid email address" displayed 
	 */
	@Test
	public void testSignupInvalidEmailErrors(){
		//go to join page
		driver.get("http://vimeo.com/join"); 
		
		//invalid-format-email address
		String inValidEmail = "chon.email.com"; 
		
		//enter data
		driver.findElement(By.id("name")).sendKeys(first_and_LastName);
		driver.findElement(By.id("email")).sendKeys(inValidEmail);//invalid-email address
		driver.findElement(By.id("password")).sendKeys(password);
		driver.findElement(By.id("tos")).click(); //"I accept" check box 

		//submit form
		driver.findElement(By.cssSelector(INPUT_TYPE_SUBMIT))
				.click();
		
		
		//verify the Valid Email error message is displayed
		assertTrue(driver.findElement(By.id("advice-validate-email-email")).isDisplayed());
		assertTrue(driver.findElement(By.id("advice-validate-email-email"))
				                      .getText().equalsIgnoreCase("Please enter a valid email address")); 

		//logout for the next test. 
		driver.get("https://vimeo.com/log_out"); 
	}
	
	/** 
	 *  Test the signup page with missing password. 
	 *  Expected result: "Please enter your password" displayed 
	 */
	@Test
	public void testSignupNo_password(){
		//go to join page
		driver.get("http://vimeo.com/join"); 
		//enter data
		driver.findElement(By.id("name")).sendKeys(first_and_LastName);
		driver.findElement(By.id("email")).sendKeys(email);//invalid-email address
		
		//No password input
		
		driver.findElement(By.id("tos")).click(); //"I accept" check box 
		//submit form
		driver.findElement(By.cssSelector(INPUT_TYPE_SUBMIT))
				.click();
		//Verify the require password message displayed
		assertTrue(driver.findElement(By.id("advice-required-password")).isDisplayed());
		assertTrue(driver.findElement(By.id("advice-required-password"))
				   .getText().equalsIgnoreCase("Please enter your password"));
				
		//logout for the next test. 
		driver.get("https://vimeo.com/log_out"); 
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
