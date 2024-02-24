package com.sample.step_definitions;

import com.aventstack.extentreports.ExtentTest;
import com.sample.context.TestContext;
import com.sample.utilities.BrowserUtils;
import com.sample.utilities.Driver;
import com.sample.utilities.Waits;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;

public class Steps {

//    BrowserUtils browserutils = new BrowserUtils();
//    Waits waits = new Waits();
    BrowserUtils browserutils;
    Waits waits ;
    String dob;
    String[] parts;
    int DateToUpdate;
    int UpdatedDate;
    TestContext testContext;
    //Hooks hooks;

    public Steps(TestContext context){
        dob="";
        DateToUpdate=0;
        UpdatedDate=0;
        browserutils = new BrowserUtils();
        waits = new Waits();
        this.testContext = context;
        //hooks = new Hooks();
    }

    @Given("I launch payment gateway")
    public void i_launch_payment_gateway() {
        // Write code here that turns the phrase above into concrete actions
        Driver.getDriver().get("https://demo.guru99.com/payment-gateway/index.php");

        WebElement generate_card_number = Driver.getDriver().findElement(By.linkText("Generate Card Number"));
        generate_card_number.click();
        ArrayList<String> tabs = new ArrayList<String>(Driver.getDriver().getWindowHandles());
        Driver.getDriver().switchTo().window(tabs.get(1));

        WebElement card_nmber = Driver.getDriver().findElement(By.xpath("//h4[contains(text(),'Card Number')]"));
        String CN = card_nmber.getText();
        CN = CN.replaceAll("[^0-9]","");
        System.out.println("Card number is : "+CN);

        WebElement cvv = Driver.getDriver().findElement(By.xpath("//h4[contains(text(),'CVV')]"));
        String CVV = cvv.getText();
        CVV = CVV.replaceAll("[^0-9]","");
        System.out.println("CVV is : "+CVV);

        WebElement exp = Driver.getDriver().findElement(By.xpath("//h4[contains(text(),'Exp')]"));
        String EXP = exp.getText();
        EXP = EXP.replaceAll("[^0-9]","");
        String month = EXP.substring(0,2);
        String Year = EXP.substring(2,6);
        System.out.println("Expiry Date is : "+EXP);

        WebElement cardAmount = Driver.getDriver().findElement(By.xpath("//h4"));
        String CardAm = cardAmount.getText();
        CardAm = CardAm.replaceAll("[^0-9]","");
        System.out.println("Card Amount is : "+CardAm);


        Driver.getDriver().switchTo().window(tabs.get(0));
        SelectDropDown("//select[@name='quantity']","6");

        //WebElement qnty = Driver.getDriver().findElement(By.xpath("//select[@name='quantity']"));
        //Select qnty_element = new Select(qnty);
        //qnty_element.selectByValue("6");

        WebElement buyNow_btn = Driver.getDriver().findElement(By.xpath("//input[@type='submit']"));
        buyNow_btn.click();
    }
    @Then("I generate card details")
    public void i_generate_card_details() {
        // Write code here that turns the phrase above into concrete actions
        //throw new io.cucumber.java.PendingException();
    }
    @When("I buy the toys by selecting quantity")
    public void i_buy_the_toys_by_selecting_quantity() {
        // Write code here that turns the phrase above into concrete actions
        //throw new io.cucumber.java.PendingException();
    }

    public void SelectDropDown(String xpath,String value)
    {
        WebElement month_drdn = Driver.getDriver().findElement(By.xpath(xpath));
        Select month_drdn_element = new Select(month_drdn);
        month_drdn_element.selectByVisibleText(value);
    }

