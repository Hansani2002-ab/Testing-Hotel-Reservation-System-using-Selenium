package instragram;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;

public class longCaptionPostTest {

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

            // Click New Post
            WebElement newPostButton = driver.findElement(By.cssSelector("svg[aria-label='New post']"));
            newPostButton.click();
            Thread.sleep(3000);

            // Upload image
            WebElement fileInput = driver.findElement(By.xpath("//input[@type='file']"));
            fileInput.sendKeys(new File("C:\\Users\\ASUS\\Desktop\\burger.jpg").getAbsolutePath());
            Thread.sleep(5000);

            // Click Next
            driver.findElement(By.xpath("//div[text()='Next' and @role='button']")).click();
            Thread.sleep(3000);

            // Click Next again
            driver.findElement(By.xpath("//div[text()='Next' and @role='button']")).click();
            Thread.sleep(3000);

            // Enter very long caption (2,200 characters)
            String longCaption = "A".repeat(2200);
            WebElement caption = driver.findElement(By.xpath("//div[@role='textbox' and @aria-label='Write a caption...']"));
            caption.sendKeys(longCaption);
            Thread.sleep(2000);

            // Share post
            WebElement shareButton = driver.findElement(By.xpath("//div[text()='Share' and @role='button']"));
            shareButton.click();
            Thread.sleep(12000); // Wait for post

            // Save success screenshot
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.copy(screenshot.toPath(), Paths.get("C:\\Users\\ASUS\\Desktop\\long_caption_success_20250716_153508.png"));
            System.out.println("✅ Post uploaded with long caption. Screenshot saved to Desktop.");

        } catch (Exception e) {
            e.printStackTrace();
            try {
                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                Files.copy(screenshot.toPath(), Paths.get("C:\\Users\\ASUS\\Desktop\\long_caption_error.png"));
                System.out.println("⚠ Error occurred. Screenshot saved to Desktop.");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } finally {
            driver.quit();
        }
    }
}
