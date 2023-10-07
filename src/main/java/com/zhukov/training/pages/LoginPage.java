package com.zhukov.training.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

  @FindBy(name = "username")
  private WebElement usernameInput;

  @FindBy(name = "password")
  private WebElement passwordInput;

  @FindBy(name = "login")
  private WebElement loginButton;

  public LoginPage(WebDriver webDriver) {
    super(webDriver);
  }

  public void asAdmin() {
    login("admin", "admin");
  }
  private void login(String username, String password) {
    fillUsername(username);
    fillPassword(password);
    loginButton.click();
  }

  private void fillUsername(String username) {
    usernameInput.clear();
    usernameInput.sendKeys(username);
  }

  private void fillPassword(String password) {
    passwordInput.clear();
    passwordInput.sendKeys(password);
  }
}
