package com.zhukov.training;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Collectors;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

public class GeoZoneSortingTests extends BaseTest {

  private WebElement table;

  @Test
  public void geoZoneSortingTest() {
    webDriver.get("http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones");
    table = webDriver.findElement(By.className("dataTable"));
    int columnNameId = getColumnIdByName(table, "Name");
    List<WebElement> rows = table.findElements(By.className("row"));
    for (int i = 0; i < rows.size(); i++) {
      WebElement row = table.findElements(By.className("row")).get(i);
      row.findElements(By.tagName("td")).get(columnNameId).findElement(By.tagName("a")).click();
      verifyZones();
      webDriver.findElement(By.name("cancel")).click();
      table = webDriver.findElement(By.className("dataTable")); //search table again after page reloading
    }

  }

  private void verifyZones() {
    table = webDriver.findElement(By.id("table-zones"));
    List<WebElement> rows = table.findElements(By.tagName("tr"));
    int columnZoneId = getColumnIdByName(table, "Zone");

    List<String> zones = rows.stream()
        .filter(row -> row.findElements(By.tagName("td")).size() > 0) //exclude header row
        .filter(row -> row.findElements(By.xpath(".//td/select")).size() > 0) //exclude last empty row
        .map(row -> {
          WebElement selectElement = row.findElements(By.tagName("td")).get(columnZoneId)
              .findElement(By.tagName("select"));
          Select dropDown = new Select(selectElement);
          return dropDown.getFirstSelectedOption().getText();
        })
        .collect(Collectors.toList());

    assertThat(zones)
        .describedAs("Проверка, что зоны расположены в алфавитном порядке")
        .isEqualTo(zones.stream()
            .sorted()
            .collect(Collectors.toList()));
  }

}
