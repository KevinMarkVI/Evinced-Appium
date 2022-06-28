package com.yourcompany;

import com.evinced.appium.sdk.core.EvincedAppiumSdk;
import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class PerfectoAndroid {

    public static URL url;

    public static IOSDriver<IOSElement> driver;
    public static EvincedAppiumSdk a11yValidator;

    @BeforeClass
    public static void setupAppiumDriver() throws MalformedURLException, IOException {
        String securityToken;

        securityToken = "SECURITY_TOKEN";
        // Perfecto Media repository path

        //Mobile: Auto generate capabilities for device selection: https://developers.perfectomobile.com/display/PD/Select+a+device+for+manual+testing#Selectadeviceformanualtesting-genCapGeneratecapabilities
        String browserName = "mobileOS";
        DesiredCapabilities capabilities = new DesiredCapabilities("", "", Platform.ANY);
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("platformVersion", "15");
        capabilities.setCapability("location", "NA-US-BOS");
        capabilities.setCapability("resolution", "1440x3040");
        capabilities.setCapability("manufacturer", "Apple");
        capabilities.setCapability("model", "iPhone 13");
        capabilities.setCapability("app", "PUBLIC:MY_APP.ipa"); // Set Perfecto Media repository path of App under test.
        capabilities.setCapability("enableAppiumBehavior", true);
        capabilities.setCapability("openDeviceTimeout", 2); // Waits for 2 minutes before device connection timeout
        capabilities.setCapability("automationName", "XCUITest");
        capabilities.setCapability("securityToken", securityToken);


        driver = new IOSDriver<IOSElement>(new URL("https://YOUR_DOMAIN.perfectomobile.com/nexperience/perfectomobile/wd/hub"), capabilities);
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

//RUN With: mvn test -Dtest=PerfectoAndroid