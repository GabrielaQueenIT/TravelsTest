import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class EmptySignUpTest {

    @Test
    public void signUp() {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("http://www.kurs-selenium.pl/demo/");

        driver.findElements(By.xpath("//li[@id='li_myaccount']")).stream().filter(WebElement::isDisplayed).findFirst().ifPresent(WebElement::click);
        driver.findElements(By.xpath("//a[text()='  Sign Up']")).get(1).click();
        driver.findElement(By.xpath("//button[@type='submit']")).click();

        List<String> results = driver.findElements(By.xpath("//div[@class='resultsignup']//p")).stream().map(WebElement::getText).collect(Collectors.toList());

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(results.contains("The Email field is required."));
        softAssert.assertTrue(results.contains("The Password field is required."));
        softAssert.assertTrue(results.contains("The Password field is required."));
        softAssert.assertTrue(results.contains("The First name field is required."));
        softAssert.assertTrue(results.contains("The Last Name field is required."));
        softAssert.assertAll();


    }
}
