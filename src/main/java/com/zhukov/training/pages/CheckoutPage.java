package com.zhukov.training.pages;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CheckoutPage extends StorePage {

  @FindBy(className = "shortcut")
  private List<WebElement> shortcuts;

  @FindBy(xpath = "//table[contains(@class,'dataTable')]")
  private WebElement orderSummaryTable;

  @FindBy(name = "remove_cart_item")
  private WebElement removeButton;

  public CheckoutPage(WebDriver webDriver) {
    super(webDriver);
  }

  public void removeAllProducts() {
    WebElement table = webDriver.findElement(By.xpath("//table[contains(@class,'dataTable')]"));
    for (int i = 0; i < shortcuts.size(); i++) {
      if (isElementPresented(shortcuts.get(i))) {
        shortcuts.get(i).findElement(By.tagName("img")).click();
      }
      removeButton.click();
      wait.until(ExpectedConditions.invisibilityOf(table));
    }
  }
}
