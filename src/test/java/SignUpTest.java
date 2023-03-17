import com.fasterxml.jackson.annotation.JsonTypeInfo;
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

public class SignUpTest {

    @Test
    public void signUp() {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("http://www.kurs-selenium.pl/demo/");

        int randomNumber = (int) (Math.random()*1000);
        String  email = "tester" + randomNumber + "@tester.pl";
        String lastName = "Testowy";
        driver.findElements(By.xpath("//li[@id='li_myaccount']")).stream().filter(WebElement::isDisplayed).findFirst().ifPresent(WebElement::click);
        driver.findElements(By.xpath("//a[text()='  Sign Up']")).get(1).click();

        driver.findElement(By.name("firstname")).sendKeys("Bartek");
        driver.findElement(By.name("lastname")).sendKeys("Testowy");
        driver.findElement(By.name("phone")).sendKeys("111111111");
        driver.findElement(By.name("email")).sendKeys(email);
        driver.findElement(By.name("password")).sendKeys("Test123");
        driver.findElement(By.name("confirmpassword")).sendKeys("Test123");
        driver.findElement(By.xpath("//button[@type='submit']")).click();


//        WebElement h3 = driver.findElement(By.xpath("//h3[text()='Hi, Bartek Testowy']"));
        String  h3 = driver.findElement(By.xpath("//h3[@class='RTL']")).getText();

        Assert.assertTrue(h3.contains(lastName));
        Assert.assertFalse(false, h3);
        Assert.assertEquals(h3, "Hi, Bartek Testowy");

        driver.quit();

    }

    @Test
    public void signUpWithEmptyFields() {
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

        driver.quit();

    }

    @Test
    public void signUpWithInvalidEmail() {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("http://www.kurs-selenium.pl/demo/");


        driver.findElements(By.xpath("//li[@id='li_myaccount']")).stream().filter(WebElement::isDisplayed).findFirst().ifPresent(WebElement::click);
        driver.findElements(By.xpath("//a[text()='  Sign Up']")).get(1).click();

        driver.findElement(By.name("firstname")).sendKeys("Bartek");
        driver.findElement(By.name("lastname")).sendKeys("Testowy");
        driver.findElement(By.name("phone")).sendKeys("111111111");
        driver.findElement(By.name("email")).sendKeys("tester.");
        driver.findElement(By.name("password")).sendKeys("Test123");
        driver.findElement(By.name("confirmpassword")).sendKeys("Test123");
        driver.findElement(By.xpath("//button[@type='submit']")).click();

        WebElement error = driver.findElement(By.xpath("//div[@class='resultsignup']//p"));

        Assert.assertTrue(error.isDisplayed(), "The Email field must contain a valid email address.");
        Assert.assertEquals(error.getText(), "The Email field must contain a valid email address.");

        driver.quit();

    }
}
