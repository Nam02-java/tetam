package com.example.Selenium.Package01;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.CountDownLatch;
;

public class Check_HostAD implements Runnable {
    private WebDriver driver;
    private WebDriverWait wait;

    private List<WebElement> element_solve;

    private CountDownLatch countDownLatch;


    public Check_HostAD(WebDriver driver, WebDriverWait wait, CountDownLatch countDownLatch) {
        this.driver = driver;
        this.wait = wait;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        try { // quảng cáo host 8 9
            System.out.println("Xem xét quảng cáo của host ");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//iframe[contains(@style,'width: 100vw')]")));
            element_solve = driver.findElements(By.xpath("//iframe[contains(@style,'width: 100vw')]"));
            if (element_solve.size() > 0 && element_solve.get(0).isDisplayed()) {
                System.out.println("Quảng của host có hiển thị ");
                WebElement frame1 = driver.findElement(By.xpath("//iframe[contains(@style,'width: 100vw')]"));
                driver.switchTo().frame(frame1);
                driver.findElement(By.xpath("//div[@aria-label='Đóng quảng cáo']")).click();
                System.out.println("da tat host");
                driver.switchTo().defaultContent(); // return default content
            }
            countDownLatch.countDown();
        } catch (Exception exception) {
            countDownLatch.countDown();
            System.out.println("Không hiển thị quảng cáo của host ");
        }
    }
}

//    @Override
//    public void run() {
//        try { // quảng cáo host 8 9
//            System.out.println("Xem xét quảng cáo của host ");
//            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//iframe[contains(@style,'width: 100vw')]")));
//            element_solve = driver.findElements(By.xpath("//iframe[contains(@style,'width: 100vw')]"));
//            if (element_solve.size() > 0 && element_solve.get(0).isDisplayed()) {
//                System.out.println("Quảng của host có hiển thị ");
//                WebElement frame1 = driver.findElement(By.xpath("//iframe[contains(@style,'width: 100vw')]"));
//                driver.switchTo().frame(frame1);
//                List<WebElement> list = driver.findElements(By.xpath("//div[@aria-label='Đóng quảng cáo']"));
//                if (list.size() > 0) {
//                    driver.findElement(By.xpath("//div[@aria-label='Đóng quảng cáo']")).click();
//                    System.out.println("da tat host");
//                }
//            }
//            countDownLatch.countDown();
//        } catch (Exception exception) {
//            countDownLatch.countDown();
//            System.out.println("Không hiển thị quảng cáo của host ");
//        }
//    }
//}