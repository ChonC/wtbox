package test.pageobject_example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import wtbox.pages.PageBase;
import wtbox.util.WaitTool;

/**
 * Google Search Result Page Object. 
 * @author Chon Chung
 *
 */
public class GoogleSearchResultPage extends PageBase{
	
	private static final String resultStats_id = "resultStats"; 
	
	/**
	 * Constructor. It wait for the page to load by using WaitTool method. 
	 * @param driver
	 */
	public GoogleSearchResultPage(WebDriver driver){
		super(driver); 
		//wait for the page to load by waiting of google-search-resultStat 
		WaitTool.waitForElement(driver, By.id(resultStats_id), 5); 		
	}
	
	
	/** Returns the search ResultStat. */
	public String getSearchResultStat(){
		return driver.findElement(By.id(resultStats_id)).getText();
	}
	
	
}
