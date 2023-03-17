import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;


import java.util.concurrent.TimeUnit;

public class SearchWithoutHotel {

    @Test
    public void searchWithoutHotel() {

        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://www.kurs-selenium.pl/demo/");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(By.name("checkin")).sendKeys("17/03/2023");
        driver.findElement(By.name("checkout")).click();
        driver.findElements(By.xpath("//td[@class='day ' and text()='20']"))
                .stream()
                .filter(WebElement::isDisplayed) //to jest method refferences i używamy zamiast el -> el.isDisplayed()
                .findFirst()
                .ifPresent(WebElement::click);
        driver.findElement(By.id("travellersInput")).click();

        driver.findElement(By.id("adultPlusBtn")).click();
        driver.findElement(By.id("childPlusBtn")).click();
        driver.findElement(By.xpath("//button[text()=' Search']")).click();


        WebElement result = driver.findElement(By.xpath("//div[@class='itemscontainer']//h2"));

        Assert.assertEquals(result.getText(), "No Results Found");
        Assert.assertTrue(result.isDisplayed());





    }
}
