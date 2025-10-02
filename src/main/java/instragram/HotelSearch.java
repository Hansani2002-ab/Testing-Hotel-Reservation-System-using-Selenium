package instragram;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;

public class HotelSearch {

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "C:\\Drivers\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.setExperimentalOption("useAutomationExtension", false);
        options.addArguments("user-agent=Mozilla/5.0");

        WebDriver driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();

        try {
            driver.get("https://adactinhotelapp.com/");

            // Login
            driver.findElement(By.name("username")).sendKeys("Hansani123");
            driver.findElement(By.name("password")).sendKeys("D2OUS2" + Keys.ENTER);
            Thread.sleep(3000);

            // Ensure search form is loaded
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("location")));

            // Fill form
            new Select(driver.findElement(By.id("location"))).selectByVisibleText("Sydney");
            new Select(driver.findElement(By.id("hotels"))).selectByVisibleText("Hotel Creek");
            new Select(driver.findElement(By.id("room_type"))).selectByVisibleText("Standard");
            new Select(driver.findElement(By.id("room_nos"))).selectByVisibleText("1 - One");

            WebElement checkIn = driver.findElement(By.id("datepick_in"));
            checkIn.clear();
            checkIn.sendKeys("16/09/2025");

            WebElement checkOut = driver.findElement(By.id("datepick_out"));
            checkOut.clear();
            checkOut.sendKeys("17/09/2025");

            new Select(driver.findElement(By.id("adult_room"))).selectByVisibleText("1 - One");
            new Select(driver.findElement(By.id("child_room"))).selectByVisibleText("0 - None");

            // Submit the form
            driver.findElement(By.id("Submit")).click();
            System.out.println("✅ Form submitted");

            // Wait for next page (Select Hotel) to load
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("radiobutton_0")));
            System.out.println("✅ Reached the hotel selection page");

            // Take screenshot of next page
            File nextPageScreenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.copy(nextPageScreenshot.toPath(), Paths.get("C:\\Users\\ASUS\\Desktop\\next_page.png"));
            System.out.println("📸 Screenshot saved of next page (hotel selection)");

            // Optional: Select first hotel
            driver.findElement(By.id("radiobutton_0")).click();
            driver.findElement(By.id("continue")).click();
            System.out.println("✅ Hotel selected and continued");

        } catch (Exception e) {
            e.printStackTrace();
            try {
                File errorScreenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                Files.copy(errorScreenshot.toPath(), Paths.get("C:\\Users\\ASUS\\Desktop\\error.png"));
                System.out.println("⚠ Error occurred. Screenshot saved to Desktop as error.png");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } finally {
            driver.quit();
        }
    }
}
