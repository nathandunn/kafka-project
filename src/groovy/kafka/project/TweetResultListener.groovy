package kafka.project

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
        println "status read: ${status.text}"
        ++count
    }

    @Override
    void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {

    }

    @Override
    void onTrackLimitationNotice(int i) {

    }

    @Override
    void onScrubGeo(long l, long l1) {

    }

    @Override
    void onStallWarning(StallWarning stallWarning) {

    }
}
