package com.swiatek.benford.infrastructure;

import com.swiatek.benford.document.DocumentFacade;
import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@AllArgsConstructor
@Slf4j
public class InfrastructureConfiguration {

    Environment environment;

    @Bean
    PostgresqlConnectionFactory connectionFactory() {
        return new PostgresqlConnectionFactory(PostgresqlConnectionConfiguration.builder()
                .username(environment.getProperty("spring.r2dbc.username", "NULL_USERNAME"))
                .password(environment.getProperty("spring.r2dbc.password", "NULL_PASSWORD"))
                .host(environment.getProperty("db.host", "NULL_HOST"))
                .database(environment.getProperty("db.name", "NULL_NAME"))
                .port(Integer.parseInt(environment.getProperty("db.port", "5432")))
                .build());
    }

    @Bean
    DocumentController documentController(DocumentFacade documentFacade, PostgresqlConnectionFactory connectionFactory) {
        return new DocumentController(documentFacade, connectionFactory);
    }
}
