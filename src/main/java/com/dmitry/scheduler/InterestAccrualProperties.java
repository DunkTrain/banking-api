package com.dmitry.scheduler;

import jakarta.validation.constraints.DecimalMin;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

/**
 * Конфигурационные параметры начисления процентов пользователям.
 * <p>
 * Значения загружаются из файла конфигурации {@code application.yml} с префиксом {@code interest}.
 * Используются для расчёта периодического увеличения баланса аккаунтов.
 */
@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "interest")
public class InterestAccrualProperties {

    /**
     * Процент начисления на баланс.
     * <p>
     * Значение {@code 0.10} означает увеличение на 10% за период.
     */
    @DecimalMin("0.0")
    private BigDecimal percent;

    /**
     * Максимально допустимый множитель баланса по отношению к начальному депозиту.
     * <p>
     * Значение {@code 2.07} означает, что баланс не может превысить 207% от первоначальной суммы.
     */
    @DecimalMin("1.0")
    private BigDecimal maxMultiplier;
}
