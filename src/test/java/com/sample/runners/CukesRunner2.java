package com.sample.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
//import org.testng.annotations.Test;


@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {
                "html:target/cucumber-report.html",
                "json:target/cucumber.json",
                "rerun:target/rerun.txt"
        },
        features = "src/test/resources/features",
        glue = "com/sample/step_definitions",
        dryRun = false,
        tags = "@test112"
)

public class CukesRunner2 {
//
////        @Test
////        public  void Tests(){
////
////        }
}
