package com.sample.pages;


import com.sample.utilities.BrowserUtils;
import com.sample.utilities.Driver;
import com.sample.utilities.Waits;
import groovy.time.BaseDuration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    BrowserUtils browserUtils;
    //CourseHome courseHome;

    public HomePage() {
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

/*    @FindBy(xpath = "//div[@data-id='auth-flow-section']/span")
    public static WebElement loginClose_btn;*/

    public By loginClose_btn = By.xpath("//div[@data-id='auth-flow-section']/span");
    public By destination_btn = By.xpath("(//p[text()='Enter city or airport'])[1]");
    public By From_txt = By.xpath("//span[text()='From']/following-sibling::input");
    public By To_txt = By.xpath("//span[text()='To']/following-sibling::input");
    public By suggestion = By.xpath("//ul[@id='autoSuggest-list']/li/div");
    public By departure = By.xpath("//span[text()='Departure']/following-sibling::p");
    public By travel_details = By.xpath("//span[contains(text(),'Travellers')]/following-sibling::p");
    public By departure_30day = By.xpath("(//div[@class='DayPicker-Day'])[30]");
    public By traveller_student = By.xpath("//label[@for='ST']/div");
    public By traveller_done= By.xpath("//a[text()='Done']");
    public By searchFlight_btn = By.xpath("//span[text()='SEARCH FLIGHTS']");
    public By loader_btn = By.xpath("//div[@class='loader']");

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

    public void CloseLoginPopup(){

        if(BrowserUtils.findElement(Driver.getDriver(),loginClose_btn,5)!=null){
            BrowserUtils.findElement(Driver.getDriver(),loginClose_btn,5,"click");
        }

    }
    public void EnterFromCity(String from){
        Driver.getDriver().findElement(destination_btn).click();
        Waits.waitFixedTime(1);
        BrowserUtils.enterTextCharacterByCharacterUsingJavaScript(Driver.getDriver(),From_txt,from);

        //Driver.getDriver().findElement(From_txt).sendKeys("HYD");
        Waits.waitFixedTime(2);
        BrowserUtils.findElement(Driver.getDriver(),suggestion,5,"click");
    }
    public void EnterToCity(String to){
        //Driver.getDriver().findElement(destination_btn).click();
        Waits.waitFixedTime(1);
        BrowserUtils.enterTextCharacterByCharacterUsingJavaScript(Driver.getDriver(),To_txt,to);
        //Driver.getDriver().findElement(To_txt).sendKeys("MAA");
        Waits.waitFixedTime(2);
        BrowserUtils.findElement(Driver.getDriver(),suggestion,5,"click");
    }

    public void SetDepartureDate() {
        BrowserUtils.findElement(Driver.getDriver(),departure,5,"click");
        Waits.waitFixedTime(2);
        BrowserUtils.clickWithJS(Driver.getDriver().findElement(departure_30day));
    }
    public void SelectTraveller() {
        BrowserUtils.clickWithJS(Driver.getDriver().findElement(travel_details));
        //BrowserUtils.findElement(Driver.getDriver(),travel_details,5,"click");
        Waits.waitFixedTime(2);
        BrowserUtils.findElement(Driver.getDriver(),traveller_done,5,"click");

    }
    public void SearchFlights() throws Exception {
        BrowserUtils.findElement(Driver.getDriver(),searchFlight_btn,5,"click");
        Waits.waitFixedTime(2);
        Waits.waitForElementToDisappear(loader_btn,20);

    }
}