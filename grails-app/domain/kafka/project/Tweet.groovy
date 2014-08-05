package kafka.project

class Tweet {

    static searchable = true


    static constraints = {
    }

    Long twitterId
    Long userId
    String userName
    Date postDate
    String message
    String tags  // double-pipe separated

    def getMessageStart() {
        int maxLength = 30
        if(message?.size()>maxLength){
            return message.subSequence(0,maxLength) + "..."
        }
        else{
            return message
        }
    }
}
