package com.qa.test;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


import com.qa.base.BaseTest;

public class HomePageTest extends BaseTest{
	
	
	@Test
	public void homePageTitleTest() {
		
		
		
		String actualTitle=homePage.getHomePageTitle();
		System.out.println("This is your store...");
		Assert.assertEquals(actualTitle, prop.getProperty("title"));
	}
	
	@DataProvider
	public Object[][] getProductData() {
		return new Object[][] {
			
			{"Macbook"},
			{"Samsung"},
			{"IMAC"}
		};
	}
	
	@Test(dataProvider="getProductData")
	public void searchText(String productName) {
		String actualsearchheader=homePage.doSearch(productName);
		Assert.assertEquals(actualsearchheader, "Search - "+productName);
	}
	
	

}
