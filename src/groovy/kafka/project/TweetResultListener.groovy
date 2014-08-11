package kafka.project

import twitter4j.RawStreamListener

/**
 * Created by NathanDunn on 8/11/14.
 */
class TweetResultListener implements RawStreamListener{

    Integer count = 0

    @Override
    void onMessage(String s) {

    }

    @Override
    void onException(Exception e) {

    }
}
