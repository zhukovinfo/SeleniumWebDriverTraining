package com.zhukov.training;

import java.util.NoSuchElementException;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;


public class RegisterNewUserTests extends BaseTest {

  @Test
  public void registerNewCustomerTest() {
    String email = RandomStringUtils.randomAlphabetic(5) + "@mail.com";
    String password = "password";
    registerNewCustomer(email, password);
    logout();
    login(email, password);
    logout();
  }

  private void registerNewCustomer(String email, String password) {
    webDriver.get("http://localhost/litecart/en/create_account");

    webDriver.findElement(By.name("tax_id")).sendKeys("123456789");
    webDriver.findElement(By.name("company")).sendKeys("Test-Tech Inc.");
    webDriver.findElement(By.name("firstname")).sendKeys("Alex");
    webDriver.findElement(By.name("lastname")).sendKeys("Stanford");
    webDriver.findElement(By.name("address1")).sendKeys("Fargo city, Main str., 11-12");
    webDriver.findElement(By.name("address2")).sendKeys("New Bru city, Secondary str., 9-10");
    webDriver.findElement(By.name("postcode")).sendKeys("44455");
    webDriver.findElement(By.name("city")).sendKeys("New Moscow");
    webDriver.findElement(By.className("select2-selection__rendered")).click();
    webDriver.findElements(By.className("select2-results__option")).stream()
        .filter(element -> element.getText().equals("United States"))
        .findFirst()
        .orElseThrow(() -> new NoSuchElementException("No option United States found"))
        .click();
    new Select(webDriver.findElement(By.cssSelector("select[name='zone_code']"))).selectByVisibleText("Alaska");
    webDriver.findElement(By.name("email")).sendKeys(email);
    webDriver.findElement(By.name("phone")).sendKeys("+19870123456");
    webDriver.findElement(By.name("password")).sendKeys(password);
    webDriver.findElement(By.name("confirmed_password")).sendKeys(password);

    webDriver.findElement(By.name("create_account")).click();
  }

  private void login(String email, String password) {
    webDriver.findElement(By.name("email")).sendKeys(email);
    webDriver.findElement(By.name("password")).sendKeys(password);
    webDriver.findElement(By.name("login")).click();
  }

  private void logout() {
    webDriver.get("http://localhost/litecart/en/logout");
  }
}
