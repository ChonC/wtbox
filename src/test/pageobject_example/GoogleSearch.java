package test.pageobject_example;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import static org.junit.Assert.assertTrue;

/**
 * Testing Google search.  This class does not use Page Object pattern.  
 * Compare it with “GoogleSearch_withPageObject.java” class, which uses Page Object pattern.  
 * 
 * This example is from 
 * "The 5 Minute Getting Started Guide 
 *  (http://code.google.com/p/selenium/wiki/GettingStarted)."  
 *
 */
public class GoogleSearch {

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
	        // Create a new instance of the html unit driver
	        WebDriver driver = new HtmlUnitDriver();

	        // And now use this to visit Google
	        driver.get("http://www.google.com");

	        // Find the text input element by its name
	        WebElement element = driver.findElement(By.name("q"));

	        // Enter something to search for
	        element.sendKeys("Cheese!");

	        // Now submit the form. WebDriver will find the form for us from the element
	        element.submit();
	        
	        //*** return the next page object

	        // Check the title of the page
	        String pageTitle = driver.getTitle(); 
	        System.out.println("Page title is: " + pageTitle);
	        assertTrue("Got title: " + pageTitle, pageTitle.contains("Cheese!")); 
	    }
	    
	    public static void testGoogleSuggest() throws Exception {
			
	        // The Firefox driver supports javascript 
	        WebDriver driver = new FirefoxDriver();
	        
	        // Go to the Google Suggest home page
	        driver.get("http://www.google.com/webhp?complete=1&hl=en");
	        
	        // Enter the query string "Cheese"
	        WebElement query = driver.findElement(By.name("q"));
	        query.sendKeys("Cheese");

	        // Sleep until the div we want is visible or 5 seconds is over
	        long end = System.currentTimeMillis() + 5000;
	        while (System.currentTimeMillis() < end) {
	            WebElement resultsDiv = driver.findElement(By.className("gsq_a"));

	            // If results have been returned, the results are displayed in a drop down.
	            if (resultsDiv.isDisplayed()) {
	              break;
	            }
	        }

	        // And now list the suggestions
	        List<WebElement> allSuggestions = driver.findElements(By.className("gsq_a"));
	        
	        for (WebElement suggestion : allSuggestions) {
	            System.out.println(suggestion.getText());
	        }
	     }
	    
	    
}

/**
 * References: 
 *  1. http://stackoverflow.com/questions/10315894/selenium-webdriver-page-object
 */
