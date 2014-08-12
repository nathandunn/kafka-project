package kafka.project

import com.google.common.io.Files
import com.google.common.io.InputSupplier
import grails.transaction.Transactional
import org.biojava3.sequencing.io.fastq.FastqReader
import org.biojava3.sequencing.io.fastq.SangerFastqReader
import org.biojava3.sequencing.io.fastq.StreamListener
import org.elasticsearch.action.bulk.BulkRequestBuilder
import org.elasticsearch.action.bulk.BulkResponse
import org.elasticsearch.client.Client
import org.elasticsearch.client.transport.TransportClient
import org.elasticsearch.common.transport.InetSocketTransportAddress

import java.nio.charset.Charset

import static org.elasticsearch.node.NodeBuilder.nodeBuilder
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

    def uploadPage(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        render view: "uploadPage", model: [fastqInstance: Fastq.list(params), fastqInstanceCount: Fastq.count()]
    }

    def uploadPage2(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        render view: "uploadPage2", model: [fastqInstance: Fastq.list(params), fastqInstanceCount: Fastq.count()]
    }


    def upload2() {
        File file = new File(".")
        println "local file ${file.absolutePath}"
//        InputSupplier inputSupplier = Files.newReaderSupplier(new File("/Users/NathanDunn/hg/kafka-project/elasticsearch/SRA/SAMPLE.fastq"), Charset.defaultCharset());
//
//        FastqReader fastqReader = new SangerFastqReader()


//        org.elasticsearch.node.Node node = nodeBuilder().node();
//        Client client = node.client();
//        Client client = new TransportClient()
//                .addTransportAddress(new InetSocketTransportAddress("host1", 9300))
//
//
//        BulkRequestBuilder bulkRequest = client.prepareBulk();
//        bulkRequest.add(client.prepareIndex("twitter", "tweet", "1")
//                .setSource(jsonBuilder()
//                .startObject()
//                .field("user", "kimchy")
//                .field("postDate", new Date())
//                .field("message", "trying out Elasticsearch")
//                .endObject()
//        )
//        );

//        int count = 0
//        int priorCount = 0
//        int epochCount
//        long startTime = System.currentTimeMillis()
//        long check1Time = System.currentTimeMillis()
//        long check2Time = System.currentTimeMillis()
//        long epochTime
//        fastqReader.stream(inputSupplier, new StreamListener() {
//            @Override
//            void fastq(org.biojava3.sequencing.io.fastq.Fastq fastq) {
//                if (fastq.getSequence().length() > 16) {
//                    ++count
//                    Fastq.withTransaction {
//                        Fastq fastq1 = new Fastq(
//                                header: fastq.description
//                                , sequence: fastq.sequence
//                                , quality: fastq.quality
//                        ).save()
//                        if (count % 200 == 0) {
//                            fastq1.save(flush: true)
//                            if (count % 5000 == 0) {
//                                check2Time = System.currentTimeMillis()
//                                epochTime = (check2Time - check1Time) / 1000.0
//                                epochCount = count - priorCount
//                                println "${epochCount} processed in ${epochTime} s - ${count} total, rate ${epochCount / (epochTime + 1)} "
//                                priorCount = count
//                                check1Time = check2Time
//                            }
//                        }
//                    }
//                }
//            }
//
//        });
        long stopTime = System.currentTimeMillis()

//        flash.message = "${count} stored in ${(stopTime - startTime) / 1000.0}/s"

//        BulkResponse bulkResponse = bulkRequest.execute().actionGet();
//        if (bulkResponse.hasFailures()) {
//            println "failures: ${bulkResponse.buildFailureMessage()}"
//            // process failures by iterating through each bulk response item
//        }

//        client.close()

        redirect action: "list"
    }

//    @Transactional
    def upload() {
//        CommonsMultipartFile uploadedFile = request.getFile('file')
//        println "context path: "+request.contextPath
//        println "storage description: "+ uploadedFile.storageDescription
//        println "file item: "+ uploadedFile.fileItem.toString()
//        println "original filename: "+ uploadedFile.originalFilename
//        String fastaContent = uploadedFile.inputStream.text
//        Charset charset = Charset.defaultCharset()

        // for each file in, run aSync!!
//        /Users/NathanDunn/hg/kafka-project/elasticsearch/SRA/DRR000007/100splits

//        InputSupplier inputSupplier = Files.newReaderSupplier(new File("/Users/NathanDunn/Downloads/SRA/DRR000007.fastq"),Charset.defaultCharset());
//        InputSupplier inputSupplier = Files.newReaderSupplier(new File("/Users/NathanDunn/Downloads/SRA/SAMPLE.fastq"), Charset.defaultCharset());
        File file = new File(".")
        println "local file ${file.absolutePath}"
        InputSupplier inputSupplier = Files.newReaderSupplier(new File("/Users/NathanDunn/hg/kafka-project/elasticsearch/SRA/SAMPLE.fastq"), Charset.defaultCharset());
//        println "path ${request.getPathTranslated()}"
//        InputSupplier inputSupplier = Files.newReader(new File(request.getPathTranslated()));
//        InputSupplier inputSupplier = ByteStreams.newInputStreamSupplier(uploadedFile.bytes)
//        FileInputStream inStream = new FileInputStream( uploadedFile.inputStream );
//        FastqReader fastqReader = new IlluminaFastqReader();
        FastqReader fastqReader = new SangerFastqReader()



        int count = 0
        int priorCount = 0
        int epochCount
        long startTime = System.currentTimeMillis()
        long check1Time = System.currentTimeMillis()
        long check2Time = System.currentTimeMillis()
        long epochTime
        fastqReader.stream(inputSupplier, new StreamListener() {
            @Override
            void fastq(org.biojava3.sequencing.io.fastq.Fastq fastq) {
                if (fastq.getSequence().length() > 16) {
                    ++count
                    Fastq.withTransaction {
                        Fastq fastq1 = new Fastq(
                                header: fastq.description
                                , sequence: fastq.sequence
                                , quality: fastq.quality
                        ).save()
                        if (count % 200 == 0) {
                            fastq1.save(flush: true)
                            if (count % 5000 == 0) {
                                check2Time = System.currentTimeMillis()
                                epochTime = (check2Time - check1Time) / 1000.0
                                epochCount = count - priorCount
                                println "${epochCount} processed in ${epochTime} s - ${count} total, rate ${epochCount / (epochTime + 1)} "
                                priorCount = count
                                check1Time = check2Time
                            }
                        }
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
        long stopTime = System.currentTimeMillis()

        flash.message = "${count} stored in ${(stopTime - startTime) / 1000.0}/s"
//        FastaReader<ProteinSequence,AminoAcidCompound> fastaReader =
//                new FastaReader<ProteinSequence,AminoAcidCompound>(
//                        inStream,
//                        new GenericFastaHeaderParser<ProteinSequence,AminoAcidCompound>(),
//                        new ProteinSequenceCreator(AminoAcidCompoundSet.getAminoAcidCompoundSet()));
//        LinkedHashMap<String, ProteinSequence> b = fastaReader.process();
//        for (  Entry<String, ProteinSequence> entry : b.entrySet() ) {
//            System.out.println( entry.getValue().getOriginalHeader() + "=" + entry.getValue().getSequenceAsString() );
//        }

        redirect action: "list"
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

    def search() {
        println "params ${params}"

        List<Fastq> fastqInstanceList


        String queryString = ""
        if (params.query) {
            queryString += "*${params.query}*"
        }

        Long startTime , stopTime
        Long numHits = 0
        if (queryString.size() > 0) {
            startTime = System.currentTimeMillis()
            def res = Fastq.search(queryString,[size:100])
            stopTime = System.currentTimeMillis()
//            numHits = Fastq.countHits("*${params.query}*")
            fastqInstanceList = res.searchResults
            numHits = res.total
        }
        else {
            fastqInstanceList = []
        }
        Long totalTime = stopTime - startTime


//        FastqResult results = new FastqResult()
//        results.tweetResults = tweetInstanceList.sort(){ a, b ->
//            b.postDate <=> a.postDate
//        }
//        if(results.tweetResults.size()>60  ){
//            results.tweetResults = results.tweetResults.subList(0,60)
//        }

//        results.tweetTime = (stopTime - startTime) / 1000f
//        results.totalFastqs = tweetInstanceList.size()
//        render results as JSON
        flash.message = "${numHits}/${Fastq.count} found in ${totalTime} ms"
        render view: "list" , model: [fastqInstanceList:  fastqInstanceList,fastqInstanceCount: fastqInstanceList.size()]

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
