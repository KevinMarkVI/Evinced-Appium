package com.yourcompany;


import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.MobileCapabilityType;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.evinced.appium.sdk.core.EvincedAppiumSdk;


public class SauceLabs {

    public static URL url;
    public static IOSDriver<IOSElement> driver;
    public static EvincedAppiumSdk a11yValidator;
    public static String sauce_username = System.getenv("SAUCE_USERNAME");
    public static String sauce_accesskey = System.getenv("SAUCE_ACCESS_KEY");

    @BeforeClass
    public static void setupAppiumDriver() throws MalformedURLException, IOException {
        final String URL_STRING = "http://0.0.0.0:4723/wd/hub";
        url = new URL(URL_STRING);

        DesiredCapabilities capabilities = new DesiredCapabilities();
        // Sauce Labs Caps
         capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone 12.*");
         capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
         capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "15");
         capabilities.setCapability("automationName", "XCUITest");
         capabilities.setCapability(MobileCapabilityType.APP, "storage:STORAGE_ID");
         capabilities.setCapability("username", sauce_username);
         capabilities.setCapability("accesskey", sauce_accesskey);

        driver = new IOSDriver<IOSElement>(new URL("https://ondemand.us-west-1.saucelabs.com:443/wd/hub"), capabilities);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

        a11yValidator = new EvincedAppiumSdk(driver);
        a11yValidator.setupCredentials("SERVICE_ACCOUNT_ID", "API_KEY");
    }

    @AfterClass
    public static void tearDown() {
      a11yValidator.reportStored();
      driver.quit();
    }

    @Test
    public void testStationsScreenIsAccessible() throws InterruptedException {
        Thread.sleep(30000);
        //Scan for accessibility issues
        a11yValidator.analyze();
        IOSElement firstStationTableCell = driver.findElement(MobileBy.className("StationTableViewCell"));
        firstStationTableCell.click();
        //Scan for accessibility issues
        a11yValidator.analyze();
        IOSElement playButton = driver.findElement(MobileBy.className("XCUIElementTypeButton"));
        playButton.click();
        //Scan for accessibility issues
        a11yValidator.analyze();
    }
}



//mvn test -Dtest=SauceLabs













