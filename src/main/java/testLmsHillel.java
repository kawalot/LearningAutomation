import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by kawalot on 18.11.2016.
 */
public class testLmsHillel {

    static WebDriver driver;
    public static void sleep(int time) {
        try{
            Thread.sleep(time * 1000);
        }
        catch (InterruptedException e)
        {}
    }

    @BeforeMethod
    public static void prepare(){
        System.setProperty("webdriver.chrome.driver","bin/chromedriver.exe");
        driver = new ChromeDriver();
    }

    @AfterMethod
    public static void quit(){
        driver.quit();
    }

    @Test
    public static void loginTest(){
        login(true);
    }

    @Test
    public static void qamanualTest(){
        login(false);
        qamanual(true);
    }

    @Test
    public static void mindmapTest(){
        login(false);
        qamanual(false);
        mindmap(true);
    }

    @Test
    public static void exitTest(){
        login(false);
        exit(true);
    }

    @Test
    public static void failTest(){
        login(false);
        exit(true);
        fail(true);
    }

    @Test
    public static void failTestTwo(){
        login(false);
        exit(false);
        mindmap(true);
    }

    public static void login(boolean check){
        driver.get("http://lms.it-hillel.com.ua/");
        driver.findElement(By.cssSelector("#username")).sendKeys("k****@gmail.com");
        driver.findElement(By.cssSelector("#password")).sendKeys("newp*****");
        driver.findElement(By.cssSelector("#loginbtn")).click();
        sleep(1);
        if (check){
            Assert.assertTrue(isExist("i[class='fa fa-envelope-o']"), "Login Failed");
        }
    }

    public static void qamanual(boolean check){
        driver.findElement(By.cssSelector("a[title='QA Manual | Odessa | 19:00 | 08.2016']")).click();
        sleep(1);
        if(check) {
            Assert.assertTrue(isExist("a[href*='id=1697']"), "Can't open QA Course");
        }
    }

    public static void mindmap(boolean check){
        driver.findElement(By.cssSelector("a[href*='id=1875']")).click();
        sleep(1);
        if(check) {
            Assert.assertTrue(isExist("img[title='Mind Map Example']"), "There is no image Mind Map Example");
        }
    }

    public static void exit(boolean check){
        WebElement element = driver.findElement(By.cssSelector("a[href='#']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).build().perform();
        sleep(2);
        driver.findElement(By.cssSelector("a[href*='logout']")).click();
        sleep(2);
        if(check){

            Assert.assertTrue(isExist("#loginbtn"), "Did't exit");
        }
    }

    public static void fail(boolean check){
        driver.findElement(By.cssSelector("a[href*='about.me']")).click();
        sleep(4);
        if(check){
            Assert.assertTrue(isExist("span[class*='spotlight']"), "Fail Test");
        }
    }

    public static boolean isExist(String Selector){
        try{
        driver.findElement(By.cssSelector(Selector)).isDisplayed();
            return true;
        }
        catch (NoSuchElementException e){
            return false;
        }
    }


}
