package com.sample.step_definitions;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.sample.context.TestContext;
import io.cucumber.java.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import com.sample.utilities.Driver;
import org.testng.annotations.BeforeTest;

public class Hooks {
    TestContext testContext;
    static Scenario scenario;
    ExtentReports extent;
    ExtentTest test;

    @After
    public void tearDownScenario(Scenario scenario){

        //IF MY SCENARIO FAILS
        // TAKE A SCREENSHOT
        //scenario.isFailed() --> if scenario fails : returns true
//        if (scenario.isFailed()){
//
//            byte [] screenshot = ((TakesScreenshot) Driver.getDriver()).getScreenshotAs(OutputType.BYTES);
//            scenario.attach(screenshot, "image/png", scenario.getName());
//        }
            byte [] screenshot = ((TakesScreenshot) Driver.getDriver()).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", scenario.getName());
            Driver.closeDriver();
    }

    //@After(order = 1)
//    @AfterStep
    public static void takeScraenshot(Scenario scenario) {

            TakesScreenshot ts = (TakesScreenshot) Driver.getDriver();
            byte[] src = ts.getScreenshotAs(OutputType.BYTES);
            scenario.attach(src, "image/png", "");


    }
    @Before
    public  void before(Scenario scenario){
        this.scenario = scenario;
        extent = new ExtentReports();
    }




}
