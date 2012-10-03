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
	private final static String pageTitle = "Google Search"; 
	
	
	public GoogleSearchResultPage(WebDriver driver){
		super(driver, pageTitle); 	
	}
	
	
	/** Returns the search ResultStat. */
	public String getSearchResultStat(){
		return driver.findElement(By.id(resultStats_id)).getText();
	}
	
	
}
