import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

public class ShoppingCartTests extends BaseTest {

  private static final int PRODUCT_COUNT = 3;

  @Test
  public void addProductToCart() {
    webDriver.findElement(By.xpath("//*[@title='Catalog']")).click();
    for (int i = 0; i < PRODUCT_COUNT; i++) {
      addProduct();
      webDriver.findElement(By.xpath("//*[@title='Home']")).click();
    }
    webDriver.get("http://localhost/litecart/en/checkout");
    int shortcutCount = webDriver.findElements(By.className("shortcut")).size();
    for (int i = 0; i < shortcutCount; i++) {
      removeProduct();
    }
  }

  private void addProduct() {
    WebElement product = webDriver.findElements(By.xpath("//li[contains(@class,'product')]"))
        .stream()
        .findFirst()
        .orElseThrow(() -> new NoSuchElementException("No any products presented"));
    product.click();
    if (isElementPresented(By.name("options[Size]"))) {
      new Select(webDriver.findElement(By.name("options[Size]"))).selectByVisibleText("Small");
    }
    int counter = Integer.parseInt(webDriver.findElement(By.className("quantity")).getText());
    webDriver.findElement(By.name("add_cart_product")).click();
    wait.until(ExpectedConditions.textToBePresentInElement(webDriver.findElement(By.className("quantity")),
        String.valueOf(counter + 1)));
  }

  private void removeProduct() {
    WebElement table = webDriver.findElement(By.xpath("//table[contains(@class,'dataTable')]"));

    if (isElementPresented(By.className("shortcut"))) {
      webDriver.findElement(By.className("shortcut")).findElement(By.tagName("img")).click();
    }
    webDriver.findElement(By.name("remove_cart_item")).click();
    wait.until(ExpectedConditions.invisibilityOf(table));
  }

}
