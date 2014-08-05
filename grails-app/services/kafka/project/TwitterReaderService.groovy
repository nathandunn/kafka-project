package kafka.project
import com.twitter.hbc.ClientBuilder
import com.twitter.hbc.core.Constants
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint
import com.twitter.hbc.core.endpoint.StatusesSampleEndpoint
import com.twitter.hbc.core.processor.StringDelimitedProcessor
import com.twitter.hbc.httpclient.BasicClient
import com.twitter.hbc.httpclient.auth.Authentication
import com.twitter.hbc.httpclient.auth.OAuth1
import grails.transaction.Transactional
import groovy.json.JsonSlurper
import org.elasticsearch.common.collect.Lists

import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.TimeUnit
//import org.apache.hadoop.fs.Path
//import org.apache.hadoop.io.IntWritable
//import org.apache.hadoop.io.Text
//import org.apache.hadoop.mapred.*
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
//        StatusesSampleEndpoint endpoint = new StatusesSampleEndpoint();
        StatusesFilterEndpoint endpoint = new StatusesFilterEndpoint();
        endpoint.stallWarnings(false);
//        Location.Coordinate southwest = new Location.Coordinate(-87.648926, 41.883109);
//        Location.Coordinate northeast = new Location.Coordinate(-87.606354, 41.828050);
//        Location chicago = new Location(southwest, northeast);
//        endpoint.locations(Lists.newArrayList(chicago));

        List terms = []
        terms << "twitterapi"
        terms << "#yolo"
        terms << "#photographphotographyy"
//        terms << "#elasticsearch"
        terms << "#mockumentary"
        terms << "#mtvhottest"
        terms << "#iphone"

        endpoint.trackTerms(Lists.newArrayList(terms));
        endpoint.languages(Lists.newArrayList("en"))

//        endpoint.addQueryParameter("retweet","true")

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
                    if(result.user && result.entities){
//                        println "result:[${result}]"
                        List<String> hashtags = new ArrayList<>()
                        result.entities.each{ entity ->
                            if(entity.key=="hashtags"){
                                entity.value.each{ hashtag ->
//                                    println "hashtag:[${hashtag}]"
                                    hashtags.add(hashtag.text)
                                }
                            }
                        }
                        println "adding with hashtag ${hashtags}"
                        Tweet tweet = new Tweet(
                                twitterId: result.id
                                ,message: result.text
//                                ,userName: result.user.name
                                ,userName: result.user.screen_name
                                ,userId: result.user.id
    //                        created_at: TueAug0517: 32: 50+00002014,
    //                        ,postDate: result.created_at
                                ,tags: hashtags?.join("||")
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
//        println "Total time: ${ totalTime } seconds "
//        println "Messages per sec: ${ count / totalTime } "


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
