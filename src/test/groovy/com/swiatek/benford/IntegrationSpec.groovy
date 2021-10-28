package com.swiatek.benford

import groovy.util.logging.Slf4j
import org.springframework.boot.test.context.SpringBootTest
import org.testcontainers.containers.PostgreSQLContainer
import spock.lang.Specification
import spock.util.concurrent.PollingConditions

@Slf4j
@SpringBootTest(classes = [BenfordApplication], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
abstract class IntegrationSpec extends Specification {

    def conditions = new PollingConditions(timeout: 10, initialDelay: 1.5, factor: 1.25)

    static {
        runPostgresql()
    }

    final static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:12.3")
            .withDatabaseName("benford")
            .withUsername("sa")
            .withPassword("sa")

    private static void runPostgresql() {
//        HashMap<MountableFile,String> filesWithTargetLocation = new HashMap<>()
//        filesWithTargetLocation.put(MountableFile.forHostPath("./../../../schema.sql"),"/docker-entrypoint-initdb.d")
//        postgreSQLContainer.setCopyToFileContainerPathMap(filesWithTargetLocation)
        postgreSQLContainer.start()
        System.setProperty("spring.datasource.url", postgreSQLContainer.getJdbcUrl())
        System.setProperty("spring.datasource.username", postgreSQLContainer.getUsername())
        System.setProperty("spring.datasource.password", postgreSQLContainer.getPassword())
    }
}
