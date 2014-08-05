class UrlMappings {

	static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

//        "/"(view:"/index")
        "/"(action: "/pullTweets",controller: "tweet")
        "500"(view:'/error')
	}
}
