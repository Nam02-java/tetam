package com.example.Selenium.Package02;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ReadTextCSV implements Runnable {
    private String TextCSV;
    private CountDownLatch countDownLatch;

    public static Boolean flag_TextCSV = true;

    public ReadTextCSV(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        // Đường dẫn đến file CSV
        String csvFilePath = "E:\\CongViecHocTap\\Jmeter\\dulieu.csv";

        // Tên cột bạn muốn đọc
        final String columnName = "Text";

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
            countDownLatch.countDown();
        } catch (IOException e) {
            countDownLatch.countDown();
            e.printStackTrace();
        } catch (CsvValidationException e) {
            countDownLatch.countDown();
            throw new RuntimeException(e);
        } catch (CsvException e) {
            countDownLatch.countDown();
            throw new RuntimeException(e);
        }
    }
}

//text = text.trim();
//
//        if (text.matches("[!@#$%^&*()_+=,]+")) {
//            notification = "Chỉ chứa kí tự đặc biệt, trình phiên dịch không thể đọc, yêu cầu nhập lại Text";
//            System.out.println(notification);
//            flag_checkText = false;
//        } else if (text.matches(".*[a-zA-Z].*") && text.matches(".*\\d.*")) {
//            // Các điều kiện khác
//        } else if (text.isEmpty()) {
//            notification = "Không được để trống, yêu cầu nhập lại";
//            System.out.println(notification);
//            flag_checkText = false;
//        } else {
//            // Các điều kiện khác
//        }
//
//        countDownLatch.countDown();
