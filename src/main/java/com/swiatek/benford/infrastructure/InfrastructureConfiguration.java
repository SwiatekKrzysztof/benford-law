package com.swiatek.benford.infrastructure;

import com.swiatek.benford.document.DocumentFacade;
import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.spi.Connection;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import lombok.AllArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import static io.r2dbc.spi.ConnectionFactoryOptions.*;

@Configuration
@AllArgsConstructor
public class InfrastructureConfiguration {

    Environment environment;
//    ConnectionFactory connectionFactory;
//    Publisher<? extends Connection> connectionPublisher;
//
//    public InfrastructureConfiguration(Environment environment) {
//        this.environment = environment;
////        this.connectionFactory = ConnectionFactories.get(environment.getProperty("SPRING.R2DBC.URL"));
//        this.connectionFactory = ConnectionFactories.get(ConnectionFactoryOptions.builder()
//                .option(DRIVER, "postgresql")
//                .option(HOST, environment.getProperty("db.host"))
////                .option(PORT, 5432)  // optional, defaults to 5432
//                .option(USER,environment.getProperty("spring.r2dbc.username"))
//                .option(PASSWORD, environment.getProperty("spring.r2dbc.password"))
////                .option(DATABASE, "...")  // optional
////                .option(OPTIONS, options) // optional
//                .build());
//        this.connectionPublisher = connectionFactory.create();
//    }

    @Bean
    PostgresqlConnectionFactory connectionFactory() {
        return new PostgresqlConnectionFactory(
                PostgresqlConnectionConfiguration.builder()
                        .username(environment.getProperty("spring.r2dbc.username"))
                        .password(environment.getProperty("spring.r2dbc.password"))
                        .host(environment.getProperty("db.host"))
                .build());
//        return ConnectionFactories.get(ConnectionFactoryOptions.builder()
//                .option(DRIVER, "postgresql")
//                .option(HOST, environment.getProperty("db.host"))
////                .option(PORT, 5432)  // optional, defaults to 5432
//                .option(USER,environment.getProperty("spring.r2dbc.username"))
//                .option(PASSWORD, environment.getProperty("spring.r2dbc.password"))
////                .option(DATABASE, "...")  // optional
////                .option(OPTIONS, options) // optional
//                .build());
    }

    @Bean
    DocumentController documentController(DocumentFacade documentFacade, PostgresqlConnectionFactory connectionFactory) {
        return new DocumentController(documentFacade, connectionFactory);
    }




}
