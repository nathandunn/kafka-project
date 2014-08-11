package kafka.project

import com.google.common.collect.Lists
import grails.transaction.Transactional
import twitter4j.FilterQuery
import twitter4j.TwitterStream
import twitter4j.TwitterStreamFactory
import twitter4j.conf.ConfigurationBuilder


@Transactional
class Twitter4JReaderService {

    def pullTweets(Integer numMessages) {
        System.out.println "ENTERING"

        println "trying to pull tweets ${numMessages}"

        String consumerKey = System.getenv("TWITTER_CONSUMER_TOKEN")
        String consumerSecret = System.getenv("TWITTER_CONSUMER_SECRET")
        String token = System.getenv("TWITTER_TOKEN")
        String secret = System.getenv("TWITTER_SECRET")

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true);
        cb.setOAuthConsumerKey(consumerKey);
        cb.setOAuthConsumerSecret(consumerSecret);
        cb.setOAuthAccessToken(token);
        cb.setOAuthAccessTokenSecret(secret);

//        TwitterStreamFactory twitterStreamFactory = new TwitterStreamFactory()
        TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance()
//        AccessToken accessToken = new AccessToken(token,secret)
//
//        TwitterStream twitterStream = twitterStreamFactory.getInstance()
//        twitterStream.setOAuthConsumer(consumerKey, consumerSecret);
//        twitterStream.setOAuthAccessToken(accessToken)


        TweetResultListener tweetResultListener = new TweetResultListener( maxCount: numMessages )
        twitterStream.addListener(tweetResultListener)



        List terms = []
        terms << "#mockumentary"
        terms << "#mtvhottest"
        terms << "#iphone"
        terms << "#ipad"
        terms << "#ipadgames"
        terms << "#gameinsight"
        terms << "#RT"
        terms << "#android"
        terms << "#androidgames"
        terms << "#BestFandom2014"
        terms << "#RETWEET"
        terms << "#TeamFollowBack"

        FilterQuery filterQuery = new FilterQuery()
        filterQuery.track(Lists.newArrayList(terms) as String[])
        String[] language = [ "en"]
        filterQuery.language(language)

        twitterStream.filter(filterQuery)

//        twitterStream.sample()
        long startTime = System.currentTimeMillis()
        int count = 0
        while (tweetResultListener.count < tweetResultListener.maxCount) {
            println "sleeping ${tweetResultListener.count} < ${tweetResultListener.maxCount}"
            new Object().sleep(1000)
            List<Tweet> tweetList = tweetResultListener.drainTweets()
            for(Tweet tweet in tweetList){
                tweet.save()
            }

            println "awake ${tweetResultListener.count} < ${tweetResultListener.maxCount}"
        }
        twitterStream.removeListener(tweetResultListener)
        twitterStream.shutdown()
        println "FINISHED ${tweetResultListener.count}"

        long stopTime = System.currentTimeMillis()
        double totalTime = (stopTime - startTime) / 1000.0

        return [count: tweetResultListener.count, fetchTime: totalTime]
    }

}
