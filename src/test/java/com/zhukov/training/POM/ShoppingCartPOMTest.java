package com.zhukov.training.POM;

import com.zhukov.training.data.Size;
import com.zhukov.training.pages.ProductPage;
import com.zhukov.training.pages.StorePage;
import org.testng.annotations.Test;

public class ShoppingCartPOMTest extends BasePOMTest {

  private StorePage storePage;

  @Test
  public void addProductToCart() {
    storePage = app
        .adminPage()
        .goToStore();

    for (int i = 0; i < 3; i++) {
      addProductStep();
    }

    storePage
        .checkout()
        .removeAllProducts();
  }


  private void addProductStep() {
    ProductPage productPage = storePage.openFirstProduct();

    if (productPage.isSizePresented()) {
      productPage.selectSize(Size.SMALL.getValue());
    }
    int counter = storePage.getQuantity();
    productPage
        .addProductToCart()
        .waitQuantityChangedOn(counter + 1)
        .goHome();
  }
}
