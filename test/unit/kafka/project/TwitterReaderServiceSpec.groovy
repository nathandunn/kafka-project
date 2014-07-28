package kafka.project

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(TwitterReaderService)
class TwitterReaderServiceSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test something"() {
        when:
        List<String> messages = service.findSimpleStream()
        then:
        println messages.size()
        assert messages.size()>0


    }
}
