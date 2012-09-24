package test.pageobject_example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import wtbox.pages.PageBase;
import wtbox.util.WaitTool;

/**
 * Vimeo.com Profile page Object.  It extends PageBase class.
 * It provides properties and methods to test profile page.  
 * 
 * @author Chon Chung
 *
 */
public class VimeoProfilePage  extends PageBase{
	
	/** constructor: initialized and load the page properties. */ 
	public VimeoProfilePage(WebDriver driver){
		super(driver); 
		//wait for page to load by waiting the main div (profile-div) to display 
		WaitTool.waitForElement(driver, By.cssSelector("div#profile"), WaitTool.DEFAULT_WAIT_4_PAGE); 
	}
	
	
	public String getProfileName(){
		//Get the profile name: <span>name</span> 
		return driver.findElement(By.xpath("//*[@id='profile']/div[2]/h1/span")).getText(); 
	}
	
}


