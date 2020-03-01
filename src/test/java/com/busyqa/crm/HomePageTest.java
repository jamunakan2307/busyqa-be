package com.busyqa.crm.UI;
import com.busyqa.crm.UI.LoginPage;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class HomePageTest {
  WebDriver driver;

  @Before
  public void setup() throws InterruptedException {
    System.setProperty(
        "webdriver.chrome.driver",
        "C:\\Users\\jamun\\OneDrive\\Documents\\RadSol\\src\\main\\resources\\chromedriver.exe");
    driver = new ChromeDriver();
  }

  @Test
  public void signInAsAdminforAdminPortal() {

    LoginPage loginPage = new LoginPage(driver);
    loginPage.setUserName("Administrator");
    loginPage.setPassword("password12");
    loginPage.setLoginButton();
    HomePage homePage = new HomePage(driver);
    homePage.clickOnMenuButton();
    homePage.clickOnMenuListOption(" text ");
    homePage.clickOnLogOutButton();


  }
  @After
    public void close(){
      driver.close();
  }
}