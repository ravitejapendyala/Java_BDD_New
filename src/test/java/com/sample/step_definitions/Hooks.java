package com.sample.step_definitions;

import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import com.sample.utilities.Driver;

public class Hooks {
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
}
