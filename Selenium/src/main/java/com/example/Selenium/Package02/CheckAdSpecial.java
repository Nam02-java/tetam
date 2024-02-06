package com.example.Selenium.Package02;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class CheckAdSpecial implements Runnable {
    private WebDriver driver;
    private WebDriverWait wait;
    private List<WebElement> element_solve;

    private CountDownLatch countDownLatch;

    public CheckAdSpecial(WebDriver driver, CountDownLatch countDownLatch) {
        this.driver = driver;
        this.countDownLatch = countDownLatch;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(3));
    }

    public CheckAdSpecial(WebDriver driver, CountDownLatch countDownLatch, WebDriverWait wait) {
        this.driver = driver;
        this.countDownLatch = countDownLatch;
        this.wait = wait;
    }

    @Override
    public void run() {
        if (wait != null) {
            //  logic for the second constructor
            System.out.println("Logic for the second constructor of special ad ");
            try { // quảng cáo đặc biệt
                System.out.println("Xem xét quảng cáo đặc biệt ");
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"dismiss-button\"]/div/span")));
                element_solve = driver.findElements(By.xpath("//*[@id=\"dismiss-button\"]/div/span"));
                if (element_solve.size() > 0 && element_solve.get(0).isDisplayed()) {
                    System.out.println("Quảng cáo đặc biệt có hiển thị ");
                    driver.findElement(By.xpath("//*[@id=\"dismiss-button\"]/div/span")).click();
                    System.out.println("Quảng cáo đặc biệt đã tắt");
                }
                countDownLatch.countDown();
            } catch (Exception exception) {
                countDownLatch.countDown();
                System.out.println("Quảng cáo đặc biệt :  " + exception);
            }

        } else {
            // logic for the first constructor
        }
    }
}
