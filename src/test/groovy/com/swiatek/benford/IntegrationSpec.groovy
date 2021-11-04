package com.swiatek.benford

import groovy.util.logging.Slf4j
import org.springframework.boot.test.context.SpringBootTest
import org.testcontainers.containers.PostgreSQLContainer
import reactor.blockhound.BlockHound
import spock.lang.Specification
import spock.util.concurrent.PollingConditions

import java.nio.file.Path

@Slf4j
@SpringBootTest(classes = [BenfordApplication], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
abstract class IntegrationSpec extends Specification {

    def conditions = new PollingConditions(timeout: 15, initialDelay: 1.5, factor: 1.25)

    static {
        runPostgresql()
        BlockHound.install()
    }

    final static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer<>("postgres:12.3")
    .withInitScript("schema.sql")
            .withDatabaseName("benford")
            .withUsername("sa")
            .withPassword("sa")

    private static void runPostgresql() {
        postgreSQLContainer.start()
        System.setProperty("spring.r2dbc.url", "r2dbc:tc:postgresql:///test?TC_IMAGE_TAG=12.3")
        System.setProperty("spring.datasource.username", postgreSQLContainer.getUsername())
        System.setProperty("spring.datasource.password", postgreSQLContainer.getPassword())
        System.setProperty("spring.r2dbc.username", postgreSQLContainer.getUsername())
        System.setProperty("spring.r2dbc.password", postgreSQLContainer.getPassword())
        System.setProperty("db.host", postgreSQLContainer.getHost())
        System.setProperty("db.name", postgreSQLContainer.getDatabaseName())
        System.setProperty("db.port", postgreSQLContainer.getFirstMappedPort().toString())
    }
}
