package com.qa.base;

import java.util.Properties;

import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.microsoft.playwright.Page;
import com.qa.factory.PlaywrightFactory;
import com.qa.pages.HomePage;
import com.qa.pages.LoginPage;



public class BaseTest {
	
	PlaywrightFactory pf;
	Page page;
	protected Properties prop;
	
	protected HomePage homePage;
	protected LoginPage loginPage;

	
	@Parameters({"browser"})
	@BeforeMethod(alwaysRun = true)
    public void setup(String browserName) {
      

        pf = new PlaywrightFactory();
        prop = pf.init_prop();
        
        if(browserName!=null) {
        	prop.setProperty("browser", browserName);
        }

        // Initialize browser and page
        page = pf.initBrowser(prop);

        // Initialize page objects
        homePage = new HomePage(page);
        loginPage = new LoginPage(page);
    }
	
	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		PlaywrightFactory.tearDown();
		
	}
	

}
