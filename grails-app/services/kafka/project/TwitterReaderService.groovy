package kafka.project
import com.twitter.hbc.ClientBuilder
import com.twitter.hbc.core.Constants
import com.twitter.hbc.core.endpoint.StatusesSampleEndpoint
import com.twitter.hbc.core.processor.StringDelimitedProcessor
import com.twitter.hbc.httpclient.BasicClient
import com.twitter.hbc.httpclient.auth.Authentication
import com.twitter.hbc.httpclient.auth.OAuth1
import groovy.json.JsonSlurper

import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.TimeUnit
//@Transactional
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

        // Do whatever needs to be done with messages
        for (int msgRead = 0; msgRead < 10; msgRead++) {
            if (client.isDone()) {
                System.out.println("Client connection closed unexpectedly: " + client.getExitEvent().getMessage());
                break;
            }

            String msg = queue.poll(5, TimeUnit.SECONDS);
            if (msg == null) {
                System.out.println("Did not receive a message in 5 seconds");
            } else {
                def result = slurper.parseText(msg)
//                System.out.println(result.text);
                tweetList.add(result.text)
            }
        }

        client.stop();

        return tweetList
    }
}
