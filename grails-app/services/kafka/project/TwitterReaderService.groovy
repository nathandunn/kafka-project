package kafka.project

import com.twitter.hbc.ClientBuilder
import com.twitter.hbc.core.Constants
import com.twitter.hbc.core.endpoint.StatusesSampleEndpoint
import com.twitter.hbc.core.processor.StringDelimitedProcessor
import com.twitter.hbc.httpclient.BasicClient
import com.twitter.hbc.httpclient.auth.Authentication
import com.twitter.hbc.httpclient.auth.OAuth1
import grails.transaction.Transactional
import groovy.json.JsonSlurper

import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue

//import org.apache.hadoop.fs.Path
//import org.apache.hadoop.io.IntWritable
//import org.apache.hadoop.io.Text
//import org.apache.hadoop.mapred.*
import java.util.concurrent.TimeUnit

@Transactional
class TwitterReaderService {

    def findSimpleStream() {

        String consumerKey = System.getenv("TWITTER_CONSUMER_TOKEN")
        String consumerSecret = System.getenv("TWITTER_CONSUMER_SECRET")
        String token = System.getenv("TWITTER_TOKEN")
        String secret = System.getenv("TWITTER_SECRET")


        Authentication auth = new OAuth1(consumerKey, consumerSecret, token, secret);

        // Define our endpoint: By default, delimited=length is set (we need this for our processor)
        // and stall warnings are on.
        StatusesSampleEndpoint endpoint = new StatusesSampleEndpoint();
        endpoint.stallWarnings(false);

        // Create an appropriately sized blocking queue
        BlockingQueue<String> queue = new LinkedBlockingQueue<String>(10000);

        BasicClient client = new ClientBuilder()
                .name("sampleExampleClient")
                .hosts(Constants.STREAM_HOST)
                .endpoint(endpoint)
                .authentication(auth)
                .processor(new StringDelimitedProcessor(queue))
                .build();

        // Establish a connection
        client.connect();

        def slurper = new JsonSlurper()
//        def result = slurper.parseText('{"person":{"name":"Guillaume","age":33,"pets":["dog","cat"]}}')

        List<String> tweetList = new ArrayList<>()

        long startTime = System.currentTimeMillis()
        // Do whatever needs to be done with messages
        int numMessages = 2000
        for (int msgRead = 0; msgRead < numMessages ; msgRead++) {
            if (client.isDone()) {
                System.out.println("Client connection closed unexpectedly: " + client.getExitEvent().getMessage());
                break;
            }

            String msg = queue.poll(5, TimeUnit.SECONDS);
            if (msg == null) {
                System.out.println("Did not receive a message in 5 seconds");
            } else {
                def result = slurper.parseText(msg)
//                if(msgRead < 5){
//                    println "${msgRead} FULL: ${result}"
//                }
//                println "${msgRead} TEXT: ${result.text}"
                tweetList.add(result.text)
//                println ""
            }
        }
        long stopTime = System.currentTimeMillis()
        double totalTime = (stopTime - startTime) / 1000.0
        println "Total time: ${ totalTime } seconds "
        println "Messages per sec: ${ numMessages  / totalTime } "

        client.stop();

        return tweetList
    }

    def pullTweets(Integer numMessages) {

        String consumerKey = System.getenv("TWITTER_CONSUMER_TOKEN")
        String consumerSecret = System.getenv("TWITTER_CONSUMER_SECRET")
        String token = System.getenv("TWITTER_TOKEN")
        String secret = System.getenv("TWITTER_SECRET")


        Authentication auth = new OAuth1(consumerKey, consumerSecret, token, secret);

        // Define our endpoint: By default, delimited=length is set (we need this for our processor)
        // and stall warnings are on.
        StatusesSampleEndpoint endpoint = new StatusesSampleEndpoint();
        endpoint.stallWarnings(false);

        // Create an appropriately sized blocking queue
        BlockingQueue<String> queue = new LinkedBlockingQueue<String>(10000);

        BasicClient client = new ClientBuilder()
                .name("sampleExampleClient")
                .hosts(Constants.STREAM_HOST)
                .endpoint(endpoint)
                .authentication(auth)
                .processor(new StringDelimitedProcessor(queue))
                .build();

        // Establish a connection
        client.connect();

        def slurper = new JsonSlurper()
//        def result = slurper.parseText('{"person":{"name":"Guillaume","age":33,"pets":["dog","cat"]}}')

        long startTime = System.currentTimeMillis()
        int count = 0
        // Do whatever needs to be done with messages
        try {
            for (int msgRead = 0; msgRead < numMessages ; msgRead++) {
                if (client.isDone()) {
                    System.out.println("Client connection closed unexpectedly: " + client.getExitEvent().getMessage());
                    break;
                }

                String msg = queue.poll(5, TimeUnit.SECONDS);
                if (msg == null) {
                    System.out.println("Did not receive a message in 5 seconds");
                } else {
                    def result = slurper.parseText(msg)
    //                if(msgRead < 5){
    //                    println "${msgRead} FULL: ${result}"
    //                }
    //                println "${msgRead} TEXT: ${result}"
                    if(result.user && result.entities){
//                        println "result:[${result}]"
                        List<String> hashtags = new ArrayList<>()
                        result.entities.each{ entity ->
//                            println "entity:[${entity}]"
//                            println "entity properties:[${entity.properties}]"
//                            println "entity key:[${entity.key}]"
//                            println "entity value:[${entity.value}]"
                            if(entity.key=="hashtags"){
//                            println "tags value:[${entity.value}]"
                                entity.value.each{ hashtag ->
                                    println "hashtag:[${hashtag}]"
                                    hashtags.add(hashtag.text)
                                }
                            }
                        }
                        Tweet tweet = new Tweet(
                                message: result.text
                                ,user: result.user.name
    //                        created_at: TueAug0517: 32: 50+00002014,
    //                        ,postDate: result.created_at
                                ,tags: hashtags
                                ,postDate: new Date()
                        ).save()
                        ++count
                    }
    //                println ""
                }

            }
        } catch (e) {
            println "some error ${e}"
        } finally {
            client.stop();
        }
        long stopTime = System.currentTimeMillis()
        double totalTime = (stopTime - startTime) / 1000.0
        println "Total time: ${ totalTime } seconds "
        println "Messages per sec: ${ count / totalTime } "


        return [count:count,fetchTime:totalTime]
//        return tweetList
    }


//    def doHadoop(){
//        JobConf conf = new JobConf(WordCounter.class);
//        conf.setJobName("wordcount");
//
//        conf.setOutputKeyClass(Text.class);
//        conf.setOutputValueClass(IntWritable.class);
//
//        conf.setMapperClass(Map.class);
//        conf.setCombinerClass(WordCounter.Reduce.class);
//        conf.setReducerClass(WordCounter.Reduce.class);
//
//        conf.setInputFormat(TextInputFormat.class);
//        conf.setOutputFormat(TextOutputFormat.class);
//
//        Path inputPath = new Path("data/input1.txt");
//        File file = new File("data/output_data");
//        if(file.exists()){
//            FileUtils.deleteDirectory(file);
//        }
//        Path outputPath = new Path("data/output_data");
//
//        FileInputFormat.setInputPaths(conf, inputPath);
//        FileOutputFormat.setOutputPath(conf, outputPath);
//        JobClient.runJob(conf);
//
//    }
}
