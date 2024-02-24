package com.sample.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
//import org.testng.annotations.Test;


@RunWith(Cucumber.class)
@CucumberOptions(
//            plugin = {
//                    "html:target/cucumber-report.html",
//                    "json:target/cucumber.json",
//                    "rerun:target/rerun.txt"
//            },
        plugin = {
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:" } ,
        features = "@target/failedrerun.txt",
        glue = "com/sample/step_definitions",
        dryRun = false
)

public class FailedRun {
//
////        @Test
////        public  void Tests(){
////
////        }
}
