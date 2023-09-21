import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

public class CountriesSortingTests extends BaseTests {

  private WebElement table;

  @Test()
  public void geoCountriesSortingTest() {
    webDriver.get("http://localhost/litecart/admin/?app=countries&doc=countries");

    table = webDriver.findElement(By.className("dataTable"));
    List<WebElement> rows = table.findElements(By.className("row"));
    int columnNameId = getColumnIdByName("Name");

    List<String> names = rows.stream()
        .map(row -> row.findElements(By.tagName("td")).get(columnNameId).getText())
        .collect(Collectors.toList());

    SoftAssertions.assertSoftly(softAssertions -> {
      softAssertions.assertThat(names)
          .describedAs("Проверка, что страны расположены в алфавитном порядке")
          .isEqualTo(names.stream()
              .sorted()
              .collect(Collectors.toList()));

      int columnZoneId = getColumnIdByName("Zones");

      for (int i = 0; i < rows.size(); i++) {
        WebElement row = table.findElements(By.className("row")).get(i);
        int zonesCount = Integer.parseInt(row.findElements(By.tagName("td")).get(columnZoneId).getText());
        if (zonesCount > 0) {
          row.findElements(By.tagName("td")).get(columnNameId).findElement(By.tagName("a")).click();
          verifyZones(softAssertions);
          webDriver.findElement(By.name("cancel")).click();
        }
        table = webDriver.findElement(By.className("dataTable")); //search table again after page reloading
      }
    });
  }

  private void verifyZones(SoftAssertions softAssertions) {
    table = webDriver.findElement(By.id("table-zones"));
    List<WebElement> rows = table.findElements(By.tagName("tr"));
    int columnNameId = getColumnIdByName("Name");

    List<String> names = rows.stream()
        .filter(row -> row.findElements(By.tagName("td")).size() > 0) //exclude header row
        .map(row -> row.findElements(By.tagName("td")).get(columnNameId).getText())
        .limit(rows.size() - 1)
        .collect(Collectors.toList());

    names = names.stream()
        .limit(names.size() - 1) //exclude the last row as a technical but zone row
        .collect(Collectors.toList());

    softAssertions.assertThat(names)
        .describedAs("Проверка, что зоны расположены в алфавитном порядке")
        .isEqualTo(names.stream()
            .sorted()
            .collect(Collectors.toList()));
  }

  private int getColumnIdByName(String columnName) {
    List<WebElement> headers = table.findElement(By.className("header")).findElements(By.tagName("th"));
    return IntStream.range(0, headers.size())
        .filter(id -> headers.get(id).getText().equals(columnName))
        .boxed()
        .findFirst()
        .orElseThrow(() -> new NoSuchElementException("Column '" + columnName + "' not found in the table"));
  }

}
