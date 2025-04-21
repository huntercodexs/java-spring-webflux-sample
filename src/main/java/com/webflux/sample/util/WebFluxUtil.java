package com.webflux.sample.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WebFluxUtil {

    public static String datetimeUtil(LocalDateTime inputDateTime) {
        return inputDateTime != null ? inputDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null;
    }

}
