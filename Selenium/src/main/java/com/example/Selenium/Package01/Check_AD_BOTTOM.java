package com.example.Selenium.Package01;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class Check_AD_BOTTOM implements Runnable {
    private WebDriver driver;
    private List<WebElement> element_solve;

    public Check_AD_BOTTOM(WebDriver driver) {
        this.driver = driver;
    }

    @Override
    public void run() {
        element_solve = driver.findElements(By.xpath("(//img[@title='Ad.Plus Advertising'])[1]"));
        if (element_solve.size() > 0 && element_solve.get(0).isDisplayed()) {
            System.out.println("ad 2 displayed");
            driver.findElement(By.xpath("(//img[@title='Ad.Plus Advertising'])[1]")).click();
        }
    }
}
