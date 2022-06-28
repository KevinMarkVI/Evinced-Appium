package com.yourcompany;

import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.MobileCapabilityType;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.evinced.appium.sdk.core.EvincedAppiumSdk;
import com.evinced.appium.sdk.core.EvincedAppiumIOSDriver;
import com.evinced.appium.sdk.core.EvincedAppiumAndroidDriver;


    public class EvincedAppiumContinuous {

        public static URL url;

        public static IOSDriver driver;
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

            driver = new EvincedAppiumIOSDriver<IOSElement>(url, capabilities);
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            a11yValidator = new EvincedAppiumSdk(driver);
            //Authentication
            a11yValidator.setupCredentials("SERVICE_ACCOUNT_ID", "API_KEY");

            //Start Evinced
            a11yValidator.startAnalyze();
        }


        @AfterClass
        public static void tearDown() {
            a11yValidator.stopAnalyze();
            driver.quit();
        }

        @Test
        public void testStationsScreenIsAccessible() throws InterruptedException {
        driver.findElement(MobileBy.AccessibilityId("tab_my_app")).click();
        driver.findElement(MobileBy.xpath("//XCUIElementTypeButton[@name=\"tab_search\"]")).click();
        driver.findElement(MobileBy.AccessibilityId("Cancel")).click();
        driver.findElement(MobileBy.AccessibilityId("tab_notifications")).click();
        driver.findElement(MobileBy.xpath("//XCUIElementTypeButton[@name=\"tab_selling\"]")).click();
        }
    }



//RUN With: mvn test -Dtest=EvincedAppiumContinuous


