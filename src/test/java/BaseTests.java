import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseTests {

  protected WebDriver webDriver;



  @BeforeClass
  public void setUp() {
    initDriver();
    login();
  }

  private void initDriver() {
    FirefoxOptions options = new FirefoxOptions();
    options.setBinary("C:\\Program Files\\Firefox Nightly\\firefox.exe");
    webDriver = new FirefoxDriver(options);
  }

  private void login() {
    webDriver.get("http://localhost/litecart/admin/");
    webDriver.findElement(By.name("username")).sendKeys("admin");
    webDriver.findElement(By.name("password")).sendKeys("admin");
    webDriver.findElement(By.name("login")).click();
  }

  @AfterClass(alwaysRun = true)
  public void tearDown() {
    webDriver.get("http://localhost/litecart/admin/logout.php");
    webDriver.close();
  }


}
