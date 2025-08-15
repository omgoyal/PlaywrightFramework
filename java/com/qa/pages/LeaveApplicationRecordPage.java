package com.qa.pages;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.qa.factory.PropertiesFileReader;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class LeaveApplicationRecordPage {

    private final Page page;
    private final PropertiesFileReader filereader;

    // Locators
    private final Locator leaveRecordsTable;
    private final Locator leaveDashboardMenu;
    private final Locator deletePopupHeading;
    private final Locator deleteButton;

    public LeaveApplicationRecordPage(Page page) throws Exception {
        this.page = page;
        this.filereader = new PropertiesFileReader(System.getProperty("user.dir") + "/leaveapp.properties");

        // Init locators
        leaveRecordsTable = page.locator("tbody.p-element.p-datatable-tbody > tr");
        leaveDashboardMenu = page.locator("div.st-left-nav-wraper > ul :nth-child(1) > a");
        deletePopupHeading = page.locator("div.p-dialog-header:has-text('Delete')");
        deleteButton = page.locator("button:has-text('Delete')");
    }

    public static String getCurrentDateSpecificFormat(String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        String date = LocalDate.now().format(formatter);
        return date + " to " + date;
    }

    public static String getDateSpecificFormat(String strdate) {
        return strdate + "to" + strdate;
    }

    public boolean validateLeaveRecordDetails() {
        boolean flag = false;
        List<ElementHandle> rows = leaveRecordsTable.elementHandles();

        for (int i = 0; i < rows.size(); i++) {
            Locator row = page.locator("tbody.p-element.p-datatable-tbody > tr:nth-child(" + (i + 1) + ") > td");
            List<String> cells = row.allTextContents();

            for (int j = 0; j < cells.size(); j++) {
                if (cells.get(j).contains(filereader.getPropertyValue("leaveStatus"))) {
                    Assert.assertEquals(cells.get(j - 3), "Leave", "Verify the leave type");
                    Assert.assertEquals(cells.get(j - 2), getCurrentDateSpecificFormat(filereader.getPropertyValue("leavedate")), "Verify the leave period");
                    Assert.assertEquals(cells.get(j - 1), filereader.getPropertyValue("leaveDuration"), "Verify leave duration");
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }

    public boolean validateFlexiLeaveRecordDetails(String strLeaveDate) {
        boolean flag = false;
        List<ElementHandle> rows = leaveRecordsTable.elementHandles();

        for (int i = 0; i < rows.size(); i++) {
            Locator row = page.locator("tbody.p-element.p-datatable-tbody > tr:nth-child(" + (i + 1) + ") > td");
            List<String> cells = row.allTextContents();

            for (int j = 0; j < cells.size(); j++) {
                if (cells.get(j).contains(filereader.getPropertyValue("flexiLeaveStatus"))) {
                    Assert.assertEquals(cells.get(j - 3), "Flexible Holiday", "Verify the leave type");
                    Assert.assertEquals(cells.get(j - 2), getDateSpecificFormat(filereader.getPropertyValue(strLeaveDate)), "Verify the leave period");
                    Assert.assertEquals(cells.get(j - 1), filereader.getPropertyValue("leaveDuration"), "Verify leave duration");
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }

    public void clickLeaveDashboardMenu() {
        leaveDashboardMenu.click();
    }

    public boolean validateLeaveDashboardDetails() {
        boolean flag = false;
        String leaveMonth = getCurrentDateFormatted(filereader.getPropertyValue("leaveMonthFormat"));

        List<ElementHandle> rows = leaveRecordsTable.elementHandles();
        outer:
        for (int i = 0; i < rows.size(); i++) {
            Locator cells = page.locator("tbody.p-element.p-datatable-tbody > tr:nth-child(" + (i + 1) + ") > td");
            List<String> cellTexts = cells.allTextContents();

            for (int j = 0; j < cellTexts.size(); j++) {
                if (cellTexts.get(j).contains(leaveMonth)) {
                    cells.nth(j - 1).click();

                    Locator expanded = page.locator("p-table:nth-child(1) table tbody tr.st-expended-row td");
                    expanded.waitFor();

                    List<String> expandedTexts = expanded.allTextContents();

                    for (int k = 0; k < expandedTexts.size(); k++) {
                        String leaveDate = getCurrentDateFormatted(filereader.getPropertyValue("leavedate"));
                        if (expandedTexts.get(k).equalsIgnoreCase(leaveDate)) {
                            Assert.assertEquals(expandedTexts.get(k), leaveDate, "Verify leave applied date");
                            Assert.assertTrue(expandedTexts.get(k + 1).contains(filereader.getPropertyValue("noOfdays")), "Verify number of leave days");
                            flag = true;
                            break outer;
                        }
                    }
                }
            }
        }
        return flag;
    }

    public void clickDeleteIconForLeave() {
        List<ElementHandle> rows = leaveRecordsTable.elementHandles();
        for (int i = 0; i < rows.size(); i++) {
            Locator cells = page.locator("tbody.p-element.p-datatable-tbody > tr:nth-child(" + (i + 1) + ") > td");
            List<String> cellTexts = cells.allTextContents();

            for (int j = 0; j < cellTexts.size(); j++) {
                if (cellTexts.get(j).equalsIgnoreCase(filereader.getPropertyValue("leaveStatus"))) {
                    Locator deleteIcon = page.locator("tbody.p-element.p-datatable-tbody > tr:nth-child(" + (i + 1) + ") td >> nth=6 >> svg");
                    deleteIcon.click();
                    return;
                }
            }
        }
    }

    public boolean isDeletePopupVisible() {
        return deletePopupHeading.isVisible();
    }

    public void clickDeleteButtonInPopup() {
        deleteButton.click();
    }

    public boolean isLeaveRecordWithInProgressStatusAbsent() {
        List<ElementHandle> rows = leaveRecordsTable.elementHandles();

        for (int i = 0; i < rows.size(); i++) {
            Locator cells = page.locator("tbody.p-element.p-datatable-tbody > tr:nth-child(" + (i + 1) + ") > td");
            List<String> cellTexts = cells.allTextContents();

            for (String text : cellTexts) {
                if (text.contains(filereader.getPropertyValue("leaveStatus"))) {
                    return false;
                }
            }
        }
        return true;
    }

    private String getCurrentDateFormatted(String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return LocalDate.now().format(formatter);
    }
}

