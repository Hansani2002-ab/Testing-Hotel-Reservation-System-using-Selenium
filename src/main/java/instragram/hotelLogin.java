package instragram;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;

public class hotelLogin {

    public static void main(String[] args) {
        // Set path to ChromeDriver
        System.setProperty("webdriver.chrome.driver", "C:\\Drivers\\chromedriver.exe");

        // Chrome options
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.setExperimentalOption("useAutomationExtension", false);
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 Chrome/114 Safari/537.36");

        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://adactinhotelapp.com/");

        try {
            // Login
            driver.findElement(By.name("username")).sendKeys("Hansani123");
            driver.findElement(By.name("password")).sendKeys("D2OUS2" + Keys.ENTER);
            Thread.sleep(8000);

            // Check login failure
            if (driver.getPageSource().contains("Sorry, your password was incorrect")) {
                System.out.println("❌ Login failed: Incorrect username or password.");
                return;
            }

            // Dismiss popups if appear
            try {
                driver.findElement(By.xpath("//button[text()='Not Now']")).click();
                Thread.sleep(2000);
            } catch (Exception ignored) {}

            try {
                driver.findElement(By.xpath("//button[text()='Not Now']")).click();
                Thread.sleep(2000);
            } catch (Exception ignored) {}

            // Take screenshot after successful login
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.copy(screenshot.toPath(), Paths.get("C:\\Users\\ASUS\\Desktop\\login_success.png"));
            System.out.println("✅ Login successful! Screenshot saved to Desktop as login_success.png");

        } catch (Exception e) {
            e.printStackTrace();
            try {
                // If error, take screenshot
                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                Files.copy(screenshot.toPath(), Paths.get("C:\\Users\\ASUS\\Desktop\\login_error.png"));
                System.out.println("⚠ Error occurred. Screenshot saved to Desktop as login_error.png");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } finally {
            driver.quit();
        }
    }
}
