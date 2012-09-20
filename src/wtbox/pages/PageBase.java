package wtbox.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;



/**
 * Page Object base class.  It provides the base structure 
 * and properties for a page object to extend.  
 * 
 * @author Chon Chung
 */
public class PageBase {
	  /** Default URL */
	  protected static String URL;	
	  /** This page's WebDriver */ 
	  protected WebDriver driver; 
	  
	  /** Constructor */ 
	  public PageBase(WebDriver driver) {
		  this.driver = driver; 
	  }
	  
	  /** Open the default page */ 
	  public void open(){
		  driver.get(URL); 
	  }
	  
	  /** Returns the page title */ 
	  public String getTitle() {
		  return driver.getTitle(); 
	  }
	  
	  /** 
	   * Send text keys to the element that finds by cssSelector.  
	   * It shortens "driver.findElement(By.cssSelector()).sendKeys()". 
	   * @param cssSelector
	   * @param text
	   */
	  protected void sendText(String cssSelector, String text) {
			driver.findElement(By.cssSelector(cssSelector)).sendKeys(text);
	  }
	  
	  /** Is the text present in page. */ 
	  public boolean isTextPresent(String text){
		  return driver.getPageSource().contains(text); 
	  }
	  
	  /** Is the Element in page. */
	  public boolean isElementPresent(By by) {
			try {
				driver.findElement(by);//if it does not find the element throw NoSuchElementException, thus returns false. 
				return true;
			} catch (NoSuchElementException e) {
				return false;
			}
	  }

	  /** 
	   * Is the Element present in page 
	   * 
	   * @param _cssSelector 		element locater
	   * @return					WebElement
	   */
	  public boolean isElementPresent(String _cssSelector){
			try {
				driver.findElement(By.cssSelector(_cssSelector));
				return true;
			} catch (NoSuchElementException e) {
				return false;
			}
	  }
	  
	  /** 
	   * Returns the first WebElement using the given method.  	   
	   * It shortens "driver.findElement(By)". 
	   * @param by 		element locater. 
	   * @return 		the first WebElement
	   */
	  public WebElement getWebElement(By by){
		  	return driver.findElement(by); 			
	  }
}
/**
 * Further reading: 
 * 1. Using Page Objects with Selenium and Web Driver 2.0
 * 		http://www.summa-tech.com/blog/2011/10/10/using-page-objects-with-selenium-and-web-driver-20/
 * 2. PageFactory
 * 		http://code.google.com/p/selenium/wiki/PageFactory
 * 		
 */
