package wtbox.util;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;


/**
 * Wait tool class.  Provides Wait methods for a page, page elements, and AJAX elements to load.  
 * It uses WebDriverWait for waiting an element or javaScript.  
 * WaitTool class is designed for easy to use.  
 * 
 * @author Chon Chung, Mark Collin 
 *
 * Copyright [2012] [Chon Chung]
 * 
 * Licensed under the Apache Open Source License, Version 2.0  
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * @todo waitForElementCondition( ExpectedConditions.textToBePresentInElement(locator, text))
 * @todo verify nullify implicitlyWait issue: in the waitForJavaScriptCondition() method. 
 */
public class WaitTool {

	/** Default wait time for an element.  6 seconds. */ 
	public static final int DEFAULT_WAIT_4_ELEMENT = 6; 
	/** Default wait time for a page to be displayed.  9 seconds.  The average webpage load time is 6 seconds in 2012. */ 
	public static final int DEFAULT_WAIT_4_PAGE = 9; 


	

	/**
	  * Wait for the element to be present in the DOM, and displayed on the page. 
	  *
	  * @param WebDriver - The driver object to be used 
	  * @param By -  - selector to find the element
	  * @param int - The time in seconds to wait until returning a failure
	  */
	public static void waitForElement(WebDriver driver, 
									  final By by, int timeOutInSeconds) {
		try{	
			//To use WebDriverWait(), we would have to nullify implicitlyWait(). 
			//Because implicitlyWait time also set "driver.findElement()" wait time.  
			//info from: https://groups.google.com/forum/?fromgroups=#!topic/selenium-users/6VO_7IXylgY
			driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS); //nullify implicitlyWait() 
			  
	        new WebDriverWait(driver, timeOutInSeconds) {
	        }.until(new ExpectedCondition<Boolean>() {
	
	            @Override
	            public Boolean apply(WebDriver driverObject) {
	            	return isElementPresentAndDisplay(driverObject, by); //is the element in the DOM, and displayed
	            }
	        });
	
			driver.manage().timeouts().implicitlyWait(DEFAULT_WAIT_4_PAGE, TimeUnit.SECONDS); //reset implicitlyWait
	
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	
	

	/**
	  * Wait for the element to be present in the DOM, regardless of being displayed or not.
	  *
	  * @param WebDriver - The driver object to be used 
	  * @param By -  - selector to find the element
	  * @param int - The time in seconds to wait until returning a failure
	  */
	public static void waitForElementPresent(WebDriver driver, 
									  		 final By by, int timeOutInSeconds) {

		try{
			driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS); //nullify implicitlyWait() 
			
	        new WebDriverWait(driver, timeOutInSeconds) {
	        }.until(new ExpectedCondition<Boolean>() {
	
	            @Override
	            public Boolean apply(WebDriver driverObject) {
	            	return isElementPresent(driverObject, by); //is the element in the DOM
	            }
	        });
	
			driver.manage().timeouts().implicitlyWait(DEFAULT_WAIT_4_PAGE, TimeUnit.SECONDS); //reset implicitlyWait
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	

	/**
	  * Wait for an element to appear on the web-page.
	  *
	  * This method is to deal with dynamic pages.
	  * 
	  * Some sites I (Mark) have tested have required a page refresh to add additional elements to the DOM.  
	  * Generally you wouldn’t need to do this in a typical AJAX scenario.
	  *	
	  *
	  * @param WebDriver - The driver object to use to perform this element search
	  * @param locator - The XPath of the element you are waiting for
	  * @param int - The time in seconds to wait until returning a failure
	  * 
	  * @author Mark Collin 
	  */
	 public static void waitForElementRefresh(WebDriver driver, final By by, 
			                           int timeOutInSeconds) {

		try{	
			driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS); //nullify implicitlyWait() 
		        new WebDriverWait(driver, timeOutInSeconds) {
		        }.until(new ExpectedCondition<Boolean>() {

		            @Override
		            public Boolean apply(WebDriver driverObject) {
		                driverObject.navigate().refresh(); //refresh the page ****************
		                return isElementPresentAndDisplay(driverObject, by);
		            }
		        });
			driver.manage().timeouts().implicitlyWait(DEFAULT_WAIT_4_PAGE, TimeUnit.SECONDS); //reset implicitlyWait

		} catch (Exception e) {
			e.printStackTrace();
		} 
	 }
	 
	/**
	  * Wait for the Text to be present in the DOM, regardless of being displayed or not.
	  *
	  * @param WebDriver - The driver object to be used to wait and find the element
	  * @param String - The text element we are waiting
	  * @param int - The time in seconds to wait until returning a failure
	  */
	public static void waitForTextPresent(WebDriver driver, 
			                              final String text, int timeOutInSeconds) {

		try{	
			driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS); //nullify implicitlyWait() 
	        new WebDriverWait(driver, timeOutInSeconds) {
	        }.until(new ExpectedCondition<Boolean>() {
	
	            @Override
	            public Boolean apply(WebDriver driverObject) {
	            	return isTextPresent(driverObject, text); //is the Text in the DOM
	            }
	        });
			driver.manage().timeouts().implicitlyWait(DEFAULT_WAIT_4_PAGE, TimeUnit.SECONDS); //reset implicitlyWait
	
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	



	/** 
	 * Waits for the completion of Ajax JavaScript processing.  
	 *
	 *
	 * @param WebDriver - The driver object to be used to wait and find the element
	 * @param String - The javaScript condition we are waiting. e.g. "return (xmlhttp.readyState >= 2 && xmlhttp.status == 200)" 
	 * @param int - The time in seconds to wait until returning a failure
	 * 
	 * TO DO: seems I do not have to nullify implicitlyWait() before calling executeScript 
     * because executeScript does not effected by implicitlyWait() time.  <-- I need to double check this.*/
	public static void waitForJavaScriptCondition(WebDriver driver, final String javaScript, 
            								   int timeOutInSeconds) {

		try{	
	        new WebDriverWait(driver, timeOutInSeconds) {
	        }.until(new ExpectedCondition<Boolean>() {
	
	            @Override
	            public Boolean apply(WebDriver driverObject) {
	            	return (Boolean) ((JavascriptExecutor) driverObject).executeScript(javaScript);
	            }
	        });
	        
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	
	/** Waits for the completion of Ajax jQuery processing.  */
	public static void waitForJQueryProcessing(WebDriver driver, int timeOutInSeconds){
		try{	
			driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS); //nullify implicitlyWait() 
	        new WebDriverWait(driver, timeOutInSeconds) {
	        }.until(new ExpectedCondition<Boolean>() {
	
	            @Override
	            public Boolean apply(WebDriver driverObject) {
	            	return (Boolean) ((JavascriptExecutor) driverObject).executeScript("return jQuery.active == 0");
	            }
	        });
	        
			driver.manage().timeouts().implicitlyWait(DEFAULT_WAIT_4_PAGE, TimeUnit.SECONDS); //reset implicitlyWait
		} catch (Exception e) {
			e.printStackTrace();
		} 
    }
	

	/**
	 * Coming to implicit wait, If you have set it once then you would have to explicitly set it to zero to nullify it -
	 */
	public static void nullifyImplicitWait(WebDriver driver) {
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS); //nullify implicitlyWait() 
	} 
	
	/**
	 * Reset ImplicitWait.  
	 * To reset ImplicitWait time you would have to explicitly 
	 * set it to zero to nullify it before setting it with a new time value. 
	 */
	public static void resetImplicitWait(WebDriver driver) {
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS); //nullify implicitlyWait() 
		driver.manage().timeouts().implicitlyWait(DEFAULT_WAIT_4_PAGE, TimeUnit.SECONDS); //reset implicitlyWait
	} 
	

	/**
	 * Reset ImplicitWait.  
	 * @param int - a new wait time in seconds
	 */
	public static void resetImplicitWait(WebDriver driver, int newWaittime_InSeconds) {
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS); //nullify implicitlyWait() 
		driver.manage().timeouts().implicitlyWait(newWaittime_InSeconds, TimeUnit.SECONDS); //reset implicitlyWait
	} 
		    

     /**
	   * Checks if the text is present in the DOM. 
       * 
	   * @param driver - The driver object to use to perform this element search
	   * @param text - The Text element you are looking for
	   * @return true or false
	   */
	private static boolean isTextPresent(WebDriver driver, String text)
	{
		try {
				return driver.getPageSource().contains(text);
		} catch (NullPointerException e) {
				return false;
		}
	}
		

	/**
	 * Checks if the elment is in the DOM, regardless of being displayed or not.
	 * 
	 * @param driver - The driver object to use to perform this element search
	 * @param by - selector to find the element
	 * @return true or false
	 */
	private static boolean isElementPresent(WebDriver driver, By by) {
		try {
			driver.findElement(by);//if it does not find the element throw NoSuchElementException
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}
	

	/**
	 * Checks if the elment is in the DOM and displayed. 
	 * 
	 * @param driver - The driver object to use to perform this element search
	 * @param by - selector to find the element
	 * @return true or false
	 */
	private static boolean isElementPresentAndDisplay(WebDriver driver, By by) {
		try {			
			return driver.findElement(by).isDisplayed();
		} catch (NoSuchElementException e) {
			return false;
		}
	}
	
 }
/*
 * References: 
 * 1. Mark Collin's post on: https://groups.google.com/forum/?fromgroups#!topic/webdriver/V9KqskkHmIs%5B1-25%5D
 * 	  Mark's code inspires me to write this class. Thank you! Mark. 
 * 
 * 2. Explicit and Implicit Waits: http://seleniumhq.org/docs/04_webdriver_advanced.html
 * 
 * Further reading: 
 * 1. Instead of creating new WebDriverWait() instance every time in the each methods, 
 *    I tried to reuse a single WebDriverWait() instance, but I found and tested 
 *    that creating 100 WebDriverWait() instances takes less than one millisecond. 
 *    So, it seems not necessary.  
 */
