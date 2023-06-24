package com.sample.utilities;

import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

import static org.junit.Assert.assertTrue;

public class BrowserUtils {

    public void navigateTo (String navigationUrl){
        Driver.getDriver().get(navigationUrl);
    }
    public static void clickByElement (WebElement elementToClick){
        Waits.waitForClickability(elementToClick,30);
        elementToClick.click();
    }

    public void clickByElement(WebDriver driver, WebElement elementToClick) {
        Waits.waitForClickability(driver, elementToClick, 10);
        elementToClick.click();
    }

    public void performActionsClick(WebElement elementToClick) {
        Waits.waitForClickability(elementToClick, 30);
        Actions act = new Actions(Driver.getDriver());
        act.moveToElement(elementToClick).click().build().perform();
    }

    public void click(WebElement element) {
        Waits.waitForClickability(element,30);
        element.click();
    }

    public void type(WebElement element, String data) {
        Waits.waitForVisibility(element,30);
        element.clear();
        element.sendKeys(data);
    }

    public void actionsType(WebElement element, String data) {
        Actions act = new Actions(Driver.getDriver());
        act.moveToElement(element)
                .sendKeys(data).build().perform();
    }

    public void type(WebDriver driver, WebElement element, String data) {
        Waits.waitForVisibility(driver, element, 30);
        element.clear();
        element.sendKeys(data);
    }

    public String getText(WebElement element) {
        Waits.waitForVisibility(element, 30);

        return element.getText();
    }

    public static void selectByVisibleText(WebElement element, String text) {
        Waits.waitForVisibility(element, 30);
        Select dropDown = new Select(element);
        dropDown.selectByVisibleText(text);
    }

