package com.example.Selenium.Package02;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class LogUtils {
    private static final String LOG_FILE_PATH = "E:\\CongViecHocTap\\log.txt";

    public static void logResponse(String response) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE_PATH, true))) {
            // Lấy thời gian hiện tại
            LocalDateTime timestamp = LocalDateTime.now();
            // Ghi thông tin vào tệp log
            writer.write("Timestamp: " + timestamp + "\n");
            writer.write("Response: " + response + "\n");
            writer.write("----------------------------\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
