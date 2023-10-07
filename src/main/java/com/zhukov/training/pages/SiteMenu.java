package com.zhukov.training.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SiteMenu extends BasePage {

  @FindBy(xpath = "//*[@title='Home']")
  private WebElement homeLink;

  public SiteMenu(WebDriver webDriver) {
    super(webDriver);
  }

  public void goHome() {
    homeLink.click();
  }
}
