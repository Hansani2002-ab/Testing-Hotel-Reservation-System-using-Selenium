package instragram;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;

public class captionInputTest {

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\ASUS\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");

        // Chrome setup
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

            // Click New Post button
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

            // Enter caption
            WebElement captionBox = driver.findElement(By.xpath("//div[@role='textbox' and @aria-label='Write a caption...']"));
            captionBox.sendKeys("This is an automated test caption.");
            Thread.sleep(2000);

            // Screenshot after caption entered
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.copy(screenshot.toPath(), Paths.get("C:\\Users\\ASUS\\Desktop\\caption_input_success.png"));
            System.out.println("✅ Caption entered successfully. Screenshot saved: caption_input_success.png");

        } catch (Exception e) {
            System.out.println("❌ Error occurred during caption input.");
            e.printStackTrace();
            try {
                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                Files.copy(screenshot.toPath(), Paths.get("C:\\Users\\ASUS\\Desktop\\caption_input_error.png"));
                System.out.println("⚠ Screenshot saved to Desktop: caption_input_error.png");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } finally {
            driver.quit();
        }
    }
}
