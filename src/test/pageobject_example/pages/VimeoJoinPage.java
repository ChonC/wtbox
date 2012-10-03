package test.pageobject_example.pages;

import wtbox.pages.PageBase;
import wtbox.util.WaitTool;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Vimeo.com Sign up page Object.  It extends PageBase class. 
 * It provides properties and methods to test sign up page. 
 * 
 * @author Chon Chung
 *
 */
public class VimeoJoinPage extends PageBase{

	private static final String INPUT_TYPE_SUBMIT = "input[type=submit][value=Join]";	
	private final static String pageTitle = "Join Vimeo"; 
	

	/** constructor: initialized and load this page properties. */ 
	public VimeoJoinPage(WebDriver driver){
		super(driver, pageTitle); 
		
		// set the default URL
		URL = "http://vimeo.com/join";
	}
		
	/** Enter the sing up First_and_LastName. */
	public void enterFirst_and_LastName(String first_and_LastName){		
		driver.findElement(By.id("name")).sendKeys(first_and_LastName);
	}

	/** Enter the sing up Email. */
	public void enterEmail(String email){
		driver.findElement(By.id("email")).sendKeys(email);		
	}
	
	/** Enter the sing up Password. */
	public void enterPassword(String password){
		driver.findElement(By.id("password")).sendKeys(password);	
	}
	
	/** Click "I accept Term Of Services" check box. */ 
	public void clickAcceptTOS(){
		driver.findElement(By.id("tos")).click(); //"I accept" check box 		
	}
	
	/** 
	 * Submit the form.  
	 * Seperate function like submit should be in a seperate method.  
	 * 
	 * Operation that would cause the browser to point to another page 
	 * -- for example, clicking a link or submitting a form -- 
	 * then it's the responsibility of the operation to return the next page object. 
	 * [http://stackoverflow.com/questions/10315894/selenium-webdriver-page-object] */
	public VimeoSignupPage submitForm(){
		driver.findElement(By.cssSelector(INPUT_TYPE_SUBMIT)).click();   
		return new VimeoSignupPage(driver); 
	}


	/**
	 * Verify the error message of name field is displayed. 
	 */
	public boolean verifyErrorMessageRequired_Name_displayed() {  		
        	return isErrorMessageOfField_display(By.id("advice-required-name"));       
    	} 
	

	/** 
	 * Verify the Require email error message is displayed.
	 */
	public boolean isErrorMessageRequired_Email_displayed() {    
        	return isErrorMessageOfField_display(By.id("advice-required-email"));         
    	} 
	

	/** 
	 * Verify the Require Valid email error message is displayed.
	 */
	public boolean verifyErrorMessageRequired_ValidEmail_displayed() {    
        	return isErrorMessageOfField_display(By.id("advice-validate-email-email"));       
    	} 
	

	/**
	 * Verify the error message of password field is displayed. 
	 */
	public boolean verifyErrorMessageRequired_Password_displayed() { 
        	return isErrorMessageOfField_display(By.id("advice-required-password")); 
    	} 
	

	/**
	 * Verify the error message of "I accept Terms of Service" is displayed.
	 */
	public boolean isErrorMessageRequired_Check_TOS_displayed() { 
        	return isErrorMessageOfField_display(By.id("advice-validate-required-check-tos"));           
    	} 
	
	/**
	 * Check the Error Message field displayed.  
	 * It will wait for the JavaScript error message to be present on the DOM, and displayed. 
	 * 
	 * @param by
	 * @return
	 */
	private boolean isErrorMessageOfField_display(By by){
		WebElement element = null; 
		//wait for the Error Message Element to be present and display
		element = WaitTool.waitForElement(driver, by, 3); 
		if (element != null){
			return true;  
		}
		
		return false; 
	}
	
}
