package com.zhukov.training;

import java.net.MalformedURLException;
import java.net.URI;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;

public class RemoteRunTests {

  WebDriver driver;

  @Test
  public void runTest() throws MalformedURLException {
    FirefoxOptions firefoxOptions = new FirefoxOptions();
    driver = new RemoteWebDriver(URI.create("http://192.168.0.103:4444/").toURL(), firefoxOptions);
    driver.get("http://selenium2.ru");
    driver.quit();
  }

}
