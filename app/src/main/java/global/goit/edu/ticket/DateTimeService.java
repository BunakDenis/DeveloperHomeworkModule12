package global.goit.edu.ticket;

import java.time.DateTimeException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateTimeService {
    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss z";
    private static final String DEFAULT_TIME_ZONE = "UTC";

    private static Date date;

    public static String get() {
        date = Date.from(Instant.now());
        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN)
                .withZone(ZoneId.of(DEFAULT_TIME_ZONE));

        String result = dateTimeFormat.format(date.toInstant());

        return result;
    }

    public static String get(String utc) {
        date = Date.from(Instant.now());

        try {
            DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN)
                    .withZone(ZoneId.of(utc));
            System.out.println(ZoneId.of(utc));
            return dateTimeFormat.format(date.toInstant());
        } catch (DateTimeException e) {

        }
        return null;
    }
}
