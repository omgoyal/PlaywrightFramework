package com.qa.factory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;


import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class PlaywrightFactory {
	
	Playwright playwright;
	Browser browser;
	BrowserContext browserContext;
	public static Page page;
	Properties prop;
	
	private static ThreadLocal<Browser> tlBrowser= new ThreadLocal<Browser>();
	private static ThreadLocal<BrowserContext> tlBrowserContext= new ThreadLocal<BrowserContext>();
	private static ThreadLocal<Page> tlPage= new ThreadLocal<Page>();
	private static ThreadLocal<Playwright> tlPlaywright= new ThreadLocal<Playwright>();
	
	
	public static Playwright getPlaywright() {
		return tlPlaywright.get();
	}
	
	public static Browser getBrowser() {
		return tlBrowser.get();
	}
	
	public static BrowserContext getBrowserContext() {
		return tlBrowserContext.get();
	}
	
	public static Page getPage() {
		return tlPage.get();
	}
	

	public Page initBrowser(Properties prop) {
		
		String browserName=prop.getProperty("browser").trim();
		System.out.println( "Browser: " + browserName);
		
		//playwright=Playwright.create();
		tlPlaywright.set(Playwright.create());
		
		switch(browserName.toLowerCase()) {
		
		case "chromium":
			//browser=playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
			tlBrowser.set(getPlaywright().chromium().launch(new BrowserType.LaunchOptions().setHeadless(false)));
			break;
			
		case "firefox":
			//browser=playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(false));
			tlBrowser.set(getPlaywright().firefox().launch(new BrowserType.LaunchOptions().setHeadless(false)));
			break;
			
		case "safari":
			//browser=playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(false));
			tlBrowser.set(getPlaywright().webkit().launch(new BrowserType.LaunchOptions().setHeadless(false)));
			break;
		
		case "chrome":
			//browser=playwright.chromium().launch(new LaunchOptions().setChannel("chrome").setHeadless(false));
			tlBrowser.set(getPlaywright().chromium().launch(new LaunchOptions().setChannel("chrome").setHeadless(false)));
			break;
			
		case "edge":
			//browser=playwright.chromium().launch(new LaunchOptions().setChannel("edge").setHeadless(false));
			tlBrowser.set(getPlaywright().chromium().launch(new LaunchOptions().setChannel("msedge").setHeadless(false)));
			break;
			
		default:
			System.out.println("Please pass the correct browser name....");
			break;
			
		}
		
		
		tlBrowserContext.set(getBrowser().newContext());
		tlPage.set(getBrowserContext().newPage());
		getPage().navigate(prop.getProperty("url").trim());
		
		//browserContext = browser.newContext();
		
		//page=browserContext.newPage();
		//page.navigate(prop.getProperty("url").trim());
		
		
		return getPage();
	}
	

	
	
	public static String takeFullPageScreenshot(Page page) {
	    page = getPage(); // fetch from ThreadLocal
	    if (page == null) {
	        throw new IllegalStateException("No Playwright Page instance found in ThreadLocal. Did you call initBrowser()?");
	    }
	    String path = System.getProperty("user.dir") + "/screenshots/" + System.currentTimeMillis() + ".png";
	    page.screenshot(new Page.ScreenshotOptions()
	            .setPath(Paths.get(path))
	            .setFullPage(true));
	    return path;
	}
	
	/**
	 * This method is used to initialize the properties from config file.
	 * 
	 */
	public Properties init_prop() {
		try {
			FileInputStream ip = new FileInputStream("./src/test/resources/conifg/config.properties");
			prop = new Properties();
			prop.load(ip);
		} catch (FileNotFoundException e) {
				e.printStackTrace();
		} catch (IOException e) {
			   e.printStackTrace();
		}
		
		return prop;
		
	}
	
	
	public static void tearDown() {
        try {
            if (getPage() != null) {
                getPage().close();
                tlPage.remove();
            }
            if (getBrowserContext() != null) {
                getBrowserContext().close();
                tlBrowserContext.remove();
            }
            if (getBrowser() != null) {
                getBrowser().close();
                tlBrowser.remove();
            }
            if (getPlaywright() != null) {
                getPlaywright().close();
                tlPlaywright.remove();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	


}
