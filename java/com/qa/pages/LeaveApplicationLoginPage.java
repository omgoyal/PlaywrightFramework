package com.qa.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

public class LeaveApplicationLoginPage {

    private final Page page;

    // Locators
    private final Locator txtEmail;
    private final Locator btnNext;
    private final Locator txtPassword;
    private final Locator btnSignIn;
    private final Locator eleTextPhoneno;
    private final Locator eleCodeText;
    private final Locator eleCallPhoneno;
    private final Locator eleStaySignedIn;
    private final Locator btnVerify;
    private final Locator btnYes;
    private final Locator eleSignAnotherWay;

    public LeaveApplicationLoginPage(Page page) {
        this.page = page;

        // Initialize locators
        txtEmail = page.locator("input[type='email']");
        btnNext = page.locator("input[type='submit']");
        txtPassword = page.locator("input[name='passwd']");
        btnSignIn = page.locator("input[value='Sign in']");
        eleTextPhoneno = page.locator("div:has-text('Text')");
        eleCodeText = page.locator("input[name='otc']");
        eleCallPhoneno = page.locator("div:has-text('Call')");
        eleStaySignedIn = page.locator("div:has-text('Stay signed in?')");
        btnVerify = page.locator("input#idSubmit_SAOTCC_Continue");
        btnYes = page.locator("input#idSIButton9");
        eleSignAnotherWay = page.locator("a#signInAnotherWay");
    }

    public void enterEmailAddress(String email) {
        txtEmail.fill("");
        txtEmail.fill(email);
    }

    public void clickNextButton() {
        btnNext.click();
    }

    public void enterPassword(String password) {
        txtPassword.fill("");
        txtPassword.fill(password);
    }

    public void clickSignInButton() {
        btnSignIn.click();
    }

    public void clickTextPhoneNo() {
        eleTextPhoneno.click();
    }

    public void clickCallPhoneNo() {
        eleCallPhoneno.click();
    }

    public boolean isCodeTextPresent() {
        try {
            return eleCodeText.isVisible(new Locator.IsVisibleOptions().setTimeout(90000));
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isStaySignedInHeadingPresent() {
        try {
            return eleStaySignedIn.isVisible(new Locator.IsVisibleOptions().setTimeout(90000));
        } catch (Exception e) {
            return false;
        }
    }

    public void clickVerifyButton() {
        btnVerify.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(90000));
        btnVerify.click();
    }

    public void clickYesButton() {
        btnYes.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(90000));
        btnYes.click();
    }

    public void clickSignInAnotherWay() {
        eleSignAnotherWay.click();
    }
}

