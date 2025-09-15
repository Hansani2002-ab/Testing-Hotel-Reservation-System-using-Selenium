package instragram;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;

public class newPostButtonTest {

    public static void main(String[] args) {
        // Set ChromeDriver path
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\ASUS\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");

        // Chrome options to avoid detection
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.setExperimentalOption("useAutomationExtension", false);
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 Chrome/114 Safari/537.36");

        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://www.instagram.com/");

        try {
            // Login
            driver.findElement(By.name("username")).sendKeys("hansani4040");
            driver.findElement(By.name("password")).sendKeys("hansi@2002" + Keys.ENTER);
            Thread.sleep(8000);

            // Dismiss popups
            try {
                driver.findElement(By.xpath("//button[text()='Not Now']")).click();
                Thread.sleep(2000);
            } catch (Exception ignored) {}
            try {
                driver.findElement(By.xpath("//button[text()='Not Now']")).click();
                Thread.sleep(2000);
            } catch (Exception ignored) {}

            // Click the New Post (+) button
            WebElement newPostButton = driver.findElement(By.cssSelector("svg[aria-label='New post']"));
            newPostButton.click();
            Thread.sleep(3000);

            // Check if post creation popup appears (look for "Select from computer" text)
            boolean popupVisible = driver.getPageSource().contains("Select from computer");
            if (popupVisible) {
                // Take success screenshot
                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                Files.copy(screenshot.toPath(), Paths.get("C:\\Users\\ASUS\\Desktop\\new_post_popup.png"));
                System.out.println("✅ Post creation popup appeared. Screenshot saved to Desktop as new_post_popup.png");
            } else {
                System.out.println("❌ Post creation popup not detected.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            try {
                // Save screenshot if error occurs
                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                Files.copy(screenshot.toPath(), Paths.get("C:\\Users\\ASUS\\Desktop\\new_post_popup_error.png"));
                System.out.println("⚠ Error screenshot saved to Desktop as new_post_popup_error.png");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } finally {
            driver.quit();
        }
    }
}
