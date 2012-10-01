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

import wtbox.util.TimeTool;
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
 * Generally relying on implicitlyWait slows things down 
 * so use WaitTool’s explicit wait methods as much as possible.
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
	 * Upon completion of AJAX XMLHttpRequest, XMLHttpRequest readState and status are changed.  
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
		//      Upon completion of AJAX XMLHttpRequest, XMLHttpRequest readState and status are changed.  
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
	
	/**
	 * Testing WaitTool class. Does WaitTool really work? 
	 * 
	 * This test verify 3 things: 
	 * 1.	Test if the WaitTool method only wait for the given wait time.  
	 *      and implicitlyWait setting does not effect on WaitTool's wait time. 
	 *  
	 * 2.	Test performance: how long does it takes calling driver-implicitlyWait 100 times.
	 * 
	 * 3.   Verify reset implicitlyWait() does actually work:
	 * 
     **/
	@Test 
	public void testWaitTool_class(){
		System.out.println("test WaitTool_class-----------------------------------------");
			driver.get("https://www.google.com/"); 
	
			//test if the WaitTool method only wait for the given amount wait time.  
			// and implicitlyWait setting does not effect on WaitTool's wait time. 
			System.out.println("Test WaitTool.waitForElement: 3 seconds wait time =========");
			System.out.println("	time before WaitTool.waitForElement: " + TimeTool.getCurrentTime()); 			
				//wait for 3 seconds: 
				WaitTool.waitForElement(driver, By.cssSelector("div#really_long_id_should_not_in_a_page__blar_blar_blar"), 3); 
			System.out.println("	time  after WaitTool.waitForElement: " + TimeTool.getCurrentTime()); 
			System.out.println(""); 
			
			
			//test performance: how long does it takes calling driver-implicitlyWait 100 times.
			System.out.println("Test performance: calling driver-implicitlyWait 100 times=========");
			System.out.println("	time before calling: " + TimeTool.getCurrentTime()); 
				for (int i = 0; i < 50; i++){
					//resetImplicitWait call driver-implicitlyWait() 2 times(nullify and set). 
					WaitTool.resetImplicitWait(driver, i);
				}
			System.out.println("	time  after calling: " + TimeTool.getCurrentTime()); 
			System.out.println("");
			
			//Test reset implicitWait
			WaitTool.resetImplicitWait(driver, 2);
			System.out.println("Test resetImplicitWait (given wait time = 2 seconds) =========");
			System.out.println("	time before: " + TimeTool.getCurrentTime()); 
				try{
					//check how long implicitlyWait() do actually wait
					driver.findElement(By.id("really_long_id_should_not_in_a_page__blar_blar_blar")); 
				}catch (Exception e){}
			System.out.println("	time  after: " + TimeTool.getCurrentTime()); 
			System.out.println("");
		System.out.println("test WaitTool_class-----------------------------------------");
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
