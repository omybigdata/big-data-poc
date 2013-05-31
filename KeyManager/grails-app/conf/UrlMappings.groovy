class UrlMappings {

	static mappings = {
		
		"/rest/resource"{
			controller = "RKey"
			action = "index"
		}
				
		"/rest/resource/$name?"{
			controller = "RKey"
			action = [GET:"show", DELETE:"delete"]
		}	
		
		"/rest/resources"{
			controller = "RKey"
			action = "list"
		}
		
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}

		"/"(view:"/index")
		"500"(view:'/error')
	}
}
