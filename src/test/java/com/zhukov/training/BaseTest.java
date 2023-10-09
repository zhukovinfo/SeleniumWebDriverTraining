package com.zhukov.training;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.stream.IntStream;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.Browser;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.events.WebDriverListener;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseTest {

  protected WebDriver webDriver;
  protected WebDriverWait wait;

  public static class MyListener implements WebDriverListener {

    @Override
    public void onError(Object target, Method method, Object[] args, InvocationTargetException e) {
      WebDriverListener.super.onError(target, method, args, e);
      System.out.println("Ошибка " + e.getTargetException().toString());
      takeScreenshot((WebDriver) target);
    }

    private void takeScreenshot(WebDriver driver) {
      File tempFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
      File file = new File("screen" + System.currentTimeMillis() + ".png");
      try {
        Files.copy(tempFile.toPath(), file.toPath());
      } catch (IOException e) {
        e.printStackTrace();
      }
      System.out.println(file);
    }
  }


  @BeforeClass
  public void setUp() {
    initDriver();
    login();
  }

  private void initDriver() {
    String browserName = Browser.CHROME.browserName();
    //String browserName = Browser.FIREFOX.browserName();
    webDriver = getWebDriver(browserName);
    wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
  }

  private WebDriver getWebDriver(String name) {
    if (Browser.CHROME.browserName().equals(name)) {
      LoggingPreferences loggingPreferences = new LoggingPreferences();
      loggingPreferences.enable("browser", Level.ALL);
      ChromeOptions chromeOptions = new ChromeOptions();
      chromeOptions.setCapability("goog:loggingPrefs", loggingPreferences);
      return new EventFiringDecorator<>(new MyListener()).decorate(new ChromeDriver(chromeOptions));
    }
    if (Browser.FIREFOX.browserName().equals(name)) {
      FirefoxOptions options = new FirefoxOptions();
      options.setBinary("C:\\Program Files\\Firefox Nightly\\firefox.exe");
      return new EventFiringDecorator<>(new MyListener()).decorate(new FirefoxDriver(options));
    } else {
      throw new IllegalArgumentException("Unknown browser name specified");
    }
  }

  private void login() {
    webDriver.get("http://localhost/litecart/admin/");
    webDriver.findElement(By.name("username")).sendKeys("admin");
    webDriver.findElement(By.name("password")).sendKeys("admin");
    webDriver.findElement(By.name("login")).click();
  }


  public int getColumnIdByName(WebElement table, String columnName) {
    List<WebElement> headers = table.findElement(By.className("header")).findElements(By.tagName("th"));
    return IntStream.range(0, headers.size())
        .filter(id -> headers.get(id).getText().equals(columnName))
        .boxed()
        .findFirst()
        .orElseThrow(() -> new NoSuchElementException("Column '" + columnName + "' not found in the table"));
  }

  public boolean isElementPresented(By locator) {
    try {
      webDriver.findElement(locator);
      return true;
    } catch (org.openqa.selenium.NoSuchElementException ex) {
      return false;
    }
  }

  @AfterClass(alwaysRun = true)
  public void tearDown() {
    webDriver.get("http://localhost/litecart/admin/logout.php");
    webDriver.close();
  }


}
