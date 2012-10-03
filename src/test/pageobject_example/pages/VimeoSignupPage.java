package test.pageobject_example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import wtbox.pages.PageBase;

public class VimeoSignupPage extends PageBase{

	private final static String pageTitle = "You just joined Vimeo. Nice work!"; 
	
	/** constructor: initialized and load the page properties. */ 
	public VimeoSignupPage(WebDriver driver){
		super(driver, pageTitle); 
	}

	/**
	 * Clicking the profile page link ("Me"). 
	 * 
	 * Operation that would cause the browser to point to another page 
	 * -- for example, clicking a link or submitting a form -- 
	 * then it's the responsibility of the operation to return the next page object. 
	 * [http://stackoverflow.com/questions/10315894/selenium-webdriver-page-object] 
	 * 
	 * @return VimeoProfilePage
	 */
	public VimeoProfilePage clickProfileLink(){
		//Clicking the profile link ("Me") 
		driver.findElement(By.xpath("//*[@id='menu']/li[1]/a")).click(); 
		
		return new VimeoProfilePage(driver); 
	}

	
	
}
