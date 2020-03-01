package com.busyqa.crm.UI;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
  private WebDriver driver;


  @FindBy(
      how = How.XPATH,
      using =
          "/html/body/app-root/mat-sidenav-container/mat-sidenav-content/mat-toolbar/button[1]")
  private WebElement menuButton;

  @FindBy(how = How.CSS, using = "#appToolbar > button:nth-child(3)")
  private WebElement logOutButton;

  // Client Panel
  @FindBy(
      how = How.CSS,
      using =
          "body > app-root > mat-sidenav-container > mat-sidenav > div > mat-nav-list > a:nth-child(2) > div > div.mat-list-text > span\n")
  private WebElement leadClientStageOption;

  @FindBy(
      how = How.CSS,
      using =
          "body > app-root > mat-sidenav-container > mat-sidenav > div > mat-nav-list > a:nth-child(3) > div > div.mat-list-text > span")
  private WebElement studentClientStageOption;

  @FindBy(
      how = How.CSS,
      using =
          "body > app-root > mat-sidenav-container > mat-sidenav > div > mat-nav-list > a:nth-child(4) > div > div.mat-list-text > span")
  private WebElement internClientStageOption;

  @FindBy(
      how = How.CSS,
      using =
          "body > app-root > mat-sidenav-container > mat-sidenav > div > mat-nav-list > a:nth-child(5) > div > div.mat-list-text > span")
  private WebElement resumeClientStageOption;

  @FindBy(
      how = How.CSS,
      using =
          "body > app-root > mat-sidenav-container > mat-sidenav > div > mat-nav-list > a:nth-child(6) > div > div.mat-list-text > span")
  private WebElement mockClientStageOption;

  @FindBy(
      how = How.CSS,
      using =
          "body > app-root > mat-sidenav-container > mat-sidenav > div > mat-nav-list > a:nth-child(7) > div > div.mat-list-text > span")
  private WebElement aluminiClientStageOption;

  // AdminOffice  Panel
  @FindBy(
      how = How.CSS,
      using =
          "body > app-root > mat-sidenav-container > mat-sidenav > div > mat-nav-list > a:nth-child(9) > div > div.mat-list-text > span")
  private WebElement meAdminMenuOption;

  @FindBy(
      how = How.CSS,
      using =
          "body > app-root > mat-sidenav-container > mat-sidenav > div > mat-nav-list > a:nth-child(10) > div > div.mat-list-text > span")
  private WebElement locationMenuOption;

  @FindBy(
      how = How.CSS,
      using =
          "body > app-root > mat-sidenav-container > mat-sidenav > div > mat-nav-list > a:nth-child(11) > div > div.mat-list-text > span")
  private WebElement coursesMenuOption;

  @FindBy(
      how = How.CSS,
      using =
          "body > app-root > mat-sidenav-container > mat-sidenav > div > mat-nav-list > a:nth-child(12) > div > div.mat-list-text > span")
  private WebElement trainerMenuOption;

  @FindBy(
      how = How.CSS,
      using =
          "body > app-root > mat-sidenav-container > mat-sidenav > div > mat-nav-list > a:nth-child(13) > div > div.mat-list-text > span")
  private WebElement reportMenuOption;

  @FindBy(
      how = How.CSS,
      using =
          "body > app-root > mat-sidenav-container > mat-sidenav > div > mat-nav-list > a:nth-child(14) > div > div.mat-list-text > span")
  private WebElement batchMenuOption;

  @FindBy(
      how = How.CSS,
      using =
          "body > app-root > mat-sidenav-container > mat-sidenav > div > mat-nav-list > a:nth-child(15) > div > div.mat-list-text > span")
  private WebElement homeMenuOption;

public String homePageLabelCheck(int i){
 WebElement label =  this.driver.findElement(By.cssSelector("body > app-root > mat-sidenav-container > mat-sidenav > div > mat-nav-list > a:nth-child("+i +")> div > div.mat-list-text > span"));
return label.getText();
}

  public HomePage(WebDriver drive) {
    this.driver = drive;
    PageFactory.initElements(driver, this);
  }



  public void clickOnMenuButton() {
    menuButton.click();
  }


  public void clickOnLogOutButton() {
    logOutButton.click();
  }

  public void clickOnMeAdminMenuOption() {
    meAdminMenuOption.click();
  }

  public void clickOnLocationMenuOption() {
    locationMenuOption.click();
  }

  public void clickOnCoursesMenuOption() {
    coursesMenuOption.click();
  }

  public void clickOnTrainerMenuOption() {
    trainerMenuOption.click();
  }

  public void clickOnReportMenuOption() {
    reportMenuOption.click();
  }

  public void clickOnBatchMenuOption() {
    batchMenuOption.click();
  }

  public void clickOnHomeMenuOption() {
    homeMenuOption.click();
  }

  public void clickOnLeadClientStageOption() {
    leadClientStageOption.click();
  }

  public void clickOnStudentClientStageOption() {
    studentClientStageOption.click();
  }

  public void clickOnInternClientStageOption() {
    internClientStageOption.click();
  }

  public void clickOnResumeClientStageOption() {
    resumeClientStageOption.click();
  }

  public void clickOnMockClientStageOption() {
    mockClientStageOption.click();
  }

  public void clickOnAluminiClientStageOption() {
    aluminiClientStageOption.click();
  }
}
