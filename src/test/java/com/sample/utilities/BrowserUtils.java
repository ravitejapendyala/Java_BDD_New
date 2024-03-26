package com.sample.utilities;

import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.*;
import java.util.NoSuchElementException;
import java.util.function.Function;

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


    // New Methods from WebUtilities class
    public static void scrollElementIntoView(final WebDriver driver, WebElement element, boolean top) {
        if (element != null) {
            JavascriptExecutor jse = (JavascriptExecutor) driver;
            String topView = "";
            if (top) {
                topView = "true";
            }
            try {
                jse.executeScript("arguments[0].scrollIntoView(" + topView + ")", element);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * This method locates an element
     *
     * @param driver
     * @param locator
     * @param timeoutSeconds
     * @return
     * @throws CustomException
     */


    public static WebElement findElement(final WebDriver driver, final By locator, final int timeoutSeconds, String click)  {

        FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver).
                withTimeout(Duration.ofSeconds(timeoutSeconds / 5)).withMessage("Not able to locate element WEB_element " + locator).
                pollingEvery(Duration.ofMillis(500)).ignoring(NoSuchElementException.class);

        WebElement element = null;
        for (int retries = 5; retries > 0; retries--) {
            try {
                if (click.equals("click")) {
                    element = wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(locator)));
                    highLightElement(driver, element);
                    element.click();
                } else {
                    element = wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfElementLocated(locator)));
                    highLightElement(driver, element);
                }
                break;
            } catch (Exception e) {
            }
        }

        return element;

    }

    //Method Overloading
    public static WebElement findElement(final WebDriver driver, final By locator, final int timeoutSeconds){

        return findElement(driver, locator, timeoutSeconds, "find");
    }


    /**
     * @param driver
     * @param locator
     * @param timeoutSeconds
     * @return
     * @throws CustomException
     */

    public static List<WebElement> findElements(WebDriver driver, By locator, final int timeoutSeconds) throws CustomException {

        FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeoutSeconds)).withMessage("Not able to locate element " + locator).pollingEvery(Duration.ofMillis(500)).ignoring(NoSuchElementException.class);

        WebElement element = wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver webDriver) {
                return driver.findElement(locator);
            }

        });
        if (element.isDisplayed()) {
            List<WebElement> elementsList = (List<WebElement>) driver.findElements(locator);
            return elementsList;
        }
        throw new CustomException("Elements " + locator + " is not visible");

    }

    /**
     * @param driver
     * @param locator
     * @return
     */
    public static boolean isElementPresent(final WebDriver driver, By locator) {
        try {
            driver.findElement(locator);
            System.out.println("LOG : " + locator.toString() + " PRESENT");
            return true;
        } catch (Exception ex) {
            System.out.println("LOG : " + locator.toString() + " NOT PRESENT");
            //ex.printStackTrace();
            return false;
        }
    }


    public static void switchToChildWindow(WebDriver webDriver, int index) {
        webDriver.switchTo().window(new ArrayList<String>(webDriver.getWindowHandles()).get(index));
    }

    public static void scrollDown(WebDriver webDriver) {
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        js.executeScript("window.scrollBy(0,3750)", "");
    }


    public static boolean isElementVisible(WebDriver driver, By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.ignoring(NoSuchElementException.class);
        wait.pollingEvery(Duration.ofMillis(500));

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            System.out.println("LOG : " + locator.toString() + "  VISIBLE");
        } catch (Exception e) {
            System.out.println("LOG : " + locator.toString() + " NOT VISIBLE");
            return false;

        }
        return true;
    }

    public static boolean isElementNotVisible(WebDriver driver, By locator) {
        // WebUtilities.ExplicitWait(3000);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(120));
        wait.ignoring(NoSuchElementException.class);
        wait.pollingEvery(Duration.ofMillis(500));

        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
            System.out.println("LOG : " + locator.toString() + "Element is Not Visible : Expected");
        } catch (Exception e) {
            System.out.println("LOG : " + locator.toString() + " Element is VISIBLE: Not Expected");
            return false;

        }
        return true;
    }

    public static void highLightElement(WebDriver driver, WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String style = element.getAttribute("style");

        js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 3px solid red;');", element);

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {

            System.out.println(e.getMessage());
        }
        js.executeScript("arguments[0].setAttribute('style',arguments[1]);", element, style);

    }

    static String parent_tab;
    static WebDriver webDriver;

    public static void switchToChildWindow(WebDriver driver) {
        webDriver = driver;
        parent_tab = webDriver.getWindowHandle();
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(120));
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));
        Set<String> s1 = webDriver.getWindowHandles();
        Iterator<String> i1 = s1.iterator();
        while (i1.hasNext()) {
            String child_tab = i1.next();
            if (!parent_tab.equalsIgnoreCase(child_tab)) {
                webDriver.switchTo().window(child_tab);
            }
        }
    }

    public static List<WebElement> findElements(WebDriver webDriver, By locator) {
        List<WebElement> elements = webDriver.findElements(locator);
        return elements;
    }

    public static void switchToParentTab() {
        webDriver.close();
        webDriver.switchTo().window(parent_tab);
    }

    public static void openNewEmtpyTab(WebDriver driver) {
        webDriver = driver;
        parent_tab = webDriver.getWindowHandle();
        driver.switchTo().newWindow(WindowType.TAB);
    }

    public static String getCurrentWindow(WebDriver driver) {
        return driver.getWindowHandle();
    }

    public static void switchToWindow(String window, WebDriver driver) {
        driver.switchTo().window(window);
    }

    public static void quitDriver(WebDriver driver) {
        webDriver.quit();
    }

    public void CloseAddIfExists() {
        if (!Driver.getDriver().findElements(By.xpath("//span[contains(text(),'CONTINUE SHOPPING')]")).isEmpty()) {
            try {
                clickWithJS(Driver.getDriver().findElement(By.xpath("")));
            } catch (Exception ex) {
                System.out.println("Into Exception clause ... Continue Shopping  add  doesn't exist");
            }

        } else {
            System.out.println("Continue Shopping  add  doesn't exist");
        }

    }

    private static void closeMultipleTabsExceptCurrentTab() throws InterruptedException {
        // Get all open tabs
        Set<String> allTabs = Driver.getDriver().getWindowHandles();

        // Get Current tab
        String currentTab = Driver.getDriver().getWindowHandle();

        Iterator<String> iterator = allTabs.iterator();

        while(iterator.hasNext()) {
            // Condition to check if the selected tab is not current tab
            String selectedTab = iterator.next();
            if(!selectedTab.equals(currentTab)) {
                // Switch to new tab
                Driver.getDriver().switchTo().window(selectedTab);

                // Print title of tabs to be closed
                System.out.println("Closing Tab = "+Driver.getDriver().getTitle());

                // Close the selected tab
                Driver.getDriver().close();

                // Time delay
                Waits.waitFixedTime(1);
            }
            else{
                Driver.getDriver().switchTo().window(selectedTab);
            }
        }
    }

    public static void enterTextUsingJavaScript(WebDriver driver, By elementXpath, String text) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        WebElement textField = driver.findElement(elementXpath);
        jsExecutor.executeScript("arguments[0].value='" + text + "'", textField);
    }
    public static void enterTextCharacterByCharacterUsingJavaScript(WebDriver driver, By elementXpath, String textToEnter) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        WebElement textField = driver.findElement(elementXpath);
        for (int i = 0; i < textToEnter.length(); i++) {
            char character = textToEnter.charAt(i);
            String charToString = String.valueOf(character);
            //((JavascriptExecutor) driver).executeScript("arguments[0].value += arguments[1]", inputElement, charToString);
            jsExecutor.executeScript("arguments[0].value += arguments[1]", textField,charToString);
            // Sleep for a small duration to mimic human typing speed
            Waits.waitFixedTime(1);
        }
        Waits.waitFixedTime(1);
        textField.sendKeys(Keys.BACK_SPACE);
        /*jsExecutor.executeScript(
                "var value = arguments[0].value; arguments[0].value = value.substring(0, value.length - 1);",
                textField
        );// Adjust the duration as needed*/
        Waits.waitFixedTime(1);
    }


}