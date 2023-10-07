package com.zhukov.training.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class ProductPage extends StorePage {

  @FindBy(name = "options[Size]")
  private WebElement sizeDropdown;


  @FindBy(name = "add_cart_product")
  private WebElement addCartProductButton;

  public ProductPage(WebDriver webDriver) {
    super(webDriver);
  }

  public void selectSize(String type) {
    new Select(sizeDropdown).selectByVisibleText(type);
  }

  public boolean isSizePresented() {
    return isElementPresented(sizeDropdown);
  }


  public ProductPage addProductToCart() {
    addCartProductButton.click();
    return this;
  }

  public ProductPage waitQuantityChangedOn(int value) {
    wait.until(ExpectedConditions.textToBePresentInElement(webDriver.findElement(By.className("quantity")),
        String.valueOf(value)));
    return this;
  }
}
