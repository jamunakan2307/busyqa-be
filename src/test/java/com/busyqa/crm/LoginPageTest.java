package com.busyqa.crm;
import com.busyqa.crm.UI.BaseTest;
import com.busyqa.crm.UI.HomePage;
import com.busyqa.crm.UI.LoginPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

@RunWith(Parameterized.class)
public class LoginPageTest {

  WebDriver driver;
  private String username, password;

  public LoginPageTest(String username, String password) {
    this.username = username;
    this.password = password;
  }

  @Parameterized.Parameters
  public static Collection<Object[]> data() {
    return Arrays.asList(new Object[][] {{"administrator", "password12"}});
  }

  @Before
  public void setup() throws InterruptedException {
    System.setProperty(
        "webdriver.chrome.driver",
        "C:\\Users\\jamun\\OneDrive\\Documents\\RadSol\\src\\main\\resources\\chromedriver.exe");
    driver = new ChromeDriver();
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

    public void restoredb() throws IOException, InterruptedException {
        BaseTest.restoreDB("busyqacrm", "root", "mysql", "C:\\class_Notes\\restoreDBbusyQA.sql");
    }


     @Test
  public void signInAsAdmin() throws InterruptedException {

    LoginPage loginPage = new LoginPage(driver);
    loginPage.setUserName(username);
    loginPage.setPassword(password);
    loginPage.setLoginButton();
      }

  @Test
  public void signup() throws InterruptedException {

    LoginPage loginPage = new LoginPage(driver);
   loginPage.setSignUpButton();
  }

  @After
  public void closing() {
    driver.close();
  }
}
