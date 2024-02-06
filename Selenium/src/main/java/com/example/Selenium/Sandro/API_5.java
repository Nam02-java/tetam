package com.example.Selenium.Sandro;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.time.Duration;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/web/double-tee")
public class API_5 {

    private String URL_WEBSITE = "https://outerity.com/products/outerity-double-tee-collection-flowers-canoli-cream";

    @GetMapping("/Flowers_sigin")
    public ResponseEntity<?> DJBear_sigin(@RequestParam Map<String, String> params) throws InterruptedException, IOException, TelegramApiException {
        System.setProperty("webdriver.http.factory", "jdk-http-client");
        System.setProperty("webdriver.chrome.driver", "E:\\CongViecHocTap\\ChromeDriver\\chromedriver-win64\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("useAutomationExtension", false);
        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        options.addArguments("--blink-settings=imagesEnabled=false");

        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); // số giây mà 1 driver chờ để load 1 phần tử nếu không có thiết lập của wait
        driver.manage().window().maximize();

        driver.get("https://outerity.com/");

        driver.findElement(By.xpath("(//span[contains(text(),'Đăng nhập / Đăng ký')])[1]")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("(//input[@name='customer[email]'])[1]")).sendKeys("lel501245@gmail.com");
        driver.findElement(By.xpath("(//input[@name='customer[password]'])[1]")).sendKeys("lethile1909");
        driver.findElement(By.xpath("(//button[contains(text(),'Đăng nhập')])[1]")).click();

        driver.get(URL_WEBSITE);

        driver.findElement(By.xpath("(//button[@id='add-to-cart'])[1]")).click();
        driver.findElement(By.xpath("(//a[normalize-space()='Thanh toán'])[1]")).click();

        Thread.sleep(1000);

        driver.findElement(By.xpath("(//input[@id='billing_address_full_name'])[1]")).sendKeys("a");
        driver.findElement(By.xpath("(//input[@id='billing_address_phone'])[1]")).sendKeys("1234567891");
        driver.findElement(By.xpath("(//input[@id='billing_address_address1'])[1]")).sendKeys("vietnam");


        Thread.sleep(1000);
        driver.findElement(By.xpath("(//select[@id='customer_shipping_province'])[1]")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id=\"customer_shipping_province\"]/option[4]")).click();

        Thread.sleep(1000);
        driver.findElement(By.xpath("(//select[@id='customer_shipping_district'])[1]")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id=\"customer_shipping_district\"]/option[4]")).click();

        Thread.sleep(1000);
        driver.findElement(By.xpath("(//select[@id='customer_shipping_ward'])[1]")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id=\"customer_shipping_ward\"]/option[4]")).click();

        driver.findElement(By.xpath("(//button[@class='step-footer-continue-btn btn'])[1]")).click();

        Thread.sleep(1000);
        driver.close();


        return ResponseEntity.ok(new String("Successful payment with login"));
    }

    @GetMapping("/Flowers_NoSigin")
    public ResponseEntity<?> DJBear_NoSigin(@RequestParam Map<String, String> params) throws InterruptedException, IOException, TelegramApiException {
        System.setProperty("webdriver.http.factory", "jdk-http-client");
        System.setProperty("webdriver.chrome.driver", "E:\\CongViecHocTap\\ChromeDriver\\chromedriver-win64\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("useAutomationExtension", false);
        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        options.addArguments("--blink-settings=imagesEnabled=false");

        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); // số giây mà 1 driver chờ để load 1 phần tử nếu không có thiết lập của wait
        driver.manage().window().maximize();

        driver.get(URL_WEBSITE);

        driver.findElement(By.xpath("(//button[@id='add-to-cart'])[1]")).click();
        driver.findElement(By.xpath("(//a[normalize-space()='Thanh toán'])[1]")).click();

        Thread.sleep(1000);

        driver.findElement(By.xpath("(//input[@id='billing_address_full_name'])[1]")).sendKeys("a");
        driver.findElement(By.xpath("(//input[@id='billing_address_phone'])[1]")).sendKeys("1234567891");
        driver.findElement(By.xpath("(//input[@id='billing_address_address1'])[1]")).sendKeys("vietnam");


        Thread.sleep(1000);
        driver.findElement(By.xpath("(//select[@id='customer_shipping_province'])[1]")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id=\"customer_shipping_province\"]/option[4]")).click();

        Thread.sleep(1000);
        driver.findElement(By.xpath("(//select[@id='customer_shipping_district'])[1]")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id=\"customer_shipping_district\"]/option[4]")).click();

        Thread.sleep(1000);
        driver.findElement(By.xpath("(//select[@id='customer_shipping_ward'])[1]")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id=\"customer_shipping_ward\"]/option[4]")).click();

        driver.findElement(By.xpath("(//button[@class='step-footer-continue-btn btn'])[1]")).click();

        Thread.sleep(1000);
        driver.close();

        return ResponseEntity.ok(new String("Successful payment without login"));
    }
}
