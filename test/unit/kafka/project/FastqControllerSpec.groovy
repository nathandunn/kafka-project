package kafka.project



import grails.test.mixin.*
import spock.lang.*

@TestFor(FastqController)
@Mock(Fastq)
class FastqControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            !model.fastqInstanceList
            model.fastqInstanceCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.fastqInstance!= null
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            def fastq = new Fastq()
            fastq.validate()
            controller.save(fastq)

        then:"The create view is rendered again with the correct model"
            model.fastqInstance!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            fastq = new Fastq(params)

            controller.save(fastq)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/fastq/show/1'
            controller.flash.message != null
            Fastq.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def fastq = new Fastq(params)
            controller.show(fastq)

        then:"A model is populated containing the domain instance"
            model.fastqInstance == fastq
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def fastq = new Fastq(params)
            controller.edit(fastq)

        then:"A model is populated containing the domain instance"
            model.fastqInstance == fastq
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            controller.update(null)

        then:"A 404 error is returned"
            response.redirectedUrl == '/fastq/index'
            flash.message != null


        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def fastq = new Fastq()
            fastq.validate()
            controller.update(fastq)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.fastqInstance == fastq

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            fastq = new Fastq(params).save(flush: true)
            controller.update(fastq)

        then:"A redirect is issues to the show action"
            response.redirectedUrl == "/fastq/show/$fastq.id"
            flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/fastq/index'
            flash.message != null

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def fastq = new Fastq(params).save(flush: true)

        then:"It exists"
            Fastq.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(fastq)

        then:"The instance is deleted"
            Fastq.count() == 0
            response.redirectedUrl == '/fastq/index'
            flash.message != null
    }
}
