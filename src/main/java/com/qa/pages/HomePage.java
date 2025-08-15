package com.qa.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

public class HomePage {
	
	
	//Encapsulation example of data-member of private
	private Page page;
	
	//1.String locators -Object Repository
	
	private String search="input[name='search']";
	private String searchIcon="div#search button";
	private String MyAccount="a[title='My Account']";
	
	
	
	//2. Page Constructor 
	
	public HomePage(Page page) {
		
		this.page=page;
	}
	
	//3. Page Actions
	
	public String getHomePageTitle() {
		
	String title= page.title();
	System.out.println("page title :"+ title);
	return title;
		
	}
	
	
	public String getHomePageURL() {
		
		String pageurl= page.url();
		System.out.println("page url is :"+ pageurl);
		 return pageurl;
		 
	}
	
	public String doSearch(String productName) {
		
		page.fill(search,productName);
		page.click(searchIcon);
		
		Locator searchPageHeader = page.locator("div#content h1");
		searchPageHeader.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
		
		/*synchronized(searchPageHeader) {
			try {
				searchPageHeader.wait(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}  		}*/
	
		// Then get text
		String headerText = searchPageHeader.innerText();
		System.out.println("Header text: " + headerText);
		
		
		System.out.println("Search header is:"+headerText);
		return headerText;
		
		
	}
	
	
	/**
	 * This method is used to click on My Account link in home page.
	 */
	public void clickMyAccount() {
		page.click(MyAccount);
	}
	
	/**
	 * This method is used to click on login button in home page.
	 */
	public LoginPage clickLoginButton() {
		Locator btnLogin = page.locator("a:text('Login')");
		btnLogin.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
		btnLogin.click();
		return new LoginPage(page);
	}

}
