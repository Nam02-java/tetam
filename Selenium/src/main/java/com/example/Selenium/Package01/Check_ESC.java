package com.example.Selenium.Package01;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Check_ESC implements Runnable {
    private WebDriver driver;
    private WebDriverWait wait;

    private CountDownLatch countDownLatch;
    private List<WebElement> element_solve;


    public Check_ESC(WebDriver driver, WebDriverWait wait,CountDownLatch countDownLatch) {
        this.driver = driver;
        this.wait = wait;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        try { // nút ESC
            System.out.println("Xem xét nút ESC");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[1]/div[1]/small")));
            element_solve = driver.findElements(By.xpath("/html/body/div[1]/div[1]/small"));
            if (element_solve.size() > 0 && element_solve.get(0).isDisplayed()) {
                System.out.println("Nút ESC hiển thị ");
                driver.findElement(By.xpath("/html/body/div[1]/div[1]/small")).click();
            }
            countDownLatch.countDown();
        } catch (Exception exception) {
            countDownLatch.countDown();
            System.out.println("không hiển thị nút ESC");
        }
    }
}

