package wtbox.util;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


/**
 * Wait tool class.  Provides Wait methods for an elements, and AJAX elements to load.  
 * It uses WebDriverWait for waiting an element or javaScript.  
 * 
 * To use implicitlyWait() and WebDriverWait() in the same test, 
 * we would have to nullify implicitlyWait() before calling WebDriverWait(), 
 * and reset after it.  This class takes care of it. 
 * 
 * @author Chon Chung, Mark Collin, Andre, Tarun Kumar 
 *
 * Copyright [2012] [Chon Chung]
 * 
 * Licensed under the Apache Open Source License, Version 2.0  
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 */
public class WaitTool {

	/** Default wait time for an element. 7  seconds. */ 
	public static final int DEFAULT_WAIT_4_ELEMENT = 7; 
	/** Default wait time for a page to be displayed.  12 seconds.  
	 * The average webpage load time is 6 seconds in 2012. 
	 * Based on your tests, please set this value. */ 
	public static final int DEFAULT_WAIT_4_PAGE = 12; 


	

	/**
	  * Wait for the element to be present in the DOM, and displayed on the page. 
	  *
	  * @param WebDriver	The driver object to be used 
	  * @param By	selector to find the element
	  * @param int	The time in seconds to wait until returning a failure
	  *
	  * @return WebElement	return the element, or null
	  */
	public static WebElement waitForElement(WebDriver driver, final By by, int timeOutInSeconds) {
		WebElement element; 
		try{	
			//To use WebDriverWait(), we would have to nullify implicitlyWait(). 
			//Because implicitlyWait time also set "driver.findElement()" wait time.  
			//info from: https://groups.google.com/forum/?fromgroups=#!topic/selenium-users/6VO_7IXylgY
			driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS); //nullify implicitlyWait() 
			  
			WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds); 
			element = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			
			driver.manage().timeouts().implicitlyWait(DEFAULT_WAIT_4_PAGE, TimeUnit.SECONDS); //reset implicitlyWait
			return element; //return the element	
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null; 
	}
	
	
	

	/**
	  * Wait for the element to be present in the DOM, regardless of being displayed or not.
	  *
	  * @param WebDriver	The driver object to be used 
	  * @param By	selector to find the element
	  * @param int	The time in seconds to wait until returning a failure
	  * 
	  * @return WebElement	return the element, or null
	  */
	public static WebElement waitForElementPresent(WebDriver driver, final By by, int timeOutInSeconds) {
		WebElement element; 
		try{
			driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS); //nullify implicitlyWait() 
			
			WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds); 
			element = wait.until(ExpectedConditions.presenceOfElementLocated(by));
			
			driver.manage().timeouts().implicitlyWait(DEFAULT_WAIT_4_PAGE, TimeUnit.SECONDS); //reset implicitlyWait
			return element; //return the element
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null; 
	}
	

	/**
	  * Wait for an element to appear on the refreshed web-page.
	  *
	  * This method is to deal with dynamic pages.
	  * 
	  * Some sites I (Mark) have tested have required a page refresh to add additional elements to the DOM.  
	  * Generally you (Chon) wouldn�t need to do this in a typical AJAX scenario.
	  * 
	  * @param WebDriver	The driver object to use to perform this element search
	  * @param locator	The XPath of the element you are waiting for
	  * @param int	The time in seconds to wait until returning a failure
	  * 
	  * @return WebElement	return the element, or null
	  * 
	  * @author Mark Collin 
	  */
	 public static WebElement waitForElementRefresh(WebDriver driver, final By by, 
			                           int timeOutInSeconds) {
		WebElement element; 
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
		    element = driver.findElement(by);
			driver.manage().timeouts().implicitlyWait(DEFAULT_WAIT_4_PAGE, TimeUnit.SECONDS); //reset implicitlyWait
			return element; //return the element
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null; 
	 }
	 
	/**
	  * Wait for the Text to be present in the DOM, regardless of being displayed or not.
	  *
	  * @param WebDriver	The driver object to be used to wait and find the element
	  * @param String	The text element we are waiting
	  * @param int	The time in seconds to wait until returning a failure
	  * 
	  * @return boolean 
	  */
	public static boolean waitForTextPresent(WebDriver driver, final String text, int timeOutInSeconds) {
		boolean isPresent = false; 
		try{	
			driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS); //nullify implicitlyWait() 
	        new WebDriverWait(driver, timeOutInSeconds) {
	        }.until(new ExpectedCondition<Boolean>() {
	
	            @Override
	            public Boolean apply(WebDriver driverObject) {
	            	return isTextPresent(driverObject, text); //is the Text in the DOM
	            }
	        });
	        isPresent = isTextPresent(driver, text);
			driver.manage().timeouts().implicitlyWait(DEFAULT_WAIT_4_PAGE, TimeUnit.SECONDS); //reset implicitlyWait
			return isPresent; 
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return false; 
	}
	



	/** 
	 * Waits for the completion of Ajax JavaScript processing.  
	 *
	 *
	 * @param WebDriver		The driver object to be used to wait and find the element
	 * @param String	The javaScript condition we are waiting. e.g. "return (xmlhttp.readyState >= 2 && xmlhttp.status == 200)" 
	 * @param int	The time in seconds to wait until returning a failure
	 * 
	 * @return boolean 
	 **/
	public static boolean waitForJavaScriptCondition(WebDriver driver, final String javaScript, 
            								   int timeOutInSeconds) {
		boolean jscondition = false; 
		try{	
			driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS); //nullify implicitlyWait() 
	        new WebDriverWait(driver, timeOutInSeconds) {
	        }.until(new ExpectedCondition<Boolean>() {
	
	            @Override
	            public Boolean apply(WebDriver driverObject) {
	            	return (Boolean) ((JavascriptExecutor) driverObject).executeScript(javaScript);
	            }
	        });
	        jscondition =  (Boolean) ((JavascriptExecutor) driver).executeScript(javaScript); 
			driver.manage().timeouts().implicitlyWait(DEFAULT_WAIT_4_PAGE, TimeUnit.SECONDS); //reset implicitlyWait
			return jscondition; 
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return false; 
	}

	
	/** Waits for the completion of Ajax jQuery processing.  
	 *
	 * @param WebDriver - The driver object to be used to wait and find the element
	 * @param int - The time in seconds to wait until returning a failure
	 * 
	 * @return boolean 
	 * */
	public static boolean waitForJQueryProcessing(WebDriver driver, int timeOutInSeconds){
		boolean jQcondition = false; 
		try{	
			driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS); //nullify implicitlyWait() 
	        new WebDriverWait(driver, timeOutInSeconds) {
	        }.until(new ExpectedCondition<Boolean>() {
	
	            @Override
	            public Boolean apply(WebDriver driverObject) {
	            	return (Boolean) ((JavascriptExecutor) driverObject).executeScript("return jQuery.active == 0");
	            }
	        });
	        jQcondition = (Boolean) ((JavascriptExecutor) driver).executeScript("return jQuery.active == 0");
			driver.manage().timeouts().implicitlyWait(DEFAULT_WAIT_4_PAGE, TimeUnit.SECONDS); //reset implicitlyWait
			return jQcondition; 
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return jQcondition; 
    }
	

	/**
	 * Coming to implicit wait, If you have set it once then you would have to explicitly set it to zero to nullify it -
	 */
	public static void nullifyImplicitWait(WebDriver driver) {
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS); //nullify implicitlyWait() 
	} 
	

	/**
	 * Set driver implicitlyWait() time. 
	 */
	public static void setImplicitWait(WebDriver driver, int waitTime_InSeconds) {
		driver.manage().timeouts().implicitlyWait(waitTime_InSeconds, TimeUnit.SECONDS);  
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
	 * @return boolean
	 */
	private static boolean isElementPresent(WebDriver driver, By by) {
		try {
			driver.findElement(by);//if it does not find the element throw NoSuchElementException, which calls "catch(Exception)" and returns false; 
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
	 * @return boolean
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
 * 2. Andre, and Tarun Kumar's post on: https://groups.google.com/forum/?fromgroups=#!topic/selenium-users/6VO_7IXylgY  
 * 3. Explicit and Implicit Waits: http://seleniumhq.org/docs/04_webdriver_advanced.html
 * 
 * Note: 
 * 1. Instead of creating new WebDriverWait() instance every time in each methods, 
 *    I tried to reuse a single WebDriverWait() instance, but I found and tested 
 *    that creating 100 WebDriverWait() instances takes less than one millisecond. 
 *    So, it seems not necessary.  
 */
