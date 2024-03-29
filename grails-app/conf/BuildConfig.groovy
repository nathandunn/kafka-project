grails.servlet.version = "3.0" // Change depending on target container compliance (2.5 or 3.0)
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.work.dir = "target/work"
grails.project.target.level = 1.6
grails.project.source.level = 1.6
//grails.project.war.file = "target/${appName}-${appVersion}.war"

grails.project.fork = [
    // configure settings for compilation JVM, note that if you alter the Groovy version forked compilation is required
    //  compile: [maxMemory: 256, minMemory: 64, debug: false, maxPerm: 256, daemon:true],

    // configure settings for the test-app JVM, uses the daemon by default
    test: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, daemon:true],
    // configure settings for the run-app JVM
    run: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, forkReserve:false],
    // configure settings for the run-war JVM
    war: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, forkReserve:false],
    // configure settings for the Console UI JVM
    console: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256]
]

grails.project.dependency.resolver = "maven" // or ivy
grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // specify dependency exclusions here; for example, uncomment this to disable ehcache:
        // excludes 'ehcache'
    }
    log "error" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    checksums true // Whether to verify checksums on resolve
    legacyResolve false // whether to do a secondary resolve on plugin installation, not advised and here for backwards compatibility

    repositories {
        inherits true // Whether to inherit repository definitions from plugins

        grailsPlugins()
        grailsHome()
        mavenLocal()
        grailsCentral()
        mavenCentral()
        // uncomment these (or add new ones) to enable remote dependency resolution from public Maven repositories
        //mavenRepo "http://repository.codehaus.org"
        //mavenRepo "http://download.java.net/maven/2/"
        //mavenRepo "http://repository.jboss.com/maven2/"
        mavenRepo "http://maven.twttr.com/twitter-twttr"
        mavenRepo "http://www.biojava.org/download/maven/"
    }

    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes e.g.
        // runtime 'mysql:mysql-connector-java:5.1.27'
        // runtime 'org.postgresql:postgresql:9.3-1100-jdbc41'

//        http://kafka.apache.org/documentation.html#introduction
//        runtime 'org.apache.kafka:kafka_2.10:0.8.1.1'

        // https://developer.yahoo.com/hadoop/tutorial/
//        runtime 'org.apache.hadoop:hadoop-core:1.2.1'

//        https://github.com/twitter/hbc
        // twitter APi
//        runtime 'com.twitter:hbc-core:2.0.2'
        runtime 'org.twitter4j:twitter4j-core:4.0.2'
        runtime 'org.twitter4j:twitter4j-stream:4.0.2'
        runtime 'com.twitter:hbc-core:2.2.0'
        runtime 'com.twitter:hbc-twitter4j:2.2.0'
        runtime 'org.slf4j:slf4j-api:1.7.7'
        runtime 'org.biojava:biojava3-core:3.0.8'
        runtime 'org.biojava:biojava3-sequencing:3.0.8'
        runtime 'org.elasticsearch:elasticsearch:1.3.1'



//        compile 'com.twitter:hbc-core:2.2.0'
//        runtime 'org.apache.storm:storm-core:0.9.1-incubating'

    }

    plugins {
        // plugins for the build system only
        build ":tomcat:7.0.52.1"

        // plugins for the compile step
        compile ":scaffolding:2.0.3"
        compile ':cache:1.1.2'

        runtime ':elasticsearch:0.0.3.2'
//        compile ":elasticsearch-gorm:0.0.2.4"

//        compile(":spring-security-oauth:2.1.0-RC4"){
//            excludes "scribe"
////            org.scribe:scribe:1.3.6
//        }
//        compile(':spring-security-oauth-twitter:0.1'){
//            excludes "scribe"
//            excludes "spring-security-core"
////            excludes "spring-security-web"
//        }

//        compile 'com.twitter:hbc-core:1.4.2'


        compile ":kickstart-with-bootstrap:1.1.0"
//        compile ":twitter-bootstrap:3.1.1.3"





        // plugins needed at runtime but not for compilation
        runtime ":hibernate:3.6.10.13" // or ":hibernate4:4.3.5.1"
        runtime ":database-migration:1.4.0"
        runtime ":jquery:1.11.0.2"
        runtime ":resources:1.2.7"
//        runtime ':oauth:2.0.1'
        // Uncomment these (or add new ones) to enable additional resources capabilities
        //runtime ":zipped-resources:1.0.1"
        //runtime ":cached-resources:1.1"
        //runtime ":yui-minify-resources:0.1.5"
//        compile ":executor:0.3"

        // An alternative to the default resources plugin is the asset-pipeline plugin
        //compile ":asset-pipeline:1.6.1"

        // Uncomment these to enable additional asset-pipeline capabilities
        //compile ":sass-asset-pipeline:1.5.5"
        //compile ":less-asset-pipeline:1.5.3"
        //compile ":coffee-asset-pipeline:1.5.0"
        //compile ":handlebars-asset-pipeline:1.3.0.1"
    }
}
