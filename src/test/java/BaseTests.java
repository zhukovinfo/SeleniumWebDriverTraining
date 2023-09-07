import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class BaseTests {
  WebDriver webDriver;

  @BeforeClass
  public void setUp() {
    webDriver = new ChromeDriver();
    webDriver.get(" http://localhost/litecart/admin/");
  }

  @Test
  public void test() {
    webDriver.findElement(By.name("username")).sendKeys("admin");
    webDriver.findElement(By.name("password")).sendKeys("admin");
    webDriver.findElement(By.name("login")).click();
  }

  @AfterClass(alwaysRun = true)
  public void tearDown() {
    webDriver.quit();
  }
}
