package com.qa.test;

import static org.testng.Assert.assertEquals;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.qa.factory.PropertiesFileReader;
import com.qa.pages.LeaveApplicationHomePage;




public class LeaveApplicationTest  {

    Playwright playwright;
    Browser browser;
    BrowserContext context;
    Page page;
    PropertiesFileReader fileReader;
    LeaveApplicationHomePage leavePage;
 
    @BeforeClass
    public void setup() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        context = browser.newContext();
        page = context.newPage();
        fileReader = new PropertiesFileReader(System.getProperty("user.dir") + "/leaveapp.properties");
        leavePage = new LeaveApplicationHomePage(page);
        
       
    }

    @Test
    public void testLeaveApplyInLeaveApplication() {
        // Navigate to URL
        page.navigate(fileReader.getPropertyValue("strURL"));
       // extentReports.createTest("testLeaveApplyInLeaveApplication", "leave application test started ");
        // Login
        page.locator("input[type='email']").fill(fileReader.getPropertyValue("strEmail"));
        page.locator("input[type='submit']").click(); // Next button

        page.locator("input[type='password']").fill(fileReader.getPropertyValue("strPassword"));
        page.locator("#idSIButton9").click(); // Sign in

        // MFA: Sign in another way -> Text
        page.locator("#signInAnotherWay").click();
        page.locator("div:text('Text')").click();

        page.waitForTimeout(20000); // Wait for code input externally
        page.locator("#idSubmit_SAOTCC_Continue").click();
        page.waitForTimeout(5000);
        page.locator("#idSIButton9").click();

        // Wait until input box is visible
        //page.waitForSelector("input[type='search']");
        //page.navigate("https://leavemanager.nagarro.com/");
        page.waitForTimeout(5000);

        page.waitForLoadState();
        // Assert we’re on home page
        assertEquals(page.url(), fileReader.getPropertyValue("strHomePage"));

        // Click Apply Leave
        Locator applyLeaveButton = page.locator("button:has-text('Apply Leave')");
        applyLeaveButton.waitFor(new Locator.WaitForOptions().setTimeout(60000));
        applyLeaveButton.click();
        page.locator("span[aria-label='Select leave type']").click(); // Assumes a select exists
       
        Locator dropdownItem = page.locator("div.p-dropdown-items-wrapper span", new Page.LocatorOptions().setHasText("Leave"));
        dropdownItem.click();

        // Select Start & End Dates
        page.locator("input[placeholder='Start date']").click();
        page.locator("div.p-datepicker-calendar-container.ng-tns-c419082668-5.ng-star-inserted tr:nth-of-type(2) td:nth-of-type(5)").click(); // Assuming `td.today` is today
        page.locator("input[placeholder='End date']").click();
       page.locator("div.p-datepicker-calendar-container.ng-tns-c419082668-6.ng-star-inserted tr:nth-of-type(2) td:nth-of-type(5)").click();
        page.locator("button[type='submit']").click();

        // Assert end date calendar is not visible
        //assertFalse(page.locator("#endDateCalendar").isVisible());

        // Submit Leave
        //page.locator("button:has-text('Submit')").click();

        // Wait and Assert Leave Record Presence
        //page.waitForSelector("text='Leave Records'");
        //assertTrue(page.locator("text='Leave Records'").isVisible());

        // Go to Dashboard
        //page.locator("a[title='Leave Records']").click();
        //page.waitForSelector("text='Leave Summary'");
        page.waitForTimeout(10000);
        Assert.assertTrue(leavePage.isValidateLeaveRecordDetails(page, fileReader));
        

        // Validate Dashboard Entry
        //assertTrue(page.locator("table[id='pn_id_42-table'] tbody tr:nth-of-type(2) td:nth-of-type(2)").textContent().contains(fileReader.getPropertyValue("leavedate")));
        //assertTrue(page.locator("table[id='pn_id_42-table'] tbody tr:nth-of-type(2) td:nth-of-type(3)").textContent().contains(fileReader.getPropertyValue("leaveDuration")));
        //assertTrue(page.locator("table[id='pn_id_42-table'] tbody tr:nth-of-type(2) td:nth-of-type(4)").textContent().contains(fileReader.getPropertyValue("leaveStatus")));
    }

    @AfterClass
    public void teardown() {
        if (page != null) page.close();
        if (context != null) context.close();
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }
}
