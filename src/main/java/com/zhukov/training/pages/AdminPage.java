package com.zhukov.training.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AdminPage extends BasePage {

  @FindBy(xpath = "//a[@title='Catalog']")
  private WebElement catalogLink;

  public AdminPage(WebDriver webDriver) {
    super(webDriver);
  }

  public StorePage goToStore() {
    catalogLink.click();
    return new StorePage(webDriver);
  }

}
