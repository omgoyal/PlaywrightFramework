package com.qa.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.base.BaseTest;

public class LoginPageTest extends BaseTest {
	
	@Test
	public void loginPageNavigationTest() {
		homePage.clickMyAccount();
		loginPage=homePage.clickLoginButton();
		
		String actualLoginPageTitle=loginPage.getloginPageTitle();
		System.out.println("Actual login page title:"+actualLoginPageTitle);
		Assert.assertEquals(actualLoginPageTitle,  prop.getProperty("loginPageTitle"));
		
	}
	
	@Test
	public void loginPageNavigationTest1() {
		homePage.clickMyAccount();
		loginPage=homePage.clickLoginButton();
		
		String actualLoginPageTitle=loginPage.getloginPageTitle();
		System.out.println("Actual login page title:"+actualLoginPageTitle);
		Assert.assertEquals(actualLoginPageTitle,  prop.getProperty("loginPageTitle"));
		
	}
	
	@Test
	public void loginPageNavigationTest2() {
		homePage.clickMyAccount();
		loginPage=homePage.clickLoginButton();
		
		String actualLoginPageTitle=loginPage.getloginPageTitle();
		System.out.println("Actual login page title:"+actualLoginPageTitle);
		Assert.assertEquals(actualLoginPageTitle,  prop.getProperty("loginPageTitle"));
		
	}

}
