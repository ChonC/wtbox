package test.pageobject_example;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import test.pageobject_example.pages.GoogleSearchPage;
import test.pageobject_example.pages.GoogleSearchResultPage;

/**
 * Google search demo class.  This class use page objects.  
 * Compare with "GoogleSearch" class, and see how to use page objects. 
 * 
 * @author Chon Chung
 *
 */
public class GoogleSearch_withPageObject {

    public static void main(String[] args) {
    	GoogleSearch.testGoogleSearch(); 
    	try{
    		GoogleSearch.testGoogleSuggest(); 
    	}catch(Exception e){
    		System.out.println("Unable to perform testGoogleSuggest() - " + 
    							e.getMessage());
    	}
    }
    
    public static void testGoogleSearch(){
    	//Create a new instance of GoogleSearch page
    	GoogleSearchPage googleSearchPage = new GoogleSearchPage(new HtmlUnitDriver()); 

        // And now go to Google ("http://www.google.com")
    	googleSearchPage.open(); 

        // Enter something to search for
    	googleSearchPage.enterSearchForm("Cheese!"); 

        // Now submit the form, and get the next page object
    	GoogleSearchResultPage googleSearchResultPage = googleSearchPage.submitForm(); 
               

        // Check the title of the page    	
        String pageTitle = googleSearchResultPage.getTitle();  
        System.out.println("Page title is: " + pageTitle);
        assertTrue("Got title: " + pageTitle, pageTitle.contains("Cheese!")); 
    }
    
    public static void testGoogleSuggest() throws Exception {
    	//If your Firefox is not installed in the default location 
    	//for your particular operating system:
    	//you need to Set the firefox binary path property.
		System.setProperty("webdriver.firefox.bin", 
        "C:\\Programs\\Mozilla Firefox\\firefox.exe");
		
        // The Firefox driver supports javascript 
		GoogleSearchPage googleSearchPage = new GoogleSearchPage(new FirefoxDriver());
        
        // Go to the Google Suggest home page: "http://www.google.com/webhp?complete=1&hl=en"
		googleSearchPage.openSuggestPage(); 
        
        // Enter the query string "Cheese", and get the list the suggestions
        List<WebElement> allSuggestions = googleSearchPage.getSearchSuggestions("Cheese");
        
        for (WebElement suggestion : allSuggestions) {
            System.out.println(suggestion.getText());
        }
     }
}
