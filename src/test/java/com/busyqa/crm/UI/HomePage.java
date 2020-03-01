package com.busyqa.crm.UI;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class AdminPortal {

   private WebDriver driver;


@FindBy(how = How.CSS,using = "body > app-root > mat-sidenav-container > mat-sidenav-content > app-home > div > aside")
   private WebElement landingCheckbox;

@FindBy(how = How.CSS,using = "#appToolbar > button.mat-icon-button.sidenav-toggle-button.ng-star-inserted > span > mat-icon")
   private WebElement menuButton;

   @FindBy(how = How.CSS,using ="#appToolbar > button:nth-child(3)")
   private WebElement logOutButton;

   @FindBy(how = How.CSS,using = "body > app-root > mat-sidenav-container > mat-sidenav > div > mat-nav-list > a.sidenav-link.mat-list-item.mat-list-item-avatar.mat-list-item-with-avatar.ng-star-inserted.is-active > div > div.mat-list-text > span")
   private WebElement meAdminMenuOption;

   @FindBy(how = How.CSS,using = "body > app-root > mat-sidenav-container > mat-sidenav > div > mat-nav-list > a.sidenav-link.mat-list-item.mat-list-item-avatar.mat-list-item-with-avatar.ng-star-inserted.is-active > div > div.mat-list-text > span")
   private WebElement locationMenuOption;

   @FindBy(how = How.CSS,using = "")
   private WebElement 
}
