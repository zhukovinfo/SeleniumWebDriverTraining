import static org.assertj.core.api.Java6Assertions.assertThat;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
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
    webDriver.get(" http://localhost/litecart/admin/");
    webDriver.findElement(By.name("username")).sendKeys("admin");
    webDriver.findElement(By.name("password")).sendKeys("admin");
    webDriver.findElement(By.name("login")).click();
  }

  @Test(dataProvider = "menuItemsData")
  public void test(String menuItemText) {
    webDriver.findElement(By.linkText(menuItemText)).click();
    assertThat(webDriver.findElements(By.tagName("h1")).size())
        .describedAs("Проверка наличия заголовка h1 на странице")
        .isEqualTo(1);
  }

  @DataProvider(name = "menuItemsData")
  public static Object[] mainMenuItemsTexts() {
    return new Object[]{"Appearence", "Catalog", "Countries", "Currencies", "Customers", "Geo Zones", "Languages",
        "Modules", "Orders", "Pages", "Reports", "Settings", "Slides", "Tax", "Translations", "Users", "vQmods"};
  }

  @AfterSuite(alwaysRun = true)
  public void tearDown() {
    webDriver.get("http://localhost/litecart/admin/logout.php");
    webDriver.quit();
  }
}
