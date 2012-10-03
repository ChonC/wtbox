package test.pageobject_example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import wtbox.pages.PageBase;

/**
 * Vimeo.com Profile page Object.  It extends PageBase class.
 * It provides properties and methods to test profile page.  
 * 
 * @author Chon Chung
 *
 */
public class VimeoProfilePage  extends PageBase{

	private final static String pageTitle = "on Vimeo"; 

	/** constructor: initialized and load the page properties. */ 
	public VimeoProfilePage(WebDriver driver){
		super(driver, pageTitle); 
	}
	
	/**
	 * Get the profile name: <span>name</span> 
	 * @return profile name
	 */
	public String getProfileName(){
		return driver.findElement(By.xpath("//*[@id='profile']/div[2]/h1/span")).getText(); 
	}
	
}


