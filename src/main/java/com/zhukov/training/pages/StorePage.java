package com.zhukov.training.pages;

import java.util.List;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class StorePage extends SiteMenu {

  @FindBy(xpath = "//li[contains(@class,'product')]")
  private List<WebElement> products;

  @FindBy(className = "quantity")
  private WebElement quantityLabel;

  @FindBy(linkText = "Checkout »")
  public WebElement checkoutLink;

  public StorePage(WebDriver webDriver) {
    super(webDriver);
  }

  public ProductPage openFirstProduct() {
    products.stream()
        .findFirst()
        .orElseThrow(() -> new NoSuchElementException("No any products presented"))
        .click();
    return new ProductPage(webDriver);
  }



  public int getQuantity() {
    return Integer.parseInt(quantityLabel.getText());
  }

  public CheckoutPage checkout() {
    checkoutLink.click();
    return new CheckoutPage(webDriver);
  }
}
