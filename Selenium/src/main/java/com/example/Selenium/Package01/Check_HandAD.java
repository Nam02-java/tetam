package com.example.Selenium.Package01;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Check_HandAD implements Runnable {
    private WebDriver driver;
    private WebDriverWait wait;

    private List<WebElement> element_solve;

    private CountDownLatch countDownLatch;


    public Check_HandAD(WebDriver driver, WebDriverWait wait, CountDownLatch countDownLatch) {
        this.driver = driver;
        this.wait = wait;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        try { //bàn tay quảng cáo ( không có Iframe , tắt trực tiếp được luôn )
            System.out.println("Xem xét quảng cáo bàn tay");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@aria-modal='true']")));
            element_solve = driver.findElements(By.xpath("//div[@aria-modal='true']"));
            if (element_solve.size() > 0 && element_solve.get(0).isDisplayed()) {
                System.out.println("Bàn tay quảng cáo có hiển thị");

                WebElement closeButton = driver.findElement(By.xpath("//button[@aria-label='Close this dialog']"));
                JavascriptExecutor executor = (JavascriptExecutor) driver;
                executor.executeScript("arguments[0].click();", closeButton);
                driver.switchTo().defaultContent();

            }
            countDownLatch.countDown();
        } catch (Exception exception) {
            countDownLatch.countDown();
            System.out.println("Không hiển thị bàn tay quảng cáo");
            System.out.println(exception);
        }
    }
}

