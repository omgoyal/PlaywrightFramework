package com.qa.pages;

import com.microsoft.playwright.Page;

public class LoginPage {
	
	private Page page;
	
	private String emailId="input[id='input-email']";
	private String password="input[id='input-password']";
	private String btnLogin="input[type='submit']";
	
	public LoginPage(Page page){
		this.page=page;
	}
	
	
	public String getloginPageTitle() {
		return page.title();
		
	}
	
	public void doLogin(String appUserName,String appPassword) {
		
		System.out.println("App user: "+appUserName + ":"+appPassword );
		
		page.fill(emailId, appUserName);
		page.fill(password, appPassword);
		page.click(btnLogin);
	}

}
