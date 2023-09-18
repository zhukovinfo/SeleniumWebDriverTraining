import static org.assertj.core.api.Java6Assertions.assertThat;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class BaseTests {

  WebDriver webDriver;

  @BeforeSuite
  public void setUp() {
    FirefoxOptions options = new FirefoxOptions();
    options.setBinary("C:\\Program Files\\Firefox Nightly\\firefox.exe");
    webDriver = new FirefoxDriver(options);
  }

  @BeforeClass
  public void login() {
    webDriver.get("http://localhost/litecart/admin/");
    webDriver.findElement(By.name("username")).sendKeys("admin");
    webDriver.findElement(By.name("password")).sendKeys("admin");
    webDriver.findElement(By.name("login")).click();
  }

  @Test
  public void adminMenuItemsTest() {
    List<WebElement> parents = webDriver.findElements(By.id("app-"));
    for (int i = 0; i < parents.size(); i++) {
      webDriver.findElements(By.id("app-")).get(i).click();
      WebElement parent = webDriver.findElements(By.id("app-")).get(i);
      assertHeaderExists();

      if (isChildrenListPresent(parent)) {
        List<WebElement> children = parent.findElement(By.className("docs")).findElements(By.className("name"));

        for (int j = 0; j < children.size(); j++) {
          parent.findElement(By.className("docs")).findElements(By.className("name")).get(j).click();
          //find parent element again to avoid "the element with the reference {guid} is stale" error
          parent = webDriver.findElements(By.id("app-")).get(i);
          assertHeaderExists();
        }

      }

    }
  }

  @Test()
  public void checkStickersTest() {
    webDriver.findElement(By.xpath("//*[@title='Catalog']")).click();
    webDriver.findElements(By.xpath("//li[contains(@class,'product')]"))
        .forEach(product ->
            assertThat(product.findElements(By.xpath(".//div[contains(@class,'sticker')]")).size())
                .describedAs("Проверка, что у товара только один стикер")
                .isEqualTo(1));
  }

  boolean isChildrenListPresent(WebElement parent) {
    try {
      parent.findElement(By.className("docs"));
      return true;
    } catch (NoSuchElementException ex) {
      return false;
    }
  }

  private void assertHeaderExists() {
    assertThat(webDriver.findElements(By.tagName("h1")).size())
        .describedAs("Проверка наличия заголовка h1 на странице")
        .isEqualTo(1);
  }

  @AfterSuite(alwaysRun = true)
  public void tearDown() {
    webDriver.get("http://localhost/litecart/admin/logout.php");
    webDriver.close();
  }
}
