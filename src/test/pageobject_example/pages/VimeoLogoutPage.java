package test.pageobject_example.pages;

import org.openqa.selenium.WebDriver;
import wtbox.pages.PageBase;

public class VimeoLogoutPage extends PageBase{

	/** constructor. */ 
	public VimeoLogoutPage(WebDriver driver){
		super(driver); 
	}
	/** Log out. */ 
	public static void logOut(WebDriver driver){
		driver.get("https://vimeo.com/log_out"); 
	}
}
