package instragram;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;

public class unsupportedFileUploadTest {

    public static void main(String[] args) {
        // Set path to ChromeDriver
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\ASUS\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");

        // Chrome Options
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

            // Click New Post
            WebElement newPostButton = driver.findElement(By.cssSelector("svg[aria-label='New post']"));
            newPostButton.click();
            Thread.sleep(3000);

            // Attempt to upload unsupported file (.txt)
            WebElement fileInput = driver.findElement(By.xpath("//input[@type='file']"));
            fileInput.sendKeys(new File("C:\\Users\\ASUS\\Desktop\\testfile.txt").getAbsolutePath()); // Replace with actual .txt file path
            Thread.sleep(5000); // Wait to see if anything happens

            // Take screenshot of the result
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.copy(screenshot.toPath(), Paths.get("C:\\Users\\ASUS\\Desktop\\unsupported_file_upload.png"));
            System.out.println("📸 Screenshot saved: unsupported_file_upload.png");

        } catch (Exception e) {
            System.out.println("❌ Error during unsupported file upload test.");
            e.printStackTrace();
            try {
                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                Files.copy(screenshot.toPath(), Paths.get("C:\\Users\\ASUS\\Desktop\\unsupported_file_upload_error.png"));
                System.out.println("⚠ Screenshot saved to Desktop as unsupported_file_upload_error.png");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } finally {
            driver.quit();
        }
    }
}
