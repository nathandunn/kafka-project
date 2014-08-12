package kafka.project

import grails.transaction.Transactional
import org.springframework.web.multipart.commons.CommonsMultipartFile

import static org.springframework.http.HttpStatus.*

/**
 * FastaSequenceController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
@Transactional(readOnly = true)
class FastaSequenceController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

	def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond FastaSequence.list(params), model:[fastaSequenceInstanceCount: FastaSequence.count()]
    }

	def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond FastaSequence.list(params), model:[fastaSequenceInstanceCount: FastaSequence.count()]
    }

    def show(FastaSequence fastaSequenceInstance) {
        respond fastaSequenceInstance
    }

    def create() {
        respond new FastaSequence(params)
    }

    @Transactional
    def upload(){
        CommonsMultipartFile uploadedFile = request.getFile('file')
        String fastaContent = uploadedFile.inputStream.text
        FileInputStream inStream = new FileInputStream( args[0] );
//        FastaReader<ProteinSequence,AminoAcidCompound> fastaReader =
//                new FastaReader<ProteinSequence,AminoAcidCompound>(
//                        inStream,
//                        new GenericFastaHeaderParser<ProteinSequence,AminoAcidCompound>(),
//                        new ProteinSequenceCreator(AminoAcidCompoundSet.getAminoAcidCompoundSet()));
//        LinkedHashMap<String, ProteinSequence> b = fastaReader.process();
//        for (  Entry<String, ProteinSequence> entry : b.entrySet() ) {
//            System.out.println( entry.getValue().getOriginalHeader() + "=" + entry.getValue().getSequenceAsString() );
//        }

    }

    @Transactional
    def save(FastaSequence fastaSequenceInstance) {
        if (fastaSequenceInstance == null) {
            notFound()
            return
        }

        if (fastaSequenceInstance.hasErrors()) {
            respond fastaSequenceInstance.errors, view:'create'
            return
        }

        fastaSequenceInstance.save flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.created.message', args: [message(code: 'fastaSequenceInstance.label', default: 'FastaSequence'), fastaSequenceInstance.id])
                redirect fastaSequenceInstance
            }
            '*' { respond fastaSequenceInstance, [status: CREATED] }
        }
    }

    def edit(FastaSequence fastaSequenceInstance) {
        respond fastaSequenceInstance
    }

    @Transactional
    def update(FastaSequence fastaSequenceInstance) {
        if (fastaSequenceInstance == null) {
            notFound()
            return
        }

        if (fastaSequenceInstance.hasErrors()) {
            respond fastaSequenceInstance.errors, view:'edit'
            return
        }

        fastaSequenceInstance.save flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'FastaSequence.label', default: 'FastaSequence'), fastaSequenceInstance.id])
                redirect fastaSequenceInstance
            }
            '*'{ respond fastaSequenceInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(FastaSequence fastaSequenceInstance) {

        if (fastaSequenceInstance == null) {
            notFound()
            return
        }

        fastaSequenceInstance.delete flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'FastaSequence.label', default: 'FastaSequence'), fastaSequenceInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'fastaSequenceInstance.label', default: 'FastaSequence'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
