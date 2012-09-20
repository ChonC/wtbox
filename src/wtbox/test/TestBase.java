package wtbox.test;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;

import wtbox.util.WaitTool;

/**
 * Test base class.  
 * What are the each test common  properties : implicitlyWait, WebDriver
 * @author owner
 *
 */
public class TestBase {

	/** This page's WebDriver */ 
	protected WebDriver driver;
	
	/** 
	 * Initialize test properties ( WebDriver, implicitlyWait, and etc).  
	 * 
	 * Note: for some project you can initialize the test setting based on property files or excel fille.*/ 
	protected void initialize(WebDriver driver){
		//implicitlyWait will poll the DOM every 500 milliseconds until the element is found (or timeout after 9 seconds)
		driver.manage().timeouts().implicitlyWait(WaitTool.DEFAULT_WAIT_4_PAGE, TimeUnit.SECONDS); 
		this.driver = driver; 
	}

	/**
	 * Set the driver implicitlyWait time.  
	 * @param waitTimeInSeconds
	 */
	public void setImplicitlyWaitTime(int waitTimeInSeconds){
		driver.manage().timeouts().implicitlyWait(waitTimeInSeconds, TimeUnit.SECONDS); 
	}
}
