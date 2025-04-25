package com.dmitry.config;

import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.jdbctemplate.JdbcTemplateLockProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * Конфигурация планировщика задач и механизма блокировок ShedLock.
 * <p>
 * Включает поддержку периодических задач через {@code @EnableScheduling} и
 * управление транзакциями через {@code @EnableTransactionManagement}.
 * Настраивает {@link LockProvider} для предотвращения одновременного выполнения задач
 * в нескольких инстансах приложения.
 */
@Configuration
@EnableScheduling
@EnableTransactionManagement
public class SchedulerConfig {

    /**
     * Создаёт бин {@link LockProvider} на основе {@link JdbcTemplateLockProvider}.
     * <p>
     * Используется для обеспечения распределённой блокировки задач через базу данных.
     *
     * @param dataSource источник данных для подключения к базе данных
     * @return настроенный {@link LockProvider}
     */
    @Bean
    public LockProvider lockProvider(DataSource dataSource) {
        return new JdbcTemplateLockProvider(
                JdbcTemplateLockProvider.Configuration.builder()
                        .withJdbcTemplate(new JdbcTemplate(dataSource))
                        .usingDbTime()
                        .build()
        );
    }
}
