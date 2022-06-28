package com.yourcompany;

import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import com.evinced.appium.sdk.core.EvincedAppiumSdk;


public class EvincedAppium {

    public static URL url;
    public static IOSDriver<IOSElement> driver;
    public static EvincedAppiumSdk a11yValidator;

    @BeforeClass
    public static void setupAppiumDriver() throws MalformedURLException, IOException {
        final String URL_STRING = "http://0.0.0.0:4723/wd/hub";
        url = new URL(URL_STRING);

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName","iPhone");
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("platformVersion", "15.4");
        capabilities.setCapability("app", "/Path/To/myApp.ipa");
        capabilities.setCapability("bundleId", "com.myApp");
        capabilities.setCapability("automationName", "XCUITest");
        capabilities.setCapability("xcodeOrgId", "TEAM_ID");
        capabilities.setCapability("udid","DEVICE_UDID");
        capabilities.setCapability("xcodeSigningId", "iPhone Developer");


        driver = new IOSDriver<IOSElement>(url, capabilities);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        a11yValidator = new EvincedAppiumSdk(driver);
        a11yValidator.setupCredentials("SERVICE_ACCOUNT_ID", "API_KEY");
    }

    @AfterClass
    public static void tearDown() {
        //Create HTML and JSON report and fail the test if accessibility issues are discovered
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


//mvn test -Dtest=EvincedAppium



