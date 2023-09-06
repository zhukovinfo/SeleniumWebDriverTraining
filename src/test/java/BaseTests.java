import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class BaseTests {

  @Test
  public void test() {
    WebDriver webDriver = new ChromeDriver();
    webDriver.get("http://www.yandex.ru");
    webDriver.quit();
  }

}
