package com.zhukov.training.POM;

import com.zhukov.training.Application;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeSuite;

public class BasePOMTest {

  protected WebDriver webDriver;

  protected static Application app;

  @BeforeSuite
  public void setUp() {
    app = new Application(webDriver);
    app.start();
  }

  @AfterClass(alwaysRun = true)
  public void tearDown() {
    app.stop();
  }

}
