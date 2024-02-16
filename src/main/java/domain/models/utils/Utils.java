package domain.models.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utils {

    public static String formatLocalDateTimeToString(LocalDateTime dateTime) {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss").format(dateTime);
    }
}
