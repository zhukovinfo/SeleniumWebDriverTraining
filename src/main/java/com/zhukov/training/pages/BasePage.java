package com.zhukov.training.pages;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BasePage {

  protected WebDriver webDriver;
  protected WebDriverWait wait;

  public BasePage(WebDriver webDriver) {
    this.webDriver = webDriver;
    wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
    PageFactory.initElements(webDriver, this);
  }

  boolean isElementPresented(WebElement element) {
    try {
      element.isDisplayed();
      return true;
    }
    catch (org.openqa.selenium.NoSuchElementException ex) {
      return false;
    }
  }

}
