package test.pageobject_example.pages;

import org.openqa.selenium.WebDriver;
import wtbox.pages.PageBase;

public class VimeoLogoutPage extends PageBase{
 
	private static String pageTitle = ""; 
	
	/** constructor. */ 
	public VimeoLogoutPage(WebDriver driver){
		super(driver, pageTitle); 
	}
	/** Log out. */ 
	public static void logOut(WebDriver driver){
		driver.get("https://vimeo.com/log_out"); 
	}
}
