import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

public class ProductAttributesTest extends BaseTest {

  @Test
  public void productAttributesTest() {
    SoftAssertions.assertSoftly(softAssertions -> {
      webDriver.get("http://localhost/litecart/en/");
      WebElement product = webDriver.findElement(By.id("box-campaigns"))
          .findElements(By.xpath(".//li[contains(@class,'product')]"))
          .get(0)
          .findElement(By.className("link"));

      String originalName = product.findElement(By.className("name")).getText();
      WebElement originalRegularPrice = product.findElement(By.className("regular-price"));
      WebElement originalCampaignPrice = product.findElement(By.className("campaign-price"));

      softAssertions.assertThat(originalRegularPrice.getTagName())
          .describedAs("Проверка, что обычная цена на главной странице зачёркнутая")
          .isEqualTo("s");

      softAssertions.assertThat(originalRegularPrice.getCssValue("color"))
          .describedAs("Проверка, что обычная цена на главной странице серая")
          .contains("119, 119, 119");

      softAssertions.assertThat(originalCampaignPrice.getTagName())
          .describedAs("Проверка, что акционная цена на главной странице жирная")
          .isEqualTo("strong");

      softAssertions.assertThat(originalCampaignPrice.getCssValue("color"))
          .describedAs("Проверка, что акционная цена на главной странице красная")
          .contains("204, 0, 0");

      String originalCampaignPriceFontSize = originalCampaignPrice.getCssValue("font-size");
      String originalRegularPriceFontSize = originalRegularPrice.getCssValue("font-size");
      softAssertions.assertThat(Float.parseFloat(originalCampaignPriceFontSize
              .substring(0, originalCampaignPriceFontSize.length() - 2)))
          .describedAs("Проверка, что акционная цена на главной странице крупнее, чем обычная")
          .isGreaterThan(Float.parseFloat(originalRegularPriceFontSize
              .substring(0, originalRegularPriceFontSize.length() - 2)));

      String originalRegularPriceText = originalRegularPrice.getText();
      String originalCampaignPriceText = originalCampaignPrice.getText();

      product.click();

      WebElement actualProduct = webDriver.findElement(By.id("box-product"));
      String actualName = actualProduct.findElement(By.className("title")).getText();
      WebElement actualRegularPrice = actualProduct.findElement(By.className("regular-price"));
      WebElement actualCampaignPrice = actualProduct.findElement(By.className("campaign-price"));

      softAssertions.assertThat(actualName)
          .describedAs("Проверка, что на главной странице и на странице товара совпадает текст названия "
              + "товара")
          .isEqualTo(originalName);

      softAssertions.assertThat(actualRegularPrice.getText())
          .describedAs("Проверка, что на главной странице и на странице товара совпадают цены (обычная)")
          .isEqualTo(originalRegularPriceText);

      softAssertions.assertThat(actualCampaignPrice.getText())
          .describedAs("Проверка, что на главной странице и на странице товара совпадают цены (акционная)")
          .isEqualTo(originalCampaignPriceText);

      softAssertions.assertThat(actualRegularPrice.getTagName())
          .describedAs("Проверка, что обычная цена на странице товара зачёркнутая")
          .isEqualTo("s");

      softAssertions.assertThat(actualRegularPrice.getCssValue("color"))
          .describedAs("Проверка, что обычная цена на странице товара серая")
          .contains("102, 102, 102");

      softAssertions.assertThat(actualCampaignPrice.getTagName())
          .describedAs("Проверка, что акционная цена на странице товара жирная")
          .isEqualTo("strong");

      softAssertions.assertThat(actualCampaignPrice.getCssValue("color"))
          .describedAs("Проверка, что акционная цена на странице товара красная")
          .contains("204, 0, 0");

      String actualCampaignPriceFontSize = actualCampaignPrice.getCssValue("font-size");
      String actualRegularPriceFontSize = actualRegularPrice.getCssValue("font-size");
      softAssertions.assertThat(Float.parseFloat(actualCampaignPriceFontSize
              .substring(0, actualRegularPriceFontSize.length() - 2)))
          .describedAs("Проверка, что акционная цена на главной странице крупнее, чем обычная")
          .isGreaterThan(Float.parseFloat(actualRegularPriceFontSize
              .substring(0, actualCampaignPriceFontSize.length() - 2)));
    });
  }

}
