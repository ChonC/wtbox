package test.pageobject_example.pages;

import wtbox.pages.PageBase;
import wtbox.util.WaitTool;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Vimeo.com Sign up page Object.  It extends PageBase class. 
 * It provides properties and methods to test sign up page. 
 * 
 * @author Chon Chung
 *
 */
public class VimeoSignupPage extends PageBase{

	private static final String INPUT_TYPE_SUBMIT = "input[type=submit][value=Join]";	
	public static final String URL = "http://vimeo.com/join";
	

	/** constructor: initialized and load this page properties. */ 
	public VimeoSignupPage(WebDriver driver){
		super(driver); 
	}
	
	/** Enter the sing up data. */ 
	public void enterSignupData(String first_and_LastName, String email, String password){
		//enter data
		driver.findElement(By.id("name")).sendKeys(first_and_LastName);
		driver.findElement(By.id("email")).sendKeys(email);
		driver.findElement(By.id("password")).sendKeys(password);
		driver.findElement(By.id("tos")).click(); //"I accept" check box 
	}
	
	/** Submit the form.  Seperate function should be in a seperate method */
	public void submitForm(){
		driver.findElement(By.cssSelector(INPUT_TYPE_SUBMIT))
				.click();//@todo add css [value=Join]   
	}

	public VimeoProfilePage gotoProfilePage(){

		//Clicking "Me" button, and go to the profile page
		driver.findElement(By.xpath("//*[@id='menu']/li[1]/a")).click(); 
		
		return new VimeoProfilePage(driver); 
	}

	/**
	 * Get the error message of name field.
	 * @return "Please enter your name"
	 */
	public String getErrorMessageRequired_Name() {  
        return getErrorMessageOfField(By.id("advice-required-name"));       
    } 
	

	/** 
	 * Get the Require email error message.
	 * @return "Please enter a valid email address"
	 */
	public String getErrorMessageRequired_Email() {    
        return getErrorMessageOfField(By.id("advice-required-email"));   
    } 
	

	/** 
	 * Get the Require Valid email error message.
	 * @return "Please enter a valid email address"
	 */
	public String getErrorMessageRequired_ValidEmail() {    
        return getErrorMessageOfField(By.id("advice-validate-email-email"));   
    } 
	

	/**
	 * Get the error message of password field.
	 * @return "Please enter your password"
	 */
	public String getErrorMessageRequired_Password() { 
        return getErrorMessageOfField(By.id("advice-required-password"));    
    } 
	

	/**
	 * Get the error message of "I accept Terms of Service" checkbox required.
	 * @return "This field is required."
	 */
	public String getErrorMessageRequired_Check_TOS() { 
        return getErrorMessageOfField(By.id("advice-validate-required-check-tos"));    
    } 
	
	/**
	 * Get the Error Message of the field.  
	 * It will wait for the error message to be present on the DOM and displayed. 
	 * @param by
	 * @return
	 */
	private String getErrorMessageOfField(By by){
		//wait for the Error Message Element to be present and display
		WaitTool.waitForElement(driver, by, 3); 
        return driver.findElement(by).getText();    
	}
	
}
