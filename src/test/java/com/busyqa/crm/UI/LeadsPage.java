package com.busyqa.crm.UI;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class LeadsPage {

  @FindBy(
      how = How.CSS,
      using =
          "body > app-root > mat-sidenav-container > mat-sidenav-content > app-leads-list > button")
  WebElement addClient;

  // menu button and logout button created in home page;
  @FindBy(
      how = How.CSS,
      using =
          "body > app-root > mat-sidenav-container > mat-sidenav-content > app-leads-list > input")
  WebElement searchClient;

  @FindBy(
      how = How.CSS,
      using =
          "body > app-root > mat-sidenav-container > mat-sidenav-content > app-leads-list > table > thead > tr > th:nth-child(2)")
  WebElement firstName;

  @FindBy(
      how = How.CSS,
      using =
          "body > app-root > mat-sidenav-container > mat-sidenav-content > app-leads-list > table > thead > tr > th:nth-child(3)")
  WebElement email;

  @FindBy(
      how = How.CSS,
      using =
          "body > app-root > mat-sidenav-container > mat-sidenav-content > app-leads-list > table > thead > tr > th:nth-child(4)")
  WebElement contact;

  @FindBy(
      how = How.CSS,
      using =
          "body > app-root > mat-sidenav-container > mat-sidenav-content > app-leads-list > table > thead > tr > th:nth-child(5)")
  WebElement course;

  @FindBy(
      how = How.CSS,
      using =
          "body > app-root > mat-sidenav-container > mat-sidenav-content > app-leads-list > table > thead > tr > th:nth-child(6)")
  WebElement location;

  @FindBy(
      how = How.CSS,
      using =
          "body > app-root > mat-sidenav-container > mat-sidenav-content > app-leads-list > table > thead > tr > th:nth-child(7)")
  WebElement registered;

  @FindBy(
      how = How.CSS,
      using =
          "body > app-root > mat-sidenav-container > mat-sidenav-content > app-leads-list > div.center-div > h4")
  WebElement leadsPageCheck;

  private WebDriver driver;

  public LeadsPage(WebDriver driver) {
    this.driver = driver;
    PageFactory.initElements(driver, this);
  }

  public boolean isPageOpened(String title) {
    return leadsPageCheck.getText().toString().contains(title);
  }

  public void setAddClient() {
    addClient.click();
  }

  public void setSearchClient(String clientSearchIdentity) {
    searchClient.getText().contains(clientSearchIdentity);
  }

  public void setFirstName(String ftName) {
    firstName.sendKeys(ftName);
  }

  public void setEmail(WebElement email) {
    this.email = email;
  }

  public void setContact(WebElement contact) {
    this.contact = contact;
  }

  public void setCourse(WebElement course) {
    this.course = course;
  }

  public void setLocation(WebElement location) {
    this.location = location;
  }

  public void setRegistered(WebElement registered) {
    this.registered = registered;
  }

  public int checkTablelenth() {
    int count;

    WebElement table =  driver
            .findElement(
                By.xpath(
                    "/html/body/app-root/mat-sidenav-container/mat-sidenav-content/app-leads-list/table"));
    if(table.getSize()== null){
      return 0;
    }
    else
    count = table.getSize().height;
    System.out.println("value of" + count);
    return count;
  }

  public boolean checkTableElements(String email) {
    boolean bool = false;
    int size = checkTablelenth();
    for (int i = 0; i < size; i++) {
      String innerText =
          driver
              .findElement(
                  By.xpath(
                      "/html/body/app-root/mat-sidenav-container/mat-sidenav-content/app-leads-list/table/tbody/tr["
                          + i
                          + "]/td[3]"))
              .getText();
      if (email == innerText) {
        bool = true;
      } else bool = false;
    }

    return bool;
  }


}
