import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;

public class ProxyTests {

  @Test
  public void proxyTest() {
    Proxy proxy = new Proxy();
    proxy.setHttpProxy("127.0.0.1:8888");
    proxy.setSslProxy("127.0.0.1:8888");
    ChromeOptions chromeOptions = new ChromeOptions();
    chromeOptions.setCapability("proxy", proxy);
    WebDriver driver = new ChromeDriver(chromeOptions);
    driver.get("https://mail.ru/");
    System.out.println(proxy.getHttpProxy());
    driver.quit();
  }

}
