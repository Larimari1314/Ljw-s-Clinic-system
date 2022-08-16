package sale.ljw.clinicsystem.common.sercurity.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Component
public class TimeUtils {
    public static String interceptTime(String timeStr) {
        if (!StringUtils.isBlank(timeStr)) {
            if (timeStr.contains("T")) {
                DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
                LocalDateTime ldt = LocalDateTime.parse(timeStr, df);
                ZoneId currentZone = ZoneId.of("UTC");
                ZoneId newZone = ZoneId.of("Asia/Shanghai");
                timeStr = ldt.atZone(currentZone).withZoneSameInstant(newZone).toLocalDateTime().toString();
            }
            if (timeStr.length() >= 10) {
                return timeStr.substring(0, 10);
            }
        }
        return timeStr;
    }

}
