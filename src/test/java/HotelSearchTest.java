import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.Collectors;

public class HotelSearchTest extends BaseTest{


    @Test
    public void searchHotelTest() throws InterruptedException {


        driver.findElement(By.xpath("//span[text()='Search by Hotel or City Name']")).click();
        driver.findElement(By.xpath("//div[@id='select2-drop']//input")).sendKeys("Dubai");
        driver.findElement(By.xpath("//span[@class='select2-match' and text()='Dubai']")).click();



        driver.findElement(By.name("checkin")).sendKeys("17/03/2023");
//        driver.findElement(By.name("checkout")).sendKeys("20/03/2023");

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
        List<String> hotelNames = driver.findElements(By.xpath("//h4[contains(@class, 'list_title')]//b")).stream()
                .map(el->el.getAttribute("textContent")) //zmienione z .getText() bo chcemy poczekać na wszystkie hotele
                .collect(Collectors.toList());

//        System.out.println(hotelNames.size());


//        for(String hotel : hotelNames) {
//            System.out.println(hotel);
//        }


        Assert.assertEquals("Jumeirah Beach Hotel", hotelNames.get(0));
        Assert.assertEquals("Oasis Beach Tower", hotelNames.get(1));
        Assert.assertEquals("Rose Rayhaan Rotana", hotelNames.get(2));
        Assert.assertEquals("Hyatt Regency Perth", hotelNames.get(3));


    }

    @Test
    public void searchWithoutHotelTest() {

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
