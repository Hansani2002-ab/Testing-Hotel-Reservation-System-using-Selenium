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

public class BookMakingTest {

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

            // Search hotel
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("location")));
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

            driver.findElement(By.id("Submit")).click();
            System.out.println("✅ Form submitted");

            // Select Hotel
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("radiobutton_0")));
            driver.findElement(By.id("radiobutton_0")).click();
            driver.findElement(By.id("continue")).click();
            System.out.println("✅ Hotel selected and continued");

            // Book a hotel page
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("first_name")));

            driver.findElement(By.id("first_name")).sendKeys("Hansani");
            driver.findElement(By.id("last_name")).sendKeys("Madurangi");
            driver.findElement(By.id("address")).sendKeys("83/1, sdswscdxscscsdcsacascss");
            driver.findElement(By.id("cc_num")).sendKeys("1235464711122223");

            Select cardType = new Select(driver.findElement(By.id("cc_type")));
            cardType.selectByVisibleText("VISA");

            Select expMonth = new Select(driver.findElement(By.id("cc_exp_month")));
            expMonth.selectByVisibleText("November");

            Select expYear = new Select(driver.findElement(By.id("cc_exp_year")));
            expYear.selectByVisibleText("2026");

            driver.findElement(By.id("cc_cvv")).sendKeys("1234");

            driver.findElement(By.id("book_now")).click();
         // Wait for final fields to load before screenshot
           

            System.out.println("📄 Booking submitted");

            // Wait for confirmation page
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("order_no")));

            // Take screenshot of confirmation page
            File confirmPageScreenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.copy(confirmPageScreenshot.toPath(), Paths.get("C:\\Users\\ASUS\\Desktop\\confirmation_page.png"));
            System.out.println("📸 Screenshot of booking confirmation saved");

            // Print the Order Number
            String orderNo = driver.findElement(By.id("order_no")).getAttribute("value");
            System.out.println("✅ Booking Successful! Order Number: " + orderNo);

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
