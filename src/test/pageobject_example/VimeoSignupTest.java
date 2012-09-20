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
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import wtbox.util.WaitTool;


/**
 * Testing Vimeo.com Sign up pages.  
 * 
 * @author Chon Chung
 *
 */
public class VimeoSignupTest {

	private static WebDriver driver;
	private static final String INPUT_TYPE_SUBMIT = "input[type=submit][value=Join]";	
	private static final String URL = "http://vimeo.com/join";


	//testing input data: 
	private String first_and_LastName; 
	private String email; 
	private String password; 
	
	/** Initialized class properties before excuting this class. */ 
	@BeforeClass
	public static void initialize(){
		
		driver = new FirefoxDriver();

		driver.manage().timeouts().implicitlyWait(9, TimeUnit.SECONDS); //set implicitlyWait
		
	}
	
	/**
	 * Set up test properties before each test. 
	 **/
	@Before
	public void setup() {
		driver.get(URL); 
		getTestingData(); 
		//delete all previous cookies set by the page 
        driver.manage().deleteAllCookies();  
	}
	
	@Test
	public void testSignupSuccess(){
		
		//enter data
		driver.findElement(By.id("name")).sendKeys(first_and_LastName);
		driver.findElement(By.id("email")).sendKeys(email);
		driver.findElement(By.id("password")).sendKeys(password);
		driver.findElement(By.id("tos")).click(); //"I accept" check box 

		//submit form
		driver.findElement(By.cssSelector(INPUT_TYPE_SUBMIT)).click();
		
		//Clicking "Me" button, and go to the profile page
		driver.findElement(By.xpath("//*[@id='menu']/li[1]/a")).click(); 
		//Get the profile name: <span>name</span> 
		String userName = driver.findElement(By.xpath("//*[@id='profile']/div[2]/h1/span")).getText(); 
		
		//Success: validate the displayed Name
		assertEquals("The user name should be equal.", first_and_LastName, userName); 
		
		
		//logout for the next test. 
		driver.get("https://vimeo.com/log_out"); 
		
	}
	

	

	@Test
	public void testSignupInvalidEmailErrors(){
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
