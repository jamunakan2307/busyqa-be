package com.busyqa.crm.UI;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SignUpPage {
    WebDriver driver;

    private WebElement firstName;
    private WebElement lastName;
    private WebElement userName;
    private WebElement email;
    private WebElement phoneNumber;
    private WebElement courseName;
    private WebElement courseSchedules;
    private WebElement leadSource;
    private WebElement comments;
    private WebElement acceptButton;
    private WebElement discardButton;


    public void setFirstName(String fName) {
       firstName.sendKeys(fName);
    }

    public void setLastName(String lName) {
        lastName.sendKeys(lName);

    }

    public void setUserName(String uName) {
        userName.sendKeys(uName);

    }

    public void setEmail(String emailId) {
        email.sendKeys(emailId);

    }

    public void setPhoneNumber(String phNumber) {
        phoneNumber.sendKeys(phNumber);

    }

    public void setCourseName(String course) {
        courseName.sendKeys(course);

    }

    public void setCourseSchedules(String Schedules) {
        courseSchedules.sendKeys(Schedules);

    }

    public void setLeadSource(String leades) {
        leadSource.sendKeys(leades);

    }

    public void setComments(String comment) {
        comments.sendKeys(comment);

    }
    public void clickToAccept(){
        acceptButton.click();
    }
    public void clickToDiscard(){
        discardButton.click();
    }
}

