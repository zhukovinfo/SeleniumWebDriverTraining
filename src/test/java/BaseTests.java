import static org.assertj.core.api.Java6Assertions.assertThat;

import java.util.List;
import org.openqa.selenium.By;
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

  @Test(enabled = false)
  public void adminMenuItemsTest() {
    By byName = By.className("name");
    List<WebElement> parentMenuItems = webDriver.findElements(byName);
    int parentMenuItemsCount = parentMenuItems.size();
    for (int i = 0; i < parentMenuItemsCount; i++) {
      //find elements again to avoid "the element with the reference {guid} is stale" error
      WebElement parentMenuItem = webDriver.findElements(byName).get(i);
      int childrenCount = parentMenuItem.findElements(byName).size();
      parentMenuItem.click();
      assertHeaderExists();

      if (childrenCount > 0) {
        for (int j = 0; j < childrenCount; j++) {
          webDriver.findElements(byName).get(i).findElements(byName).get(j).click();
          assertHeaderExists();
        }
      }
    }
  }

  @Test
  public void checkStickersTest() {
    webDriver.findElement(By.xpath("//*[@title='Catalog']")).click();
    webDriver.findElements(By.xpath("//*[@class='image-wrapper']"))
        .forEach(product ->
            assertThat(product.findElements(By.xpath(".//div[contains(@class,'sticker')]")).size())
                .describedAs("Проверка, что у товара только один стикер")
                .isEqualTo(1));
  }

  private void assertHeaderExists() {
    assertThat(webDriver.findElements(By.tagName("h1")).size())
        .describedAs("Проверка наличия заголовка h1 на странице")
        .isEqualTo(1);
  }

  @AfterSuite(alwaysRun = true)
  public void tearDown() {
    webDriver.get("http://localhost/litecart/admin/logout.php");
    webDriver.quit();
  }
}