    @Given("I automate orange app")
    public void iAutomateOrangeApp() {

        browserutils.navigateTo("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
        browserutils.type(Driver.getDriver().findElement(By.name("username")),"Admin");
        browserutils.type(Driver.getDriver().findElement(By.name("password")),"admin123");

        Waits.waitFixedTime(3);
        browserutils.clickWithJS(Driver.getDriver().findElement(By.xpath("//button[@type='submit']")));
        Waits.waitFixedTime(3);
        browserutils.VerifyElementExists("//h6[text()='Dashboard']");
        browserutils.clickWithJS(Driver.getDriver().findElement(By.xpath("//span[text()='My Info']")));
        //Waits.waitFixedTime(3);
        waits.waitForPageLoad(Driver.getDriver(),120);

        String dob = BrowserUtils.getAttribute(Driver.getDriver().findElement(By.xpath("(//div[@class='oxd-date-input']/input)[2]")),"value");
        int counter=5;
        do{

            if(dob.isEmpty()|| dob.isBlank()){
                Waits.waitFixedTime(2);
                dob = BrowserUtils.getAttribute(Driver.getDriver().findElement(By.xpath("(//div[@class='oxd-date-input']/input)[2]")),"value");
            }
            counter--;
        }while(dob==""&&counter>1);
        Assert.assertTrue("Verify Date of birth is not blank",dob.contains("-"));
        browserutils.clickWithJS(Driver.getDriver().findElement(By.xpath("(//i[@class='oxd-icon bi-calendar oxd-date-input-icon'])[2]")));
        Waits.waitFixedTime(2);
        String[] parts = dob.split("-");
        int DateToUpdate = Integer.parseInt(parts[2])+1;
        if(DateToUpdate>29){DateToUpdate--;}
        browserutils.clickWithJS(Driver.getDriver().findElement(By.xpath("(//div[@class='oxd-calendar-date'])["+(DateToUpdate-1)+"]")));
        //Waits.waitFixedTime(3);
        browserutils.clickWithJS(Driver.getDriver().findElement(By.xpath("//button[@type='submit']")));
        Waits.waitFixedTime(2);
        dob = BrowserUtils.getAttribute(Driver.getDriver().findElement(By.xpath("(//div[@class='oxd-date-input']/input)[2]")),"value");
        parts = dob.split("-");
        int UpdatedDate = Integer.parseInt(parts[2]);
        Assert.assertTrue("Verify Updated date is reflected :",UpdatedDate== DateToUpdate);
    }

    @Given("I login to Orange HRM app")
    public void iLoginToOrangeHRMApp() {
        browserutils.navigateTo("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
        browserutils.type(Driver.getDriver().findElement(By.name("username")),"Admin");
        browserutils.type(Driver.getDriver().findElement(By.name("password")),"admin123");
        browserutils.clickWithJS(Driver.getDriver().findElement(By.xpath("//button[@type='submit']")));

        //Waits.waitFixedTime(3);

    }

    @Then("Dashboard should be launched by default")
    public void dashboardShouldBeLaunchedByDefault() {
        Assert.assertTrue("Validate Dashboard is displayed by default after login",browserutils.VerifyElementExists("//h6[text()='Dashboard']"));
        Hooks.scenario.log("Success: Validate Dashboard is displayed by default after login");
//        Hooks.scenario.log(Status.PASSED, MarkupHelper.createLabel("Assertion Pass:", ExtentColor.GREEN));


    }

    @When("I navigate to My Info section")
    public void iNavigateToMyInfoSection() {
        browserutils.clickWithJS(Driver.getDriver().findElement(By.xpath("//span[text()='My Info']")));
    }

    @Then("Date of birth details should be displayed")
    public void dateOfBirthDetailsShouldBeDisplayed() {

        waits.waitForPageLoad(Driver.getDriver(),120);
        Waits.waitForVisibility(By.xpath("//label[text()='Date of Birth']"),20);
        dob = BrowserUtils.getAttribute(Driver.getDriver().findElement(By.xpath("(//div[@class='oxd-date-input']/input)[2]")),"value");
        int counter=5;
        do{

            if(dob.isEmpty()|| dob.isBlank()){
                Waits.waitFixedTime(2);
                dob = BrowserUtils.getAttribute(Driver.getDriver().findElement(By.xpath("(//div[@class='oxd-date-input']/input)[2]")),"value");
            }
            counter--;
        }while(dob==""&&counter>1);
        Assert.assertTrue("Verify Date of birth is not blank",dob.contains("-"));
        browserutils.clickWithJS(Driver.getDriver().findElement(By.xpath("(//i[@class='oxd-icon bi-calendar oxd-date-input-icon'])[2]")));
        Waits.waitFixedTime(2);
        parts = dob.split("-");
        DateToUpdate = Integer.parseInt(parts[1])+1;
        if(DateToUpdate>29){DateToUpdate--;}

    }

    @When("I Update Date of birth details")
    public void iUpdateDateOfBirthDetails() {
        browserutils.clickWithJS(Driver.getDriver().findElement(By.xpath("(//div[@class='oxd-calendar-date'])["+(DateToUpdate-1)+"]")));
        Hooks.takeScraenshot(Hooks.scenario);
        //Waits.waitFixedTime(3);
        Waits.waitForVisibility(By.xpath("//button[@type='submit']"),20);
        browserutils.clickWithJS(Driver.getDriver().findElement(By.xpath("//button[@type='submit']")));
        Hooks.takeScraenshot(Hooks.scenario);

    }

    @Then("My Info page should be updated with latest selected date of birth")
    public void myInfoPageShouldBeUpdatedWithLatestSelectedDateOfBirth() {
        Waits.waitFixedTime(2);
        dob = BrowserUtils.getAttribute(Driver.getDriver().findElement(By.xpath("(//div[@class='oxd-date-input']/input)[2]")),"value");
        parts = dob.split("-");
        int UpdatedDate = Integer.parseInt(parts[1]);
        Assert.assertTrue("Verify Updated date is reflected :",UpdatedDate== DateToUpdate);
        Hooks.scenario.log("Success: Date of Birth updated");

    }

    @When("I logout from the app")
    public void iLogoutFromTheApp() {
        browserutils.clickWithJS(Driver.getDriver().findElement(By.xpath("//i[contains(@class,'userdropdown-icon')]")));
        Waits.waitForVisibility(By.xpath("//a[text()='Logout']"),20);
        browserutils.clickWithJS(Driver.getDriver().findElement(By.xpath("//a[text()='Logout']")));



    }

    @Then("Login page should be displayed")
    public void loginPageShouldBeDisplayed() {
        Assert.assertTrue("Validate login page is displayed after logout ",browserutils.VerifyElementExists("//input[@name='username']"));
        Hooks.scenario.log("Success: Login page displayed after Logout operation");
    }

    @Given("I login to Orange HRM app with invalid details")
    public void iLoginToOrangeHRMAppWithInvalidDetails() {
        browserutils.navigateTo("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
        browserutils.type(Driver.getDriver().findElement(By.name("username")),"Admin12");
        browserutils.type(Driver.getDriver().findElement(By.name("password")),"admin123");
        browserutils.clickWithJS(Driver.getDriver().findElement(By.xpath("//button[@type='submit']")));
    }
}
