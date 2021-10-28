package com.swiatek.benford


import org.testcontainers.spock.Testcontainers

@Testcontainers
class TestContainerTest extends IntegrationSpec {

//    @Shared
//    PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:9.6.23")
//            .withDatabaseName("benford")
//            .withUsername("sa")
//            .withPassword("sa")

    void setup() {
    }

    def "Main"() {
        given:
        println "dupa"
        expect:
        1 == 1
    }
}
