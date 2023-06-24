package com.sample.pages;


import com.sample.utilities.BrowserUtils;
import com.sample.utilities.Driver;
import com.sample.utilities.Waits;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class StudentSearch {

    BrowserUtils browserUtils;
    //CourseHome courseHome;

    public StudentSearch() {
        PageFactory.initElements(Driver.getDriver(), this);
        browserUtils = new BrowserUtils();
        //courseHome = new CourseHome();
    }

    //Elements:
    @FindBy(id = "emailAddress")
    protected static WebElement emailAddress_txt;

    @FindBy(xpath = "//input[@title='Search']")
    protected static WebElement search_btn;

    @FindBy(xpath = "//a[contains(@title,'Click to view')]")
    protected static WebElement studentListId_row;

    String advanced_btn = "//button[@id='details-button']";

    public void SearchByEmail(String email) {


        browserUtils.type(emailAddress_txt, email);
        browserUtils.clickByElement(search_btn);
    }

    public void SelectRecordFromResult() {
        browserUtils.clickByElement(studentListId_row);
    }

    public void SelectCourseFromEnrollment(String courseName) {

        String course_xpath = "//a[text()='" + courseName + "']";
        browserUtils.clickByElement(Driver.getDriver().findElement(By.xpath(course_xpath)));
        browserUtils.SwitchToNewTab();
        Waits.waitFixedTime(3);
        Waits.waitForBlocker();
        //browserUtils.clickByElement(courseHome.close_btn);
    }

    public boolean isLicenseDisplayed(String license) {
        return browserUtils.isElementVisible(Driver.getDriver().findElement(By.xpath(String.format("//td[text()='%s']", license))));
    }
}