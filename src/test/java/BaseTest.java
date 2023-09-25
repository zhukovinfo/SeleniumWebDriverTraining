import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.Browser;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseTest {

  protected WebDriver webDriver;
  protected WebDriverWait wait;

  @BeforeClass
  public void setUp() {
    initDriver();
    login();
  }

  private void initDriver() {
    //String browserName = Browser.CHROME.browserName();
    String browserName = Browser.FIREFOX.browserName();
    webDriver = getWebDriver(browserName);
    wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
  }

  private WebDriver getWebDriver(String name) {
    if (Browser.CHROME.browserName().equals(name)) {
      return new ChromeDriver();
    }
    if (Browser.FIREFOX.browserName().equals(name)) {
      FirefoxOptions options = new FirefoxOptions();
      options.setBinary("C:\\Program Files\\Firefox Nightly\\firefox.exe");
      return new FirefoxDriver(options);
    } else
      throw new IllegalArgumentException("Unknown browser name specified");
  }

  private void login() {
    webDriver.get("http://localhost/litecart/admin/");
    webDriver.findElement(By.name("username")).sendKeys("admin");
    webDriver.findElement(By.name("password")).sendKeys("admin");
    webDriver.findElement(By.name("login")).click();
  }

  public int getColumnIdByName(WebElement table, String columnName) {
    List<WebElement> headers = table.findElement(By.className("header")).findElements(By.tagName("th"));
    return IntStream.range(0, headers.size())
        .filter(id -> headers.get(id).getText().equals(columnName))
        .boxed()
        .findFirst()
        .orElseThrow(() -> new NoSuchElementException("Column '" + columnName + "' not found in the table"));
  }

  boolean isElementPresented(By locator) {
    try {
      webDriver.findElement(locator);
      return true;
    }
    catch (org.openqa.selenium.NoSuchElementException ex) {
      return false;
    }
  }
  @AfterClass(alwaysRun = true)
  public void tearDown() {
    webDriver.get("http://localhost/litecart/admin/logout.php");
    webDriver.close();
  }


}
