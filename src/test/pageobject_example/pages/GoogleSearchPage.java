package test.pageobject_example.pages;


import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import wtbox.pages.PageBase;
import wtbox.util.WaitTool;


/**
 * Google Search Page Object. 
 * @author Chon Chung
 */
public class GoogleSearchPage  extends PageBase{
	
	private final static String pageTitle = "Google"; 
	
	private final static String SEARCH_FIELD_NAME = "q"; 

	/** Constructor */ 
	public GoogleSearchPage(WebDriver driver){
		super(driver, pageTitle); 

		// set the default URL
		URL = "http://www.google.com";
	}
	
	/** Returns the default URL */
	public String getURL(){
		return URL; 
	}
	
	/** Enter the Search Text in the Search form field */
	public void enterSearchForm(String searchText){
		driver.findElement(By.name(SEARCH_FIELD_NAME)).sendKeys(searchText); 		
	}
	
	/** Submit the form and return the next page object.  
	 *  Seperate function should be in a seperate method. */
	public GoogleSearchResultPage submitForm(){
		driver.findElement(By.name(SEARCH_FIELD_NAME)).submit();   
		return new GoogleSearchResultPage(driver); 
	}
	
}
