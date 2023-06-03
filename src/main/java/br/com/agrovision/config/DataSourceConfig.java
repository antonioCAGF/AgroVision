package br.com.agrovision.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean
    public DataSource dataSource() {

        // Configuração do bancoa
        return DataSourceBuilder
                .create()
                .url("jdbc:mysql://localhost:3306/base_agrovision")
                .username("root")
                .password("admin")
                .build();
    }
}
