import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

public class AddNewProductTests extends BaseTest {
  private static final String PRODUCT_NAME = "DOG";

  @Test()
  public void addNewProductTest() throws InterruptedException {
    webDriver.findElement(By.linkText("Catalog")).click();
    List<String> expectedNames = getProductNames();
    expectedNames.add(PRODUCT_NAME);

    fillGeneral();

    webDriver.findElement(By.linkText("Information")).click();
    Thread.sleep(1000);
    fillInformation();

    webDriver.findElement(By.linkText("Prices")).click();
    Thread.sleep(1000);
    fillPrice();

    webDriver.findElement(By.name("save")).click();

    List<String> actualNames = getProductNames();

    assertThat(actualNames)
        .describedAs("Проверка, что в каталоге содержится название добавленного продукта " + PRODUCT_NAME)
        .containsExactlyInAnyOrderElementsOf(expectedNames);
  }

  private List<String> getProductNames() {
    WebElement table = webDriver.findElement(By.className("dataTable"));
    List<WebElement> rows = table.findElements(By.className("row"));
    return rows.stream()
        .map(row -> row.findElements(By.tagName("td"))
            .get(getColumnIdByName(table, "Name"))
            .getText())
        .collect(Collectors.toList());
  }

  private void fillGeneral() {
    webDriver.findElement(By.linkText("Add New Product")).click();
    webDriver.findElement(
            By.xpath("//label[normalize-space(text()) = 'Enabled']/input[@type='radio'][@name='status']"))
        .click();
    webDriver.findElement(By.name("name[en]")).sendKeys(PRODUCT_NAME);
    webDriver.findElement(By.name("code")).sendKeys("001");
    webDriver.findElement(By.xpath("//input[@type='checkbox'][@name='categories[]']"
        + "[@data-name='Rubber Ducks']")).click();
    new Select(webDriver.findElement(By.name("default_category_id"))).selectByVisibleText("Rubber Ducks");
    webDriver.findElement(By.xpath("//td[text()='Unisex']/preceding-sibling::td/input[@type='checkbox' "
        + "and @name='product_groups[]']")).click();

    webDriver.findElement(By.name("quantity")).sendKeys("10");
    new Select(webDriver.findElement(By.name("quantity_unit_id"))).selectByVisibleText("pcs");
    new Select(webDriver.findElement(By.name("delivery_status_id"))).selectByVisibleText("3-5 days");
    new Select(webDriver.findElement(By.name("sold_out_status_id"))).selectByVisibleText("Sold out");
    webDriver.findElement(By.name("new_images[]")).sendKeys(getTestImageFilePath());

    webDriver.findElement(By.name("date_valid_from")).sendKeys("01012023");
    webDriver.findElement(By.name("date_valid_to")).sendKeys("01122024");
  }

  private String getTestImageFilePath() {
    URL resourceUrl = getClass().getClassLoader().getResource("dog.jpeg");
    if (resourceUrl != null) {
      return new File(resourceUrl.getFile()).getAbsolutePath();
    } else {
      throw new NullPointerException("Resource " + "dog.jpeg" + "not found");
    }
  }

  private void fillInformation() {
    new Select(webDriver.findElement(By.name("manufacturer_id"))).selectByVisibleText("ACME Corp.");
    webDriver.findElement(By.name("keywords")).sendKeys("dog, retriever, gold");
    webDriver.findElement(By.name("short_description[en]")).sendKeys("a friend of any child");
    webDriver.findElement(By.xpath("//div[@contenteditable='true']"))
        .sendKeys("The most wanted product for a child. Lovely, funny, friendly pet");
    webDriver.findElement(By.name("head_title[en]")).sendKeys("Funny Dog");
    webDriver.findElement(By.name("meta_description[en]")).sendKeys("some meta info");
  }

  private void fillPrice() {
    webDriver.findElement(By.name("purchase_price")).clear();
    webDriver.findElement(By.name("purchase_price")).sendKeys("100.55");
    new Select(webDriver.findElement(By.name("purchase_price_currency_code"))).selectByVisibleText("US Dollars");

    webDriver.findElement(By.name("prices[USD]")).clear();
    webDriver.findElement(By.name("prices[USD]")).sendKeys("10");

    webDriver.findElement(By.name("gross_prices[USD]")).clear();
    webDriver.findElement(By.name("gross_prices[USD]")).sendKeys("15");

    webDriver.findElement(By.name("prices[EUR]")).clear();
    webDriver.findElement(By.name("prices[EUR]")).sendKeys("11");

    webDriver.findElement(By.name("gross_prices[EUR]")).clear();
    webDriver.findElement(By.name("gross_prices[EUR]")).sendKeys("14");
  }
}
