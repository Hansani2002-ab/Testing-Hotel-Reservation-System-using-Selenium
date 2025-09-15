package instragram;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;

public class postWithoutImageTest {

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\ASUS\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");

        // Setup ChromeOptions
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.setExperimentalOption("useAutomationExtension", false);
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 Chrome/114 Safari/537.36");

        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://www.instagram.com/");

        try {
            // Login
            driver.findElement(By.name("username")).sendKeys("hansani4040");
            driver.findElement(By.name("password")).sendKeys("hansi@2002" + Keys.ENTER);
            Thread.sleep(8000);

            // Check login failure
            if (driver.getPageSource().contains("Sorry, your password was incorrect")) {
                System.out.println("❌ Login failed: Incorrect username or password.");
                return;
            }

            // Dismiss popups
            try {
                driver.findElement(By.xpath("//button[text()='Not Now']")).click();
                Thread.sleep(2000);
            } catch (Exception ignored) {}

            try {
                driver.findElement(By.xpath("//button[text()='Not Now']")).click();
                Thread.sleep(2000);
            } catch (Exception ignored) {}

            // Click New Post button
            WebElement newPostButton = driver.findElement(By.cssSelector("svg[aria-label='New post']"));
            newPostButton.click();
            Thread.sleep(3000);

            // Try to click "Next" without uploading an image
            WebElement nextButton = driver.findElement(By.xpath("//div[text()='Next' and @role='button']"));

            if (nextButton.isEnabled()) {
                System.out.println("❌ ERROR: 'Next' button is enabled without selecting an image!");

                // Save screenshot to desktop
                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                Files.copy(screenshot.toPath(), Paths.get("C:\\Users\\ASUS\\Desktop\\next_enabled_without_image.png"));
                System.out.println("⚠ Screenshot saved to Desktop: next_enabled_without_image.png");
            } else {
                System.out.println("✅ Test Passed: 'Next' button is disabled when no image is selected.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            try {
                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                Files.copy(screenshot.toPath(), Paths.get("C:\\Users\\ASUS\\Desktop\\error_screenshot.png"));
                System.out.println("⚠ Error screenshot saved to Desktop: error_screenshot.png");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } finally {
            driver.quit();
        }
    }
}
