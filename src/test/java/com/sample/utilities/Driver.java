package com.sample.utilities;

import com.sample.enums.ConfigProperties;
import io.cucumber.java.hu.De;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class Driver {


    private Driver() {
    }

    /*
    Making our 'driver' instance private so that it is not reachable from outside of the class.
    We make it static, because we want it to run before everything else, and also we will use it in a static method
     */
    private static ThreadLocal<WebDriver> driverPool = new ThreadLocal<>();
    private static ConfigFileReader configfileReader = new ConfigFileReader();

    /*
    Creating re-usable utility method that will return same 'driver' instance everytime we call it.
     */
    public static WebDriver getDriver() {

        if (driverPool.get() == null) {

            synchronized (Driver.class) {
        /*
        We read our browser type from configuration.properties file using
        .getProperty method we creating in ConfigurationReader class.
         */
                String browserType = configfileReader.getBrowser();
                String headless = configfileReader.getPropertyValue(ConfigProperties.headless.toString());
                String runMode = configfileReader.getPropertyValue(ConfigProperties.runMode.toString());
        /*
        Depending on the browser type our switch statement will determine
        to open specific type of browser/driver
         */
                if(runMode.equalsIgnoreCase("local")){
                    switch (browserType) {
                        case "chrome":
                            //WebDriverManager.chromedriver().setup();
                            ChromeOptions options = new ChromeOptions();
                            options.addArguments("--remote-allow-origins=*");
                            if(headless.equalsIgnoreCase("true")){
                                options.addArguments("--headless");
                                options.addArguments("start-maximized");
                                options.addArguments("--window-size=1920,1080");
                            }

                            driverPool.set(new ChromeDriver(options));
                            driverPool.get().manage().window().maximize();
                            driverPool.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
                            break;
                        case "firefox":
                            //WebDriverManager.firefoxdriver().setup();
                            driverPool.set(new FirefoxDriver());
                            driverPool.get().manage().window().maximize();
                            driverPool.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
                            break;
                    }
                }
                else{
                    switch (browserType) {
                        case "chrome":
                            //WebDriverManager.chromedriver().setup();
                            ChromeOptions options = new ChromeOptions();
                            options.addArguments("--remote-allow-origins=*");
                            if(headless.equalsIgnoreCase("true")){
                                options.addArguments("--headless");
                                options.addArguments("start-maximized");
                                options.addArguments("--window-size=1920,1080");
                            }
                            DesiredCapabilities cap = new DesiredCapabilities();
                            cap.setBrowserName("chrome");
                            cap.setCapability(ChromeOptions.CAPABILITY,options);
                            try{
                                driverPool.set(new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),cap));

                            }catch (Exception e){
                                throw new RuntimeException("Exception : "+e.getLocalizedMessage());
                            }
                            driverPool.get().manage().window().maximize();
                            break;
                        case "firefox":
                            DesiredCapabilities firefoxcap = new DesiredCapabilities();
                            FirefoxOptions firefoxOptions = new FirefoxOptions();
                            firefoxOptions.addArguments("--remote-allow-origins=*");
                            firefoxcap.setBrowserName("Firefox");
                            try{
                                driverPool.set(new RemoteWebDriver(new URL("http://localhost:4445/wd/hub"),firefoxcap));

                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            driverPool.get().manage().window().maximize();
                            break;
                    }

                }

            }
        }

    /*
    Same driver instance will be returned every time we call Driver.getDriver(); method
     */
        return driverPool.get();


    }

    /*
    This method makes sure we have some form of driver session or driver id has.
    Either null or not null it must exist.
     */
    public static void closeDriver() {
        if (driverPool.get() != null) {
            driverPool.get().quit();
            driverPool.remove();
        }
    }
}
