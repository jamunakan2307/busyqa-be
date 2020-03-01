package com.busyqa.crm.UI;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
  public static String URL = "http://localhost:4200";
  private WebDriver driver;

  @FindBy(
      how = How.CSS,
      using =
          "body > app-root > mat-sidenav-container > mat-sidenav-content > app-login > div > div > div > form > div:nth-child(1) > input")
  private WebElement userName;

  @FindBy(
      how = How.CSS,
      using =
          "body > app-root > mat-sidenav-container > mat-sidenav-content > app-login > div > div > div > form > div:nth-child(2) > input")
  private WebElement password;

  @FindBy(
      how = How.CSS,
      using =
          "body > app-root > mat-sidenav-container > mat-sidenav-content > app-login > div > div > div > form > div:nth-child(3) > button")
  private WebElement loginButton;

  @FindBy(
      how = How.CSS,
      using =
          "body > app-root > mat-sidenav-container > mat-sidenav-content > app-login > div > div > div > a")
  private WebElement signUpButton;

  public LoginPage(WebDriver driver) {
 this.driver = driver;
   driver.get(URL);
    PageFactory.initElements(driver, this);
  }



  public void setUserName(String usName) {
    userName.sendKeys(usName);
  }

  public void setPassword(String pwd) {
    password.sendKeys(pwd);
  }

  public void setLoginButton() {
    loginButton.click();
  }

  public void setSignUpButton() {
    signUpButton.click();
  }
}
