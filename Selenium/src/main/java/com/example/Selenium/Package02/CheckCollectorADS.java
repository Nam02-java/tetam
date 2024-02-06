package com.example.Selenium.Package02;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class CheckCollectorADS implements Runnable {
    private WebDriver driver;
    private CountDownLatch countDownLatch;
    private List<WebElement> element_solve;
    private WebDriverWait wait;

    public CheckCollectorADS(WebDriver driver, CountDownLatch countDownLatch) {
        this.driver = driver;
        this.countDownLatch = countDownLatch;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public CheckCollectorADS(WebDriver driver, CountDownLatch countDownLatch, WebDriverWait wait) {
        this.driver = driver;
        this.countDownLatch = countDownLatch;
        this.wait = wait;
    }

    @Override
    public void run() {
        if (wait != null) {
            //  logic for the second constructor
            System.out.println("logic for the second constructor of HAND_AD");
            try {
                Thread.sleep(2000);
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@class='grippy-host'])[1]"))).click();
                Thread.sleep(2000);
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[1]/div[1]/small"))).click();
                Thread.sleep(2000);
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='×']"))).click();
            } catch (Exception exception) {
                System.out.println(exception);
            }

        } else {
            // logic for the first constructor
            System.out.println("logic for the first constructor of ADsTOP_ESC");
            try {
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ins[@data-anchor-status='displayed']"))).click();
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[1]/div[1]/small"))).click();
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='×']"))).click();
            } catch (Exception exception) {
                System.out.println(exception);
            }
        }
        countDownLatch.countDown();
    }
}

