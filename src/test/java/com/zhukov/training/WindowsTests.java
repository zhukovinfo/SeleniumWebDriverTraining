package com.zhukov.training;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

public class WindowsTests extends BaseTest {

  @Test
  public void openWindowsTest() {
    webDriver.get("http://localhost/litecart/admin/?app=countries&doc=countries");
    webDriver.findElement(By.linkText("Add New Country")).click();
    String originalWindow = webDriver.getWindowHandle();
    int windowCount = webDriver.getWindowHandles().size();
    webDriver.findElements(By.xpath("//i[@class='fa fa-external-link']"))
        .forEach(link -> {
          link.click();
          wait.until(ExpectedConditions.numberOfWindowsToBe(windowCount + 1));

          webDriver.getWindowHandles().forEach(windowHandle -> {
            if (!windowHandle.equals(originalWindow)) {
              webDriver.switchTo().window(windowHandle);
              wait.until(ExpectedConditions.not(ExpectedConditions.titleIs("")));
              System.out.println("Открыта новая страница: " + webDriver.getTitle());
              webDriver.close();
              webDriver.switchTo().window(originalWindow);
            }
          });
        });
  }
}
