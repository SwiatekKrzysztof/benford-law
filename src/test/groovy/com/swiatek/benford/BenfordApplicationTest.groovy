package com.swiatek.benford

import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.spock.Testcontainers
import spock.lang.Shared
import spock.lang.Specification

@Testcontainers
class BenfordApplicationTest extends Specification {

    @Shared
    PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:9.6.23")
            .withDatabaseName("foo")
            .withUsername("foo")
            .withPassword("secret")

    void setup() {
    }

    def "Main"() {
        given:
        println "dupa"
        expect:
        1 == 1
    }
}
