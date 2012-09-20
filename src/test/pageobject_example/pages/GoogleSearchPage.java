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
	
	private final static String URL = "http://www.google.com"; 
	private final static String SUGGEST_PAGE_URL = "http://www.google.com/webhp?complete=1&hl=en"; 
	
	private final static String SEARCH_FIELD_NAME = "q"; 

	/** Constructor */ 
	public GoogleSearchPage(WebDriver driver){
		super(driver); 
	}
	
	/** Returns the default URL */
	public String getURL(){
		return URL; 
	}
	
	/** open Suggest home page */
	public void openSuggestPage(){
		driver.get(SUGGEST_PAGE_URL); 
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
	
	/**
	 * Enter the query string, and get the list of the suggestions
	 * It use WaitTool to wait for the div (class=gac_m). 
	 * WaitTool uses WebDriverWait, which is the recommended best practice. 
	 * 
	 * @param searchText the query string to search for
	 * @return the list of the google search suggestions
	 */
	public List<WebElement> getSearchSuggestions(String searchText){

		driver.findElement(By.name(SEARCH_FIELD_NAME)).sendKeys(searchText); 

        // Wait until the div to be visible, or 5 seconds is over
		WaitTool.waitForElementPresent(driver, By.className("gac_m"), 5); 

        // list the suggestions
        List<WebElement> allSuggestions = driver.findElements(By.xpath("//td[@class='gac_c']"));
		return allSuggestions; 
	}
}
