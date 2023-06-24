package com.sample.step_definitions;

import com.sample.utilities.Driver;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;

public class Steps {

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
}
