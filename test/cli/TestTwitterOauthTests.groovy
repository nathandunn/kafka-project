import grails.test.AbstractCliTestCase

class TestTwitterOauthTests extends AbstractCliTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testTestTwitterOauth() {

        execute(["test-twitter-oauth"])

        assertEquals 0, waitForProcess()
        verifyHeader()
    }
}
