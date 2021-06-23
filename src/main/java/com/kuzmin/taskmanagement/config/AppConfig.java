package com.kuzmin.taskmanagement.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:additional.properties")
@EnableCaching
public class AppConfig {
}
