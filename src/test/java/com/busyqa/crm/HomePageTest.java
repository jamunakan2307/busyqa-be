package com.busyqa.crm;

import com.busyqa.crm.UI.BaseTest;
import com.busyqa.crm.UI.HomePage;
import com.busyqa.crm.UI.LoginPage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class HomePageTest {
  WebDriver driver;

  @After
  public void close(){
    this.driver.close();
  }

  @Before
  public void setup(){
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
  public void signInAsAdminforAdminPortal() throws InterruptedException {
    LoginPage loginPage = new LoginPage(driver);
    loginPage.setUserName("Administrator");
    loginPage.setPassword("password12");
    loginPage.setLoginButton();
    Thread.sleep(6000);
    HomePage homePage = new HomePage(driver);
    homePage.clickOnMenuButton();
    Assert.assertEquals("Leads" , homePage.homePageLabelCheck(2));
    Assert.assertEquals("Students" , homePage.homePageLabelCheck(3));
    Assert.assertEquals("Intern" , homePage.homePageLabelCheck(4));
    Assert.assertEquals("Resume" , homePage.homePageLabelCheck(5));
    Assert.assertEquals("Mock" , homePage.homePageLabelCheck(6));
    Assert.assertEquals("Alumini" , homePage.homePageLabelCheck(7));
   //homePage.clickOnMenuListOption(" text ");
    homePage.clickOnLogOutButton();
  }


 }
