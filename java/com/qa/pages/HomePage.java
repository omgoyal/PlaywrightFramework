package com.qa.pages;

import com.microsoft.playwright.Page;

public class HomePage {
	
	
	//Encapsulation example of data-member of private
	private Page page;
	
	//1.String locators -Object Repository
	
	private String search="input[name='search']";
	private String searchIcon="div#search button";
	private String searchPageHeader="div#content h1";
	
	
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
		String header=page.textContent(searchPageHeader);
		System.out.println("Search header is:"+header);
		return header;
		
		
	}

}
