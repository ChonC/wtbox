package test.pageobject_example.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import wtbox.pages.PageBase;
import wtbox.util.WaitTool;

public class GoogleSearchSuggestPage extends PageBase{
	private final static String pageTitle = "Google"; 

	private final static String SEARCH_FIELD_NAME = "q"; 
	
	/** Constructor */ 
	public GoogleSearchSuggestPage(WebDriver driver){
		super(driver, pageTitle); 

		// set the default URL
		URL = "http://www.google.com/webhp?complete=1&hl=en";
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
	 * It use WaitTool to wait for the div (class=gsq_a).  
	 * 
	 * @param searchText the query string to search for
	 * @return the list of the google search suggestions
	 */
	public List<WebElement> getSearchSuggestions(String searchText){
        // Enter the query string "Cheese"
		driver.findElement(By.name(SEARCH_FIELD_NAME)).sendKeys(searchText); 

        // Wait and get the list of suggestions
        List<WebElement> allSuggestions = WaitTool.waitForListElementsPresent(driver, By.className("gsq_a"), 5);
		return allSuggestions; 
	}
}