    public boolean isElementVisible(WebElement element) {
        try {
            Waits.waitForVisibility(element, 20);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * This method accepts String expected title
     *
     * @param expectedTitle
     */
    public static void assertTitle(String expectedTitle, int timeout) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeout));
        wait.until(ExpectedConditions.titleIs(expectedTitle));
        String actualTitle = Driver.getDriver().getTitle();
        Assert.assertEquals(expectedTitle, actualTitle);
    }

    /**
     * This method accepts a List<WebElements> and returns List<String>
     *
     * @param webElementList
     */
    public static List<String> getElementsText(List<WebElement> webElementList) {
        //Create placeholder List<String>
        List<String> actualAsString = new ArrayList<>();
        for (WebElement each : webElementList) {
            actualAsString.add(each.getText());
        }
        return actualAsString;
    }

    /*
     * switches to new window by the exact title
     * returns to original window if windows with given title not found
     */
    public static void switchToWindow(String targetTitle) {
        String origin = Driver.getDriver().getWindowHandle();
        for (String handle : Driver.getDriver().getWindowHandles()) {
            Driver.getDriver().switchTo().window(handle);
            if (Driver.getDriver().getTitle().equals(targetTitle)) {
                return;
            }
        }
        Driver.getDriver().switchTo().window(origin);
    }

    public String getTitle() {
        return Driver.getDriver().getTitle();
    }

    public void switchToFrame(WebElement element) {
        Waits.waitForVisibility(element, 120);
        Driver.getDriver().switchTo().frame(element);
    }

    public void switchToDefaultContent() {

        Driver.getDriver().switchTo().defaultContent();
    }

    public static void hover(WebElement element) {
        Actions actions = new Actions(Driver.getDriver());
        actions.moveToElement(element).perform();
    }

    public void waitUntilLoadingDisappears() {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(120));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(".//div[@class='block-ui-message ng-binding'][text()='Loading...']")));
    }

    public void waitUntilLoadingDisappears(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(".//div[@class='block-ui-message ng-binding'][text()='Loading...']")));
    }

    /**
     * Verifies whether the element matching the provided locator is displayed on page
     * fails if the element matching the provided locator is not found or not displayed
     *
     * @param by
     */
    public static void verifyElementDisplayed(By by) {
        try {
            assertTrue("Element not visible: " + by, Driver.getDriver().findElement(by).isDisplayed());
        } catch (NoSuchElementException e) {
            Assert.fail("Element not found: " + by);
        }
    }

    public String getElementText(WebElement element) {
        Waits.waitForVisibility(element, 10);
        return element.getText();
    }

    /**
     * Verifies whether the element is displayed on page
     * fails if the element is not found or not displayed
     *
     * @param element
     */
    public static void verifyElementDisplayed(WebElement element) {
        try {
            assertTrue("Element not visible: " + element, element.isDisplayed());
        } catch (NoSuchElementException e) {
            Assert.fail("Element not found: " + element);
        }
    }

    /**
     * Waits for element to not be stale
     *
     * @param element
     */
    public void waitForStaleElement(WebElement element) {
        int y = 0;
        while (y <= 15) {
            if (y == 1)
                try {
                    element.isDisplayed();
                    break;
                } catch (StaleElementReferenceException st) {
                    y++;
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } catch (WebDriverException we) {
                    y++;
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        }
    }

    /**
     * Selects a random value from a dropdown list and returns the selected Web Element
     *
     * @param select
     * @return
     */
    public WebElement selectRandomTextFromDropdown(Select select) {
        Random random = new Random();
        List<WebElement> weblist = select.getOptions();
        int optionIndex = 1 + random.nextInt(weblist.size() - 1);
        select.selectByIndex(optionIndex);
        return select.getFirstSelectedOption();
    }

    /**
     * Clicks on an element using JavaScript
     *
     * @param element
     */
    public static void clickWithJS(WebElement element) {
        ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
        ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].click();", element);
    }

    /**
     * Scrolls down to an element using JavaScript
     *
     * @param element
     */
    public static void scrollToElement(WebElement element) {
        ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    /**
     * Performs double click action on an element
     *
     * @param element
     */
    public void doubleClick(WebElement element) {
        new Actions(Driver.getDriver()).doubleClick(element).build().perform();
    }

    /**
     * Changes the HTML attribute of a Web Element to the given value using JavaScript
     *
     * @param element
     * @param attributeName
     * @param attributeValue
     */
    public void setAttribute(WebElement element, String attributeName, String attributeValue) {
        ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);", element, attributeName, attributeValue);
    }

    public static void scrollDownToWindow() {
        JavascriptExecutor jse = (JavascriptExecutor) Driver.getDriver();
        jse.executeScript("window.scrollBy(0,500)");
    }

    public static void scrollDownToWindow(int count) {
        JavascriptExecutor jse = (JavascriptExecutor) Driver.getDriver();
        for (int i = 1; i <= count; i++) {
            Waits.waitFixedTime(2);
            jse.executeScript("window.scrollBy(0,500)");
        }
    }

    public static void scrollUpToWindow(int count) {
        JavascriptExecutor jse = (JavascriptExecutor) Driver.getDriver();
        for (int i = 1; i <= count; i++) {
            Waits.waitFixedTime(2);
            jse.executeScript("window.scrollBy(0,-500)");
        }
    }

    /**
     * @param element
     * @param check
     */
    public static void selectCheckBox(WebElement element, boolean check) {
        if (check) {
            if (!element.isSelected()) {
                element.click();
            }
        } else {
            if (element.isSelected()) {
                element.click();
            }
        }
    }

    public static void sleep(int second) {
        second *= 1000;
        try {
            Thread.sleep(second);
        } catch (InterruptedException e) {
            System.out.println("something happened in the sleep method");
        }
    }

    public void performMouseHover(WebElement element) {
        Actions act = new Actions(Driver.getDriver());
        act.moveToElement(element).build().perform();
    }

    public static String getAttribute(WebElement element, String attributeName) {
        return element.getAttribute(attributeName);
    }

    public static void waitBeforeExecution(int timeout) {
        Waits.waitForPageToLoad(timeout);
        BrowserUtils.sleep(3);
    }

    public static void assertTitleContains(String expectedTitle, int timeout) {
        Boolean titleContains;
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeout));
        wait.until(ExpectedConditions.titleContains(expectedTitle));
        String actualTitle = Driver.getDriver().getTitle();
        if (actualTitle.contains(expectedTitle)) {
            titleContains = true;
        } else {
            titleContains = false;
        }
        Assert.assertTrue(titleContains);
    }

    public boolean VerifyElementExists(String xpath) {
        //Waits.waitForVisibility(element,10);
        return (Driver.getDriver().findElements((By.xpath(xpath))).size() >= 1);
    }

    public void SwitchToNewTab() {
        ArrayList<String> tabs = new ArrayList<String>(Driver.getDriver().getWindowHandles());
        Driver.getDriver().switchTo().window(tabs.get(tabs.size() - 1));
    }
    public static boolean isElementPresent(WebElement element){
        Waits.waitForPageToLoad(10);
        return element.isDisplayed();
    }

    public static void clickByElement (String xpath){
        WebElement element  = Driver.getDriver().findElement(By.xpath(xpath));
        Waits.waitForClickability(element,30);
        element.click();
    }
    public static  void ClickBack(){
        Driver.getDriver().navigate().back();
    }

}