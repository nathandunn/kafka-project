package kafka.project

import groovy.json.JsonSlurper
import twitter4j.HashtagEntity
import twitter4j.StallWarning
import twitter4j.Status
import twitter4j.StatusDeletionNotice
import twitter4j.StatusListener

/**
 * Created by NathanDunn on 8/11/14.
 */
//class TweetResultListener implements RawStreamListener{
class TweetResultListener implements StatusListener {

    Integer count = 0
    Integer maxCount = 20
    def slurper = new JsonSlurper()
    List<Tweet> tweetList = new ArrayList<>()

//    @Override
//    void onMessage(String s) {
//        println "read message: ${s.length()} -> ${count} < ${maxCount}"
//        ++count
//    }

    @Override
    void onException(Exception e) {
        println "ERROR: ${e}"
    }

    @Override
    void onStatus(Status status) {
        ++count
//        println "status read: ${status.text}"
        List<String> tags = new ArrayList<>()
        for(HashtagEntity hashtagEntity in status.hashtagEntities){
            tags << hashtagEntity.text
        }
        Tweet tweet = new Tweet(
                twitterId: status.id
                ,message: status.text
//                                ,userName: result.user.name
                ,userName: status.user.screenName
                ,userId: status.user.id
                //                        created_at: TueAug0517: 32: 50+00002014,
                //                        ,postDate: result.created_at
                ,tags: tags.join("||")
                ,postDate: status.createdAt
        )
        tweetList.add(tweet)

    }

    @Override
    void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
//        println "status deletion? ${statusDeletionNotice.userId}"
    }

    @Override
    void onTrackLimitationNotice(int i) {
        println("gtrack limitation ${i}")
    }

    @Override
    void onScrubGeo(long l, long l1) {
        println "scrub geo ${l} ${l1} "
    }

    @Override
    void onStallWarning(StallWarning stallWarning) {
        println("stall wraning ${stallWarning.message} / full: ${stallWarning.percentFull}")
    }

    List<Tweet> drainTweets() {
        List<Tweet> returnList = new ArrayList<Tweet>(tweetList)
        tweetList.clear()
        return returnList
    }
}
