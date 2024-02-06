package com.example.Selenium.Package02;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.*;

import java.time.Duration;
import java.util.*;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static com.example.Selenium.Package02.ReadFileNameCSV.FileNameCSV_notification;
import static com.example.Selenium.Package02.ReadFileNameCSV.flag_FileNameCSV;
import static com.example.Selenium.Package02.ReadVoiceCSV.VoiceCSV_notification;
import static com.example.Selenium.Package02.ReadVoiceCSV.flag_VoiceCSV;

import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@RestController
@RequestMapping("/api/web")
public class Selenium extends TelegramLongPollingBot {

    protected static String text = null;
    private static SendMessage message = new SendMessage("1159534870", "");

    private static SendMessage getMessage = new SendMessage("1159534870", "");

    protected static String textTelegram, voiceTelegram, fileNameTelegram;

    protected static String TextCSV, VoiceCSV, FileNameCSV;

    private static CountDownLatch latch = new CountDownLatch(1);

    private static Boolean flag_TextCSV = true;


    @Override
    public void onUpdateReceived(Update update) {
        text = update.getMessage().getText();
    }

    public void sendMessageTelegram(String text) {
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void waitSecond(int second) {
        try {
            Thread.sleep(second);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    @GetMapping("/telegram")
    public ResponseEntity<?> telegram() throws InterruptedException, IOException {
        System.setProperty("webdriver.http.factory", "jdk-http-client");
        System.setProperty("webdriver.chrome.driver", "E:\\CongViecHocTap\\ChromeDriver\\chromedriver-win64\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("useAutomationExtension", false); // disable chrome running as automation
        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation")); // disable chrome running as automation
        options.addArguments("--blink-settings=imagesEnabled=false"); // block tất cả hình ảnh -> tăng tốc độ load website

        WebDriver driver = new ChromeDriver(options);

        driver.get("https://ttsfree.com/login");

        Thread.sleep(2500);

        driver.close();
        LogUtils.logResponse("telegram"); // Gọi hàm log ở đây
        return ResponseEntity.ok(new String("telegram"));
    }


    @Override
    public void onUpdatesReceived(List<Update> updates) {

        super.onUpdatesReceived(updates);
    }

    @Override
    public String getBotUsername() {
        return "CaptchaSlove_bot";
    }

    @Override
    public void onRegister() {
        super.onRegister();
    }

    @Override
    public String getBotToken() {
        return "6928830332:AAGmv3fN_k8YdITzJeOyjqtsDQfWuviF308";
    }

    @RequestMapping("/photo")
    public String SendPhoto() throws TelegramApiException, InterruptedException {
        Thread.sleep(2000);
        String save_image = "E:\\CongViecHocTap\\Captcha\\captcha.png";
        SendPhoto photo = new SendPhoto();
        photo.setChatId("1159534870");
        photo.setPhoto(new InputFile(new File(String.valueOf(save_image))));
        try {
            this.execute(photo);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
        return "Send photo 200ok";
    }

    @GetMapping("/LastAPI")
    public ResponseEntity<?> LastAPI(@RequestParam Map<String, String> params) throws InterruptedException, IOException, TelegramApiException {


        List<WebElement> element_solve;
        String user_name = "nam03test"; // mô phỏng tên user
        String user_password = "IUtrangmaimai02"; // mô phỏng password user
        JavascriptExecutor js;
        WebElement webElement;
        WebDriverWait wait;


        System.setProperty("webdriver.http.factory", "jdk-http-client");
        System.setProperty("webdriver.chrome.driver", "E:\\CongViecHocTap\\ChromeDriver\\chromedriver-win64\\chromedriver.exe");

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setExperimentalOption("useAutomationExtension", false); // disable chrome running as automation
        chromeOptions.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation")); // disable chrome running as automation


        WebDriver driver = new ChromeDriver(chromeOptions);

        CountDownLatch latchCSV = new CountDownLatch(3);
        Thread thread_CSVFileName = new Thread(new ReadFileNameCSV(latchCSV));
        Thread thread_CSVVoice = new Thread(new ReadVoiceCSV(latchCSV));
        Thread thread_CSVText = new Thread(new ReadTextCSV(latchCSV));
        thread_CSVFileName.start();
        thread_CSVVoice.start();
        thread_CSVText.start();
        latchCSV.await();

        if (flag_FileNameCSV == false || flag_VoiceCSV == false) {

            String notification = null;
            if (VoiceCSV_notification != null && FileNameCSV_notification != null) {
                notification = VoiceCSV_notification + "\n" + FileNameCSV_notification;
            } else if (VoiceCSV_notification != null) {
                notification = VoiceCSV_notification;
            } else if (FileNameCSV_notification != null) {
                notification = FileNameCSV_notification;
            }

            driver.close();
            return ResponseEntity.ok(new String(notification));
        }

        if (flag_TextCSV == true) {
            test(params);
        }


        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2)); // số giây mà 1 driver chờ để load 1 phần tử nếu không có thiết lập của wait
        driver.manage().window().maximize();

        readTextCSV();
        readVoiceCSV();
        readFileNameCSV();
        params.put("Text", TextCSV);
        params.put("Voice", VoiceCSV);
        params.put("FileName", FileNameCSV);


        if (TextCSV.length() >= 2001) {
            driver.close();
            return ResponseEntity.ok(new String("Văn bản quá 2000 kí tự , hãy nhập dưới hoặc bằng 2000 kí tự"));
        }

        if (FileNameCSV.length() >= 101) {
            driver.close();
            return ResponseEntity.ok(new String("Tên File lưu trữ quá dài , hãy nhập dưới hoặc bằng 100 kí tự"));
        }

        if (CheckGender(params.get("Voice"))) {
        } else {
            driver.close();
            return ResponseEntity.ok(new String("Thông tin về giới tính không xác định , hãy nhập lại"));
        }

        System.out.println("-----------------------------\n" + params.get("Text") + " " + params.get("Voice") + " " + params.get("FileName"));
        System.out.println(TextCSV.length());

//        Thread checkFileName = new Thread(new CheckFileName(params.get("FileName"), latch));
//        Thread checkText = new Thread(new CheckText(params.get("Text"), latch));
//        checkFileName.start();
//        checkText.start();
//        latch.await();
//
//        if (flag_checkFileName == false) {
//            flag_checkFileName = true;
//            driver.close();
//            return ResponseEntity.ok(new String("Tên File bị trùng trong dữ liệu của bạn , hãy đổi tên khác hoặc xóa file cũ của bạn"));
//        }
//        if (flag_checkText == false) {
//            flag_checkText = true;
//            driver.close();
//            return ResponseEntity.ok(new String(notification));
//        }

        driver.get("https://ttsfree.com/login");

        ((JavascriptExecutor) driver).executeScript("var images = document.getElementsByTagName('img'); for(var i = 0; i < images.length; i++) { images[i].setAttribute('hidden', 'true'); }");

        driver.findElement(By.xpath("//input[@placeholder='Username']")).sendKeys(user_name);
        driver.findElement(By.xpath("//input[@placeholder='Enter password']")).sendKeys(user_password);

        latch = new CountDownLatch(2); // thiết lập 2 Thread ( trường hợp sau khi send key password sẽ có 1 trong 2 hiển thị nên thiết lập 2 thread kiểm tra cùng 1 lúc )
        Thread threadCheckESC = new Thread(new CheckESC(driver, latch, null));
        Thread threadCheckHandAD = new Thread(new CheckHandAD(driver, latch, null));
        threadCheckESC.start();
        threadCheckHandAD.start();
        latch.await();

        driver.findElement(By.xpath("//ins[@class='iCheck-helper']")).click();
        driver.findElement(By.xpath("//input[@id='btnLogin']")).click();


        try {
            wait = new WebDriverWait(driver, Duration.ofSeconds(2));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("laptopaz.com"))).click();
        } catch (Exception exception) {
            driver.get("https://ttsfree.com/vn");
        }


        ((JavascriptExecutor) driver).executeScript("var images = document.getElementsByTagName('img'); for(var i = 0; i < images.length; i++) { images[i].setAttribute('hidden', 'true'); }");
        ((JavascriptExecutor) driver).executeScript("var images = document.querySelectorAll('img[id=captcha_image]'); for(var i = 0; i < images.length; i++) { if(images[i].src.startsWith('https://ttsfree.com/voice/captcha.php?sign=?')) { images[i].removeAttribute('hidden'); } }");

        js = (JavascriptExecutor) driver;
        webElement = driver.findElement(By.xpath("//*[@id=\"input_text\"]"));
        js.executeScript("arguments[0].scrollIntoView();", webElement);


        latch = new CountDownLatch(1);
        Thread threadCheckAdsTOP = new Thread(new CheckAdsTOP(driver, latch));
        threadCheckAdsTOP.start();
        latch.await();

        latch = new CountDownLatch(1);
        threadCheckESC = new Thread(new CheckESC(driver, latch));
        threadCheckESC.start();
        latch.await();

        latch = new CountDownLatch(1);
        threadCheckHandAD = new Thread(new CheckHandAD(driver, latch));
        threadCheckHandAD.start();
        latch.await();

        /**
         * sendKeys
         * https://ttsfree.com/vn
         */
//        WebElement textArea = driver.findElement(By.xpath("//textarea[@id='input_text']"));
//        textArea.sendKeys(Keys.chord(Keys.CONTROL, "v"));
//        textArea.sendKeys(params.get("Text"));
        driver.findElement(By.xpath("//textarea[@id='input_text']")).sendKeys(params.get("Text"));

//        driver.findElement(By.xpath("//*[@id=\"select2-select_lang_bin-container\"]")).click();
//        WebElement searchInput = driver.findElement(By.xpath("(//input[@aria-label='Search'])[1]"));
//        searchInput.sendKeys("138");
//        searchInput.sendKeys(Keys.RETURN);

        latch = new CountDownLatch(1);
        threadCheckHandAD = new Thread(new CheckHandAD(driver, latch));
        threadCheckHandAD.start();
        latch.await();

        String currentUrl = driver.getCurrentUrl();
        if (currentUrl.equals("https://ttsfree.com/vn#google_vignette")) {
            System.out.println("da quay tro lai");
            driver.navigate().back();
            driver.findElement(By.xpath("//a[normalize-space()='TTS Server 2']")).click();
        }

        if (params.get("Voice").equals("Female")) {
            driver.findElement(By.xpath("(//label[@for='radioPrimaryvi-VN'])[1]")).click();
        } else if (params.get("Voice").equals("Male")) {
            driver.findElement(By.xpath("(//label[@for='radioPrimaryvi-VN2'])[1]")).click();
        }

        driver.findElement(By.xpath("//a[@class='btn mb-2 lg action-1 text-white convert-now']")).click();

        Boolean flag = false;
        try {
            wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='alert alert-danger alert-dismissable']"))).isDisplayed();
            System.out.println("displayed captcha");

            js = (JavascriptExecutor) driver;
            webElement = driver.findElement(By.xpath("(//a[normalize-space()='Confirm'])[1]"));
            js.executeScript("arguments[0].scrollIntoView();", webElement);

            SaveCaptcha_Image saveCaptchaImage = new SaveCaptcha_Image(driver, webElement, "E:\\CongViecHocTap\\Captcha\\", "captcha.png");
            saveCaptchaImage.getCaptcha();
            SendPhoto();

            System.out.println("done image");

            int countdownDuration = 30;
            SendMessage message = new SendMessage();
            message.setChatId("1159534870"); // update.getMessage().getChatId().toString()
            int zero = countdownDuration;
            for (int second = 0; second <= countdownDuration; second++) {
                zero = countdownDuration - second;
                System.out.println(zero);

                Thread.sleep(1000);

                if (text != null) {
                    System.out.println("text : " + text);
                    for (int i = 0; i < text.length(); i++) {
                        if (!Character.isDigit(text.charAt(i))) {
                            message.setText("Value of text has char");
                            execute(message);
                            text = null;
                            break;
                        }
                    }
                    if (text == null || text.isEmpty() || text.length() <= 3) {
                        if (text == null) {
                        } else {
                            message.setText("Text length must be 4 numbers or more");
                            execute(message);
                        }
                        text = null;
                        continue;
                    }

                    latch = new CountDownLatch(1);
                    threadCheckHandAD = new Thread(new CheckHandAD(driver, latch));
                    threadCheckHandAD.start();
                    latch.await();

                    driver.findElement(By.xpath("(//input[@id='captcha_input'])[1]")).sendKeys(text);
                    element_solve = driver.findElements(By.xpath("(//img[@title='Ad.Plus Advertising'])[1]"));
                    if (element_solve.size() > 0 && element_solve.get(0).isDisplayed()) {
                        driver.findElement(By.xpath("(//img[@title='Ad.Plus Advertising'])[1]")).click();
                    }
                    driver.findElement(By.xpath("(//a[normalize-space()='Confirm'])[1]")).click();
                    try {
                        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//h4[normalize-space()='Error!'])[1]")));
                        message.setText("Wrong number of captcha image , type again !");
                        execute(message);
                        text = null;
                        saveCaptchaImage.getCaptcha();
                        SendPhoto();
                        continue;

                    } catch (Exception exception) {
                        flag = true;
                        text = null;
                        message.setText("Valid captcha code");
                        execute(message);

                        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

                        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Tạo Voice')]"))).click();

                        break;
                    }
                }
                System.out.println("---------------------");
            }
            System.out.println("count here");
            System.out.println(zero);
            if (zero == 0) {
                System.out.println("count ");
                message.setText("End of time to solove captcha");
                execute(message);
                driver.close();
                return ResponseEntity.ok(new String("End of time to solove captcha"));
            }


        } catch (Exception e) {
            System.out.println("Non display captcha");
        }
//        try{
//            wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("1]/div/a"))).click();
//        }catch (Exception exception){
//            System.out.println(exception);
//            js = (JavascriptExecutor) driver;
//            webElement = driver.findElement(By.xpath("//*[@id=\"input_text\"]"));
//            js.executeScript("arguments[0].scrollIntoView();", webElement);
//        }/html/body/header/nav/div/div/div[2]

        if (flag == true) {
            flag = false;
            System.out.println("flag == true");
            try {
                wait = new WebDriverWait(driver, Duration.ofSeconds(5));
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("laptopaz.com"))).click();
            } catch (Exception exception) {

            }
        } else {
        }

        latch = new CountDownLatch(1);
        threadCheckHandAD = new Thread(new CheckHandAD(driver, latch));
        threadCheckHandAD.start();
        latch.await();

        js = (JavascriptExecutor) driver;
        webElement = driver.findElement(By.xpath("//*[@id=\"input_text\"]"));
        js.executeScript("arguments[0].scrollIntoView();", webElement);

        wait = new WebDriverWait(driver, Duration.ofSeconds(120));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"progessResults\"]/div[2]/center[1]/div/a"))).click();
        latch = new CountDownLatch(1);
        Thread threadCheckHostAD = new Thread(new CheckHostAD(driver, latch));
        threadCheckHostAD.start();
        latch.await();

