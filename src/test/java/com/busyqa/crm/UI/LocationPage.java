package com.busyqa.crm.UI;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class LocationPage {
  WebDriver driver;

  @FindBy(
      how = How.CSS,
      using =
          "body > app-root > mat-sidenav-container > mat-sidenav-content > app-location-list > button")
  WebElement addLocation;

  @FindBy(
      how = How.CSS,
      using =
          "body > app-root > mat-sidenav-container > mat-sidenav-content > app-location-list > input")
  WebElement searchLocation;

  public LocationPage(WebDriver driver) {
    this.driver = driver;
    PageFactory.initElements(driver, this);
  }

  public void clickToAddLocation() {
    addLocation.click();
  }

  public void clickToSearchLocation(String locationtobesearched) {

    searchLocation.sendKeys(locationtobesearched);
  }

  public int checkTablelenth() {
    int count;

    WebElement table =
        driver.findElement(
            By.xpath(
                "/html/body/app-root/mat-sidenav-container/mat-sidenav-content/app-location-list/table/tbody"));
    count =table.getSize().height;
    if (count == 0) {
      return 0;
    } else
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
                      " /html/body/app-root/mat-sidenav-container/mat-sidenav-content/app-location-list/table/tbody/tr[" +i+ "]/td[2]\n)"))
              .getText();

      if (email == innerText) {
        bool = true;
      } else bool = false;
    }

    return bool;
  }

  public static class AddingLocationPage {
    WebDriver driver;

    @FindBy(
        how = How.CSS,
        using =
            "body > app-root > mat-sidenav-container > mat-sidenav-content > app-location-add > div > div > form > div:nth-child(1) > input\t")
    WebElement locationName;

    @FindBy(
        how = How.CSS,
        using =
            "body > app-root > mat-sidenav-container > mat-sidenav-content > app-location-add > div > div > form > div:nth-child(2) > input\t")
    WebElement locationDescription;

    @FindBy(
        how = How.CSS,
        using =
            "body > app-root > mat-sidenav-container > mat-sidenav-content > app-location-add > div > div > form > div:nth-child(3) > input\t")
    WebElement locationAddress;

    @FindBy(
        how = How.CSS,
        using =
            "body > app-root > mat-sidenav-container > mat-sidenav-content > app-location-add > div > div > form > div:nth-child(4) > input\t")
    WebElement locationCity;

    @FindBy(
        how = How.CSS,
        using =
            "body > app-root > mat-sidenav-container > mat-sidenav-content > app-location-add > div > div > form > div:nth-child(5) > input\t")
    WebElement locationState;

    @FindBy(
        how = How.CSS,
        using =
            "body > app-root > mat-sidenav-container > mat-sidenav-content > app-location-add > div > div > form > div:nth-child(6) > input\t")
    WebElement locationCountry;

    @FindBy(
        how = How.CSS,
        using =
            "body > app-root > mat-sidenav-container > mat-sidenav-content > app-location-add > div > div > form > div:nth-child(7) > input\t")
    WebElement locationZipCode;

    @FindBy(
        how = How.CSS,
        using =
            "body > app-root > mat-sidenav-container > mat-sidenav-content > app-location-add > div > div > form > div:nth-child(8) > button")
    WebElement locationCreate;

    public AddingLocationPage(WebDriver driver) {
      this.driver = driver;
      PageFactory.initElements(driver, this);
    }

    public void setLocationName(String nameofLocation) {
      locationName.sendKeys(nameofLocation);
    }

    public void setLocationDescription(String descriptionOfLocation) {
      locationDescription.sendKeys(descriptionOfLocation);
    }

    public void setLocationAddress(String address) {
      locationAddress.sendKeys(address);
    }

    public void setLocationCity(String city) {
      locationCity.sendKeys(city);
    }

    public void setLocationState(String state) {
      locationState.sendKeys(state);
    }

    public void setLocationCountry(String country) {
      locationCountry.sendKeys(country);
    }

    public void setLocationZipCode(String zipCode) {
      locationZipCode.sendKeys(zipCode);
    }

    public void clickToCreateLocation() {
      driver.manage().window().maximize();
    //  driver.switchTo().alert().accept();
      locationCreate.click();
    }
  }
}
