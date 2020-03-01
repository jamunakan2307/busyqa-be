package com.busyqa.crm;

import com.busyqa.crm.UI.BaseTest;
import com.busyqa.crm.UI.HomePage;
import com.busyqa.crm.UI.LocationPage;
import com.busyqa.crm.UI.LoginPage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class LocationPageTest {
  WebDriver driver;

  @After
  public void close() {
    this.driver.close();
  }

  @Before
  public void setup() {
    System.setProperty(
        "webdriver.chrome.driver",
        "C:\\Users\\jamun\\OneDrive\\Documents\\RadSol\\src\\main\\resources\\chromedriver.exe");

    this.driver = new ChromeDriver();
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  public void restoredb() throws IOException, InterruptedException {
    BaseTest.restoreDB("busyqacrm", "root", "mysql", "C:\\class_Notes\\restoreDBbusyQA.sql");
  }

  @Test
  public void checkLocationPageAsAdmin() throws InterruptedException {
    LoginPage loginPage = new LoginPage(driver);
    loginPage.setUserName("administrator");
    loginPage.setPassword("password12");
    loginPage.setLoginButton();
    Thread.sleep(6000);
    HomePage homePage = new HomePage(driver);
    homePage.clickOnMenuButton();
    homePage.clickOnLocationMenuOption();
    LocationPage locationPage = new LocationPage(driver);
    LocationPage.AddingLocationPage addingLocationPage = new LocationPage.AddingLocationPage(driver);
    locationPage.clickToAddLocation();
    addingLocationPage.setLocationName("India");
    addingLocationPage.setLocationDescription("my coutry");
    addingLocationPage.setLocationAddress("233 D ambiga strt");
    addingLocationPage.setLocationCity("chennai");
    addingLocationPage.setLocationState("TamilNadu");
    addingLocationPage.setLocationCountry("India");
    addingLocationPage.setLocationZipCode("600107");
    addingLocationPage.clickToCreateLocation();


    int count = locationPage.checkTablelenth();
    System.out.println(+count);

  }
}