package com.example.manager_garage.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {

    public static final ZoneId VIETNAM_ZONE = ZoneId.of("Asia/Ho_Chi_Minh");
    public static final DateTimeFormatter VIETNAM_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Lấy thời gian hiện tại theo múi giờ Việt Nam
     */
    public static LocalDateTime getCurrentVietnamTime() {
        return LocalDateTime.now(VIETNAM_ZONE);
    }

    /**
     * Chuyển đổi LocalDateTime sang múi giờ Việt Nam
     */
    public static LocalDateTime convertToVietnamTime(LocalDateTime dateTime) {
        return dateTime.atZone(ZoneId.systemDefault())
                .withZoneSameInstant(VIETNAM_ZONE)
                .toLocalDateTime();
    }

    /**
     * Format thời gian theo định dạng Việt Nam
     */
    public static String formatVietnamTime(LocalDateTime dateTime) {
        return dateTime.format(VIETNAM_FORMATTER);
    }

    /**
     * Kiểm tra xem thời gian có trong quá khứ không (theo múi giờ Việt Nam)
     */
    public static boolean isInPast(LocalDateTime dateTime) {
        return dateTime.isBefore(getCurrentVietnamTime());
    }

    /**
     * Kiểm tra xem thời gian có hợp lệ không (không trong quá khứ)
     */
    public static boolean isValidFutureTime(LocalDateTime dateTime) {
        return !isInPast(dateTime);
    }
}