package kafka.project

import grails.converters.JSON
import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)
class TweetController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

//    def elasticSearchService

    def twitterReaderService

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)

        respond Tweet.list(params), model:[tweetInstanceCount: Tweet.count()]
    }

    def search(){

//       def res =  elasticSearchService.search("message:trying*")
//        Tweet tweet = new Tweet(
//                message: "trying to do what"
//                ,user:"asdfas"
//                ,postDate: new Date()
//        ).save(failOnError: true)

//        println "tweet count ${Tweet.count()}"

        def res = Tweet.search("message:*Lana Del Rey*")
        List<Tweet> tweetInstanceList = res.searchResults
//        println "results: ${res}"

        render view: "index", model: [tweetInstanceList:tweetInstanceList,tweetInstanceCount: res.total]

    }

    @Transactional
    def doPullTweets(){
        def stats = twitterReaderService.pullTweets(params.numTweets as Integer)
        println stats
        flash.message  = "Pulled ${stats.count} in ${stats.fetchTime} seconds."
        if(stats.count==0){
            redirect(action: "pullTweets")
        }
        else{
        redirect(action: "index",params: [sort:"postDate",order:"desc"])
        }
    }

    def show(Tweet tweetInstance) {
        respond tweetInstance
    }

    def pullTweets(){

    }

    def create() {
        respond new Tweet(params)
    }

    @Transactional
    def save(Tweet tweetInstance) {
        if (tweetInstance == null) {
            notFound()
            return
        }

        if (tweetInstance.hasErrors()) {
            respond tweetInstance.errors, view:'create'
            return
        }

        tweetInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'tweet.label', default: 'Tweet'), tweetInstance.id])
                redirect tweetInstance
            }
            '*' { respond tweetInstance, [status: CREATED] }
        }
    }

    def edit(Tweet tweetInstance) {
        respond tweetInstance
    }

    @Transactional
    def update(Tweet tweetInstance) {
        if (tweetInstance == null) {
            notFound()
            return
        }

        if (tweetInstance.hasErrors()) {
            respond tweetInstance.errors, view:'edit'
            return
        }

        tweetInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Tweet.label', default: 'Tweet'), tweetInstance.id])
                redirect tweetInstance
            }
            '*'{ respond tweetInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Tweet tweetInstance) {

        if (tweetInstance == null) {
            notFound()
            return
        }

        tweetInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Tweet.label', default: 'Tweet'), tweetInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'tweet.label', default: 'Tweet'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }

    @Transactional
    def deleteAll(){
        Tweet.deleteAll(Tweet.all)
        flash.message = "All tweets should be deleted ${Tweet.count}"
        redirect(uri: "/")
    }

    def searchTweets(){

    }

    def doSearchTweets(){
//        println "Query ${params.message}"
        List<Tweet> tweetInstanceList
        String queryString = ""
        if(params.message){
//            println "message only search"
//            queryString += "message:*${params.message}*"
            queryString += "*${params.message}*"
        }
//        if(params.tags) {
//            println "message and tag search"
//            if(queryString.size()>0) {
//                queryString += ","
//            }
//            queryString += "tags:*${params.tags}*"
////            def res = Tweet.search("message:*"+params.message+"*,tags:"+params.tags)
//        }

        if(queryString.size()>0){
//            println "queryString ${queryString}"
            def res = Tweet.search(queryString)
            tweetInstanceList = res.searchResults
        }
        else{
            tweetInstanceList = Tweet.all
        }
        render tweetInstanceList as JSON
    }
}
