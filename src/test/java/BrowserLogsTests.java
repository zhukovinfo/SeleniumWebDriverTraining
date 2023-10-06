import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.logging.LogEntry;
import org.testng.annotations.Test;

public class BrowserLogsTests extends BaseTest {

  @Test
  public void browserLogTest() {
    webDriver.get("http://localhost/litecart/admin/?app=catalog&doc=catalog&category_id=1");
    int productCount = webDriver.findElements(
            By.xpath("//a[contains(@href, 'doc=edit_product&category_id=1') and not(@title='Edit')]"))
        .size();
    for (int i = 0; i < productCount; i++) {
      webDriver.findElements(
              By.xpath("//a[contains(@href, 'doc=edit_product&category_id=1') and not(@title='Edit')]"))
          .get(i)
          .click();

      List<LogEntry> logs = webDriver.manage().logs().get("browser").getAll();
      logs.forEach(System.out::println);
      assertThat(logs.size())
          .describedAs("Проверка, что в логе браузера нет сообщений (любого уровня)")
          .isEqualTo(0);
      webDriver.findElement(By.name("cancel")).click();
    }
  }
}