package com.swiatek.benford

import com.swiatek.benford.infrastructure.UploadController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class BenfordApplicationContextTest extends IntegrationSpec {
    @Autowired
    UploadController uploadController

    def "Should load all beans properly"() {
        expect:
        uploadController
    }
}
