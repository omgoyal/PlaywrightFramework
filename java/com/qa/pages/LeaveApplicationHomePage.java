package com.qa.pages;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.WaitForSelectorState;
import java.util.ArrayList;
import java.util.List;

public class LeaveApplicationHomePage {

    private final Page page;

    // Locators
    private final Locator btnApplyLeave;
    private final Locator dropdownLeaveType;
    private final Locator optionLeave;
    private final Locator optionFlexibleHoliday;
    private final Locator inputStartDate;
    private final Locator todayStartDate;
    private final Locator inputEndDate;
    private final Locator todayEndDate;
    private final Locator btnSubmit;
    private final Locator menuLeaveRecord;
    private final Locator availableFlexiLeaveText;
    private final Locator chooseFlexiLeaveField;
    private final Locator flexiLeaveOptions;
    private final Locator selectedFlexiLeave;
    private final Locator loadingSpinner;

    public LeaveApplicationHomePage(Page page) {
        this.page = page;

        btnApplyLeave = page.locator("button:has-text('Apply leave')");
        dropdownLeaveType = page.locator(".p-dropdown-label.p-placeholder");
        optionLeave = page.locator("p-dropdownitem >> nth=0");
        optionFlexibleHoliday = page.locator("p-dropdownitem >> nth=1");
        inputStartDate = page.locator("input[placeholder='Start date']");
        todayStartDate = page.locator(".p-datepicker-today");
        inputEndDate = page.locator("input[placeholder='End date']");
        todayEndDate = page.locator(".p-datepicker-today");  // assuming same class used
        btnSubmit = page.locator("button:has-text('Submit')");
        menuLeaveRecord = page.locator("ul > li >> text=Leave records");
        availableFlexiLeaveText = page.locator("div.st-yearly-leave-wraper.ml-3 div:nth-of-type(4) > p:first-child");
        chooseFlexiLeaveField = page.locator("apply-leave p-dropdown:last-child");
        flexiLeaveOptions = page.locator("ul.p-dropdown-items p-dropdownitem");
        selectedFlexiLeave = page.locator("ul.p-dropdown-items p-dropdownitem:last-child");
        loadingSpinner = page.locator("div.st-overlay-widh-loading.ng-star-inserted > p-progressspinner");
    }

    public String getCurrentURL() {
        return page.url();
    }

    public void clickApplyLeaveButton() {
        btnApplyLeave.click();
    }

    public boolean isApplyLeaveButtonVisible() {
        return btnApplyLeave.isVisible();
    }

    public void clickSelectLeaveType() {
        dropdownLeaveType.click();
    }

    public void selectLeaveOption() {
        optionLeave.click();
    }

    public void selectFlexibleHolidayOption() {
        optionFlexibleHoliday.click();
    }

    public void clickStartDateField() {
        inputStartDate.click();
    }

    public void selectTodayStartDate() {
        todayStartDate.click();
    }

    public void clickEndDateField() {
        inputEndDate.click();
    }

    public void selectTodayEndDate() {
        todayEndDate.click();
    }

    public void clickSubmitButton() {
        btnSubmit.click();
    }

    public void waitForLoadingToDisappear() {
        page.waitForSelector("div.st-overlay-widh-loading.ng-star-inserted > p-progressspinner",
                new Page.WaitForSelectorOptions().setState(WaitForSelectorState.DETACHED).setTimeout(10000));
    }

    public void clickLeaveRecordsMenu() {
        menuLeaveRecord.click();
    }

    public int getAvailableFlexiLeaveBalance() {
        String balanceText = availableFlexiLeaveText.textContent().trim();
        return Integer.parseInt(balanceText);
    }

    public boolean isFlexiLeaveBalanceAvailable() {
        return getAvailableFlexiLeaveBalance() > 0;
    }

    public void clickChooseADayFlexiLeave() {
        chooseFlexiLeaveField.click();
    }

    public List<String> getFlexiLeaves() {
        List<String> values = new ArrayList<>();
        flexiLeaveOptions.all().forEach(option -> values.add(option.textContent().trim()));
        return values;
    }

    public void selectFlexiLeave() {
        selectedFlexiLeave.click();
    }
}
