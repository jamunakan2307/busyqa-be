package com.busyqa.crm.UI;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

@RunWith(Parameterized.class)
public class LoginPageTest {

  private WebDriver driver;
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
System.setProperty("webdriver.chrome.driver",
        "C:\\bts\\btsE\\selenium\\src\\main\\resources\\chromedriver.exe");
    driver = new ChromeDriver();
  driver.manage().wait(1000L);

}
@Test
public void signInAsAdmin(){

    LoginPage loginPage = new LoginPage(driver);
    loginPage.setUserName(username);
    loginPage.setPassword(password);
    loginPage.setLoginButton();

}
@After
  public void closing(){
    driver.close();
}

}
