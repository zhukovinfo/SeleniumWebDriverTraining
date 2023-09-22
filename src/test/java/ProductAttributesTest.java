import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
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

      String originalRegularColorCode = originalRegularPrice.getCssValue("color");
      softAssertions.assertThat(isGrayColor(originalRegularColorCode))
          .describedAs("Проверка, что обычная цена на главной странице серая")
          .isTrue();

      softAssertions.assertThat(originalCampaignPrice.getTagName())
          .describedAs("Проверка, что акционная цена на главной странице жирная")
          .isEqualTo("strong");

      String originalCampaignColorCode = originalCampaignPrice.getCssValue("color");
      softAssertions.assertThat(isRedColor(originalCampaignColorCode))
          .describedAs("Проверка, что акционная цена на главной странице красная")
          .isTrue();

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

      String actualRegularPriceColor = actualRegularPrice.getCssValue("color");
      softAssertions.assertThat(isGrayColor(actualRegularPriceColor))
          .describedAs("Проверка, что обычная цена на странице товара серая")
          .isTrue();

      softAssertions.assertThat(actualCampaignPrice.getTagName())
          .describedAs("Проверка, что акционная цена на странице товара жирная")
          .isEqualTo("strong");

      String  actualCampaignPriceColor = actualCampaignPrice.getCssValue("color");
      softAssertions.assertThat(isRedColor(actualCampaignPriceColor))
          .describedAs("Проверка, что акционная цена на странице товара красная")
          .isTrue();

      String actualCampaignPriceFontSize = actualCampaignPrice.getCssValue("font-size");
      String actualRegularPriceFontSize = actualRegularPrice.getCssValue("font-size");
      softAssertions.assertThat(Float.parseFloat(actualCampaignPriceFontSize
              .substring(0, actualRegularPriceFontSize.length() - 2)))
          .describedAs("Проверка, что акционная цена на главной странице крупнее, чем обычная")
          .isGreaterThan(Float.parseFloat(actualRegularPriceFontSize
              .substring(0, actualCampaignPriceFontSize.length() - 2)));
    });
  }

  private boolean isGrayColor(String colorCode) {
    return getRGBValues(colorCode).stream()
        .distinct()
        .count() == 1;
  }

  private boolean isRedColor(String colorCode) {
    List<Integer> values = getRGBValues(colorCode);

    return values.get(1).equals(values.get(2));
  }

  private List<Integer> getRGBValues(String colorCode) {
    String value;
    if (colorCode.startsWith("rgba(")) {
      value = removeRgbaSymbols(colorCode, "rgba(", ", 1)");
    } else if (colorCode.contains("rgb(")) {
      value = removeRgbaSymbols(colorCode, "rgb(", ")");
    } else {
      throw new IllegalArgumentException("Color code format undefined");
    }
    return Arrays.stream(value.split(","))
        .mapToInt(Integer::parseInt)
        .boxed()
        .collect(Collectors.toList());
  }

  private String removeRgbaSymbols(String colorCode, String prefix, String postfix) {
    return colorCode
        .replace(prefix, "")
        .replace(postfix, "")
        .replaceAll("\\s+", "");
  }

}