//        try {
//            wait = new WebDriverWait(driver, Duration.ofSeconds(2));
//            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"dismiss-button\"]/div/span")));
//        } catch (Exception exception) {
//            driver.close();
//        }
        /**
         * đổi tên file theo yêu cầu user ( đơn luồng thì hoạt động oke , đa luồng thì lỗi -> đang nghiên cứu login 1 lúc có request cùng đổi để đảm bảo không có lỗi xảy ra
         * đang nghiên cứu để update
         */
//        File folder = new File("E:\\New folder");
//        File[] files = folder.listFiles();
//        if (files != null && files.length > 0) {
//            Arrays.sort(files, Comparator.comparingLong(File::lastModified).reversed());
//            File latestFile = files[0];
//            System.out.println(latestFile.getName());
//            String newFileName = params.get("FileName") + ".mp3";
//            File newFile = new File(folder, newFileName);
//            latestFile.renameTo(newFile);
//        }
        try {
            wait = new WebDriverWait(driver, Duration.ofSeconds(1));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("laptopaz.com"))).click();
        } catch (Exception exception) {
            driver.close();
        }

        return ResponseEntity.ok(new String("Downloaded successfully"));
    }

    private void readTextCSV() {
        // Đường dẫn đến file CSV
        String csvFilePath = "E:\\CongViecHocTap\\Jmeter\\dulieu.csv";

        // Tên cột bạn muốn đọc
        String columnName = "Text";

        try (CSVReader csvReader = new CSVReaderBuilder(new FileReader(csvFilePath)).build()) {
            // Lấy header để xác định index của cột cần đọc
            String[] headers = csvReader.readNext();
            int columnIndex = -1;
            for (int i = 0; i < headers.length; i++) {
                if (headers[i].equalsIgnoreCase(columnName)) {
                    columnIndex = i;
                    break;
                }
            }

            if (columnIndex == -1) {
                System.out.println("Không tìm thấy cột có tên: " + columnName);
                return;
            }

            // Đọc dữ liệu từ cột và hiển thị nó
            List<String[]> allData = csvReader.readAll();
            for (String[] row : allData) {
                TextCSV = row[columnIndex];

                //check length
                if (TextCSV.length() >= 4001) {
                    flag_TextCSV = false;
                }

                // Thêm dữ liệu vào biến text của bạn
                // Ví dụ:
                // text += cellData;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        } catch (CsvException e) {
            throw new RuntimeException(e);
        }
    }

    private void readVoiceCSV() {
        // Đường dẫn đến file CSV
        String csvFilePath = "E:\\CongViecHocTap\\Jmeter\\dulieu.csv";

        // Tên cột bạn muốn đọc
        String columnName = "Voice";

        try (CSVReader csvReader = new CSVReaderBuilder(new FileReader(csvFilePath)).build()) {
            // Lấy header để xác định index của cột cần đọc
            String[] headers = csvReader.readNext();
            int columnIndex = -1;
            for (int i = 0; i < headers.length; i++) {
                if (headers[i].equalsIgnoreCase(columnName)) {
                    columnIndex = i;
                    break;
                }
            }

            if (columnIndex == -1) {
                System.out.println("Không tìm thấy cột có tên: " + columnName);
                return;
            }

            // Đọc dữ liệu từ cột và hiển thị nó
            List<String[]> allData = csvReader.readAll();
            for (String[] row : allData) {
                VoiceCSV = row[columnIndex];
                System.out.println(VoiceCSV);

                // Thêm dữ liệu vào biến text của bạn
                // Ví dụ:
                // text += cellData;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        } catch (CsvException e) {
            throw new RuntimeException(e);
        }
    }

    private void readFileNameCSV() {
        // Đường dẫn đến file CSV
        String csvFilePath = "E:\\CongViecHocTap\\Jmeter\\dulieu.csv";

        // Tên cột bạn muốn đọc
        String columnName = "FileName";

        try (CSVReader csvReader = new CSVReaderBuilder(new FileReader(csvFilePath)).build()) {
            // Lấy header để xác định index của cột cần đọc
            String[] headers = csvReader.readNext();
            int columnIndex = -1;
            for (int i = 0; i < headers.length; i++) {
                if (headers[i].equalsIgnoreCase(columnName)) {
                    columnIndex = i;
                    break;
                }
            }

            if (columnIndex == -1) {
                System.out.println("Không tìm thấy cột có tên: " + columnName);
                return;
            }

            // Đọc dữ liệu từ cột và hiển thị nó
            List<String[]> allData = csvReader.readAll();
            for (String[] row : allData) {
                FileNameCSV = row[columnIndex];
                System.out.println(FileNameCSV);

                // Thêm dữ liệu vào biến text của bạn
                // Ví dụ:
                // text += cellData;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        } catch (CsvException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean CheckGender(String gender) {
        // Chuyển đổi giá trị của gioiTinh thành chữ thường để so sánh không phân biệt chữ hoa chữ thường
        gender = gender.toLowerCase();

        // Kiểm tra xem gioiTinh có thuộc vào các giá trị cho phép không
        return gender.equals("male") || gender.equals("female");
    }


    @GetMapping("/test")
    public ResponseEntity<?> test(@RequestParam Map<String, String> params) throws InterruptedException, IOException, TelegramApiException {
        System.setProperty("webdriver.http.factory", "jdk-http-client");
        System.setProperty("webdriver.chrome.driver", "E:\\CongViecHocTap\\ChromeDriver\\chromedriver-win64\\chromedriver.exe");

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setExperimentalOption("useAutomationExtension", false); // disable chrome running as automation
        chromeOptions.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation")); // disable chrome running as automation

        // Số lượng luồng bạn muốn sử dụng
        int numberOfThreads = 2;

        // Tạo một ThreadPoolExecutor với số lượng luồng tùy chỉnh
        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);

        for (int i = 0; i < numberOfThreads; i++) {
            // Mỗi luồng sẽ thực hiện một instance của WebDriver
            executor.execute(() -> {
                try {
                    LastAPI(params);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        // Đợi tất cả các luồng hoàn thành
        executor.shutdown();

        return ResponseEntity.ok(new String("Downloaded successfully"));
    }

}

/**
 * cd C:\Program Files\Google\Chrome\Application
 * chrome.exe --remote-debugging-port=9222 --user-data-dir="E:\CongViecHocTap\ChromeData"
 * chrome.exe --remote-debugging-port=9222 --user-data-dir="D:\New folder\ChromeDriver\chromedriver-win64"
 * <p>
 * chrome://settings/content/popups
 */


class Main {
    public static void main(String[] args) {
        String chuoi = "nam ăn c, ^am ở điện biên vì nam ở việt nam";
        String ketQua = layKyTu(chuoi, 1, 10);
        System.out.println("Các ký tự từ 1 đến 10 trong chuỗi là: " + ketQua);
    }

    public static String layKyTu(String chuoi, int batDau, int ketThuc) {
        StringBuilder ketQua = new StringBuilder();
        for (int i = batDau - 1; i < ketThuc && i < chuoi.length(); i++) {
            ketQua.append(chuoi.charAt(i));
        }
        return ketQua.toString();
    }
}