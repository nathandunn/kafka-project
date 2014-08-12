package kafka.project

import com.google.common.io.Files
import com.google.common.io.InputSupplier
import grails.transaction.Transactional
import org.biojava3.sequencing.io.fastq.FastqReader
import org.biojava3.sequencing.io.fastq.IlluminaFastqReader
import org.biojava3.sequencing.io.fastq.StreamListener
import org.springframework.web.multipart.commons.CommonsMultipartFile

import static org.springframework.http.HttpStatus.*

/**
 * FastqController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
@Transactional(readOnly = true)
class FastqController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Fastq.list(params), model: [fastqInstanceCount: Fastq.count()]
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Fastq.list(params), model: [fastqInstanceCount: Fastq.count()]
    }

    def show(Fastq fastqInstance) {
        respond fastqInstance
    }

    def create() {
        respond new Fastq(params)
    }

    @Transactional
    def upload() {
        CommonsMultipartFile uploadedFile = request.getFile('file')
//        String fastaContent = uploadedFile.inputStream.text
//        InputSupplier inputSupplier = Files.newReaderSupplier(new File("illumina.fastq"));
        println "path ${request.getPathTranslated()}"
        InputSupplier inputSupplier = Files.newReaderSupplier(new File(request.getPathTranslated()));
//        FileInputStream inStream = new FileInputStream( uploadedFile.inputStream );
        FastqReader fastqReader = new IlluminaFastqReader();

        int count = 0
        fastqReader.stream(inputSupplier, new StreamListener() {
            @Override
            void fastq(org.biojava3.sequencing.io.fastq.Fastq fastq) {
                if (fastq.getSequence().length() > 16) {
                    ++count
                    Fastq fastq1 = new Fastq(
                            header: fastq.description
                            , sequence: fastq.sequence
                            , quality: fastq.quality
                    ).save()
                    if (count % 100 == 0) {
                        fastq1.save(flush: true)
                    }
                }
            }

            //            @Override
//            public void fastq(final Fastq fastq)
//            {
//                if (fastq.getSequence().length() > 16)
//                {
//                    fastqWriter.append(fileWriter, fastq);
//                }
//            }
        });
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
    def save(Fastq fastqInstance) {
        if (fastqInstance == null) {
            notFound()
            return
        }

        if (fastqInstance.hasErrors()) {
            respond fastqInstance.errors, view: 'create'
            return
        }

        fastqInstance.save flush: true

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
            respond fastqInstance.errors, view: 'edit'
            return
        }

        fastqInstance.save flush: true

        request.withFormat {
            form {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Fastq.label', default: 'Fastq'), fastqInstance.id])
                redirect fastqInstance
            }
            '*' { respond fastqInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Fastq fastqInstance) {

        if (fastqInstance == null) {
            notFound()
            return
        }

        fastqInstance.delete flush: true

        request.withFormat {
            form {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Fastq.label', default: 'Fastq'), fastqInstance.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'fastqInstance.label', default: 'Fastq'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}
