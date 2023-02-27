package com.definex.practicum.finalcase.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Configuration
@PropertySource("classpath:persistence-mysql.properties")
@EnableAutoConfiguration
public class DatabaseConfig {}
