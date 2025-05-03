package com.webflux.sample.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static com.webflux.sample.util.WebFluxUtil.datetimeUtil;
import static org.assertj.core.api.Assertions.assertThat;

class WebFluxUtilTest {

    @Test
    @DisplayName("UTIL - datetimeUtilTestIsNull")
    void datetimeUtilTestIsNull() {
        assertThat(datetimeUtil(null)).isNull();
    }

    @Test
    @DisplayName("UTIL - datetimeUtilTestAsDateTime")
    void datetimeUtilTestAsDateTime() {
        LocalDateTime localDateTime = LocalDateTime.of(2025, 1, 30, 10, 0, 0);
        Object result = datetimeUtil(localDateTime);
        assertThat(result).isEqualTo("2025-01-30 10:00:00");
    }
}
