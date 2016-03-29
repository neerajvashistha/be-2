import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class mytest3 
{
    

    public static void main(String[] args) {
       

        WebDriver driver = new FirefoxDriver();
        String baseUrl = "http://localhost:8084/plagraism/";
        driver.get(baseUrl);
        String expected = "JSP Page";
        String actual = "";
        driver.manage().window().maximize();
        actual = driver.getTitle();
        if (actual.equals(expected)) {
            System.out.println("Title test passed");
        } else {
            System.out.println("Title test failed");}
            WebElement text=driver.findElement(By.name("File1"));
            text.sendKeys("hello");
            WebElement text1=driver.findElement(By.name("File2"));
            text1.sendKeys("hiee");
           
            WebElement btn=driver.findElement(By.name("btn"));
            btn.click();
            System.out.println(" test script sucessful");
            driver.close();
    
    }
}
