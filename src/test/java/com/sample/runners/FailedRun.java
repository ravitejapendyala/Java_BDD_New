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
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:","rerun:target/failedrerun.txt" } ,
        features = "src/test/resources/features",
        glue = "com/sample/step_definitions",
        dryRun = false,
        tags = "@orange"
)

public class FailedRun {
//
////        @Test
////        public  void Tests(){
////
////        }
}
