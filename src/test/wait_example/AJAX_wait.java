package test.wait_example;


import java.util.concurrent.TimeUnit;

import junit.framework.Assert;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import static junit.framework.Assert.*;

import wtbox.util.WaitTool;

/**
 * These tests show how to handle AJAX elements wait in Selenium 2 (WebDriver). 
 * 
 * The best practice is to set implicitWait at the beginning of a test, 
 * and use WebDriverWait for AJAX and specific elements.  
 * 
 * But you cannot use implicitWait and WebDriverWait at the same time 
 * because you have to set implicitWait to 0 before calling WebDriverWait, 
 * and reset afterwards.  
 * 
 * However, our WaitTool solve the complexity of implicitWait and WebDriverWait, 
 * and provides easy methods to use.  
 * 
 * This class shows how to use WaitTool, and how to handle AJAX elements wait. 
 * @author Chon Chung  
 */
public class AJAX_wait {

	private static WebDriver driver;

	/** Initialized class properties before executing  this test class. */ 
	@BeforeClass
	public static void beforeClass(){
		
        driver = new FirefoxDriver(); 
	}
	
	/**
	 * Set the testing properties before each test. 
	 **/
	@Before
	public void setup() {
        //Set implicitlyWait, so WebDriver will wait for an element if they are not immediately available
		WaitTool.setImplicitWait(driver, WaitTool.DEFAULT_WAIT_4_PAGE); 
	}
	
	/**
	 * Testing AJAX XMLHttpRequest wait.  
	 * I use WaitTool.waitForJavaScriptCondition() for waiting AJAX XMLHttpRequest received. 
	 * 
	 * Upon completion of AJAX XMLHttpRequest, XMLHttpRequest’s readState and status are changed.  
     * So, we can use them as the checking condition of an AJAX call.
	 */
	@Test
	public void testW3schools_AJAX(){
		driver.get("http://www.w3schools.com/ajax/default.asp"); 
		
		//Get the original text
		String originalText = driver.findElement(By.cssSelector("div#myDiv")).getText(); 
		

		//click the AJAX call button.  it call  "loadXMLDoc()" JavaScript function to load AJAX elements. 
		driver.findElement(By.cssSelector("div#myDiv+button")).click();
		
		//Wait for AJAX XMLHttpRequest received condition
		//Note: In the called "loadXMLDoc()" function, there is "xmlhttp.readyState==4 && xmlhttp.status==200" javaScript condition. 
		//      Upon completion of AJAX XMLHttpRequest, XMLHttpRequest’s readState and status are changed.  
		//      So, we can use them as the checking condition of an AJAX call.  
		//      For more info: http://www.w3schools.com/ajax/ajax_xmlhttprequest_onreadystatechange.asp
		boolean isReady = WaitTool.waitForJavaScriptCondition(driver, 
				                             "return (xmlhttp.readyState >= 2 && xmlhttp.status == 200)", 5); 
		
		if (isReady){
			//Get the changed text after AJAX call
			String afterAJAX_Call_Text = driver.findElement(By.cssSelector("div#myDiv")).getText();
			
	
			System.out.println("Before Text: " + originalText);
	
			//assert not equal 
			assertFalse("AJAX Call Changed the text", originalText.equalsIgnoreCase(afterAJAX_Call_Text)); 
			
			System.out.println("After AJAX call - Text: " + afterAJAX_Call_Text);
		}else{
			Assert.fail("Verify Failed: AJAX XMLHttpRequest is not ready"); 
		}
		
	}


	/**
	 * Testing AJAX Elements wait.  
	 * I use WaitTool.waitForElement() for waiting AJAX elements. 
	 * 
	 * -----------------------------------------------------------------------
	 * Note: the original test from: 
	 *  [Video tutorial: functional testing with Selenium IDE] http://blog.bielu.com/2008/10/video-tutorial-functional-testing-with.html
	 */
	@Test
	public void testBielu_com_AJAX()
    {
		driver.get("http://java.bielu.com:10080"); 
		
		//Click the ("Image Statistics") button to load an AJAX page
		driver.findElement(By.cssSelector("a[title=\"Image Statistics\"] > img")).click(); 

		//Wait for an AJAX Element ("Summed Images Impressions by Region") 
		WebElement summed_img = WaitTool.waitForElement(driver, By.xpath("//div[@id='statistics']/img[3]"), 5); 

		//Wait for an AJAX Element ("Directories Visits") 
		WebElement directory_img = WaitTool.waitForElement(driver, By.xpath("//div[@id='statistics']/img[5]"),5 ); 
		
		if(summed_img != null && directory_img != null){			
			// compare with expected values with the actual values
			assertEquals("Image width" , 400, summed_img.getSize().width);  
			assertEquals("Image height" , 250, summed_img.getSize().height);  
	
			System.out.println("Summed image size: " + summed_img.getSize()); 
			
			assertEquals("Image width" , 800, directory_img.getSize().width);  
			assertEquals("Image height" , 250, directory_img.getSize().height);  
	
			System.out.println("directory image size: " + directory_img.getSize()); 
		}else{
			Assert.fail("Verify Failed: fail for waiting AJAX elements"); 
		}
		
    }

	/**
	 * TO DO: show jquery AJAX testing demo here. 
	 */
	public void testJquery4u_AJAX(){
		//driver.get("http://www.jquery4u.com/function-demos/ajax"); 
	}
	
	@AfterClass
	public static void tearDown(){
		driver.quit();   
	}
}

/**
 * References: 
 * 1. Javascript with WebDriver (Selenium 2): http://stackoverflow.com/questions/11430773/javascript-with-webdriver-selenium-2
 */
