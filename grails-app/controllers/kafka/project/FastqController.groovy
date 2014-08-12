package kafka.project


import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

/**
 * FastqController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
@Transactional(readOnly = true)
class FastqController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

	def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Fastq.list(params), model:[fastqInstanceCount: Fastq.count()]
    }

	def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Fastq.list(params), model:[fastqInstanceCount: Fastq.count()]
    }

    def show(Fastq fastqInstance) {
        respond fastqInstance
    }

    def create() {
        respond new Fastq(params)
    }

    @Transactional
    def save(Fastq fastqInstance) {
        if (fastqInstance == null) {
            notFound()
            return
        }

        if (fastqInstance.hasErrors()) {
            respond fastqInstance.errors, view:'create'
            return
        }

        fastqInstance.save flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.created.message', args: [message(code: 'fastqInstance.label', default: 'Fastq'), fastqInstance.id])
                redirect fastqInstance
            }
            '*' { respond fastqInstance, [status: CREATED] }
        }
    }

    def edit(Fastq fastqInstance) {
        respond fastqInstance
    }

    @Transactional
    def update(Fastq fastqInstance) {
        if (fastqInstance == null) {
            notFound()
            return
        }

        if (fastqInstance.hasErrors()) {
            respond fastqInstance.errors, view:'edit'
            return
        }

        fastqInstance.save flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Fastq.label', default: 'Fastq'), fastqInstance.id])
                redirect fastqInstance
            }
            '*'{ respond fastqInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Fastq fastqInstance) {

        if (fastqInstance == null) {
            notFound()
            return
        }

        fastqInstance.delete flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Fastq.label', default: 'Fastq'), fastqInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'fastqInstance.label', default: 'Fastq'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
