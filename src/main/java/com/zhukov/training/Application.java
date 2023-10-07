package com.zhukov.training;

import com.zhukov.training.pages.AdminPage;
import com.zhukov.training.pages.LoginPage;
import java.util.logging.Level;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.Browser;

public class Application {

  private WebDriver driver;

  public Application(WebDriver driver) {
    this.driver = driver;
  }

  public void start() {
    initDriver();
    login().asAdmin();
  }

  public void stop() {
    logout();
    driver.close();
  }

  public AdminPage adminPage() {
    return new AdminPage(driver);
  }

  private void initDriver() {
    String browserName = Browser.CHROME.browserName();
    //String browserName = Browser.FIREFOX.browserName();
    driver = getDriver(browserName);
  }

  private WebDriver getDriver(String name) {
    if (Browser.CHROME.browserName().equals(name)) {
      LoggingPreferences loggingPreferences = new LoggingPreferences();
      loggingPreferences.enable("browser", Level.ALL);
      ChromeOptions chromeOptions = new ChromeOptions();
      chromeOptions.setCapability("goog:loggingPrefs", loggingPreferences);
      return new ChromeDriver(chromeOptions);
    }
    if (Browser.FIREFOX.browserName().equals(name)) {
      FirefoxOptions options = new FirefoxOptions();
      options.setBinary("C:\\Program Files\\Firefox Nightly\\firefox.exe");
      return new FirefoxDriver(options);
    } else {
      throw new IllegalArgumentException("Unknown browser name specified");
    }
  }

  private LoginPage login() {
    driver.get("http://localhost/litecart/admin/");
    return new LoginPage(driver);
  }

  private void logout() {
    driver.get("http://localhost/litecart/admin/logout.php");
  }
}
