package Encryption.KeyManager

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.XML
import grails.plugins.springsecurity.Secured
import groovy.xml.MarkupBuilder


class RKeyController {

    static allowedMethods = [save:  ["POST", "PUT"], delete: "DELETE"]
    
	@Secured(['ROLE_ADMIN'])
    def index = {		
		switch(request.method){
			case "GET":
			  if(params.name){
				  redirect(action: "show", params: params)
			  }
			  else {
				  redirect(action: "list", params: params)
			  }
			  break
			
			case "PUT":    //...
				redirect(action: "save", params: params)
				break
			case "POST":   //...
			  redirect(action: "save", params: params)
			  break
			case "DELETE": //...
			  if(params.name){
				redirect(action: "delete", params: params)
			  }else{
				response.status = 400 //Bad Request
				render """DELETE request must include the resource name
						Example: /rest/resource/aresource"""
			  }
			  break
		}	        
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        def resourceInstanceList = Resource.list(params)		
		render resourceInstanceList as XML
    }

    /**
     * @return
     */
    def save() {
		def xml = request.XML
		
		//If resource name exists, 
		//it will update existing resource or create a new resource
		def r = Resource.findByName(xml.resourceName.text())
		if (!r) {
			r = new Resource()
			r.name = xml.resourceName.text()
		}		
		r.description = xml.resourceDescription.text()
		
		//if encryption key exist, it will assign the key to the 
		//resource otherwise create the key in the key table
        def rk = ResourceKey.findByEncryptionKeyAndEncryptionScheme(xml.encryptionKey.text(),xml.encryptionScheme.text())
		if(!rk) {
			rk = new ResourceKey()
			rk.encryptionKey    = xml.encryptionKey.text()
			rk.encryptionScheme = xml.encryptionScheme.text()
			
			if(!rk.save()){
				render rk.errors
			}
		}
		
		r.rKey = rk
		
        if (r.save()){
			response.status = 201 // Created
			render r as XML
		}else {
			render r.errors
		}
    }

    /**
     * method for REST GET
     * @param n
     * @return
     */
    def show(String name) {
		if(params.name){
			def resourceExists = true
			def r = Resource.findByName(params.name)
			//if resource does not exist, create one
			if (!r) {
				resourceExists = false
	            r = new Resource()
				r.name = params.name				
				
				//generate a key for the resouce
				def rk = new ResourceKey()
				rk.encryptionKey = Encryption.KeyGenerator.generateKey()
				rk.encryptionScheme = "DESede"
				//assign the key to the resource
				r.rKey = rk
				//save the key
				if(!rk.save()){
					render rk.errors
				}
				//save the resouce				
				if (r.save()){
					response.status = 201 // Created
				}else {
					render r.errors
				}
			}
			
			render(contentType:"text/xml"){
				resourceKey{
					resourceName(r.name)
					resourceDescription(r.description)
					encryptionKey(r.rKey.encryptionKey)
					encryptionScheme(r.rKey.encryptionScheme)
				}
			}
		}else{
			response.status = 400 //Bad Request
			render """GET request must include the resource name
								Example: /rest/resource/aresource"""
		}		
    }
	
	def delete(String name) {
		
		if(params.name){
			def r = Resource.findByName(params.name)
			if (!r) {
				flash.message = message(code: 'default.not.found.message', args: [message(code: 'resource.label', default: 'Resource'), params.name])
	            response.sendError(404, "Resource not found for Resouce Name: " + params.name)
			}
	
			//try to delete the resource
			try {
				r.delete(flush: true)
				response.status = 200
				render "Successfully Deleted."
			}catch (DataIntegrityViolationException e) {
				flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'resource.label', default: 'Resource'), params.name])
				response.sendError(404, "Resource : " + params.name + " can not be deleted.")
			}			
			
		}else{
			response.status = 400 //Bad Request
			render """DELETE request must include the resource name
								Example: /rest/resource/aresource"""
		}
		
		
	}

    def edit(Long id) {
        def resourceInstance = Resource.get(id)
        if (!resourceInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'resource.label', default: 'Resource'), id])
            redirect(action: "list")
            return
        }

        [resourceInstance: resourceInstance]
    }

    def update(Long id, Long version) {
        def resourceInstance = Resource.get(id)
        if (!resourceInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'resource.label', default: 'Resource'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (resourceInstance.version > version) {
                resourceInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'resource.label', default: 'Resource')] as Object[],
                          "Another user has updated this Resource while you were editing")
                render(view: "edit", model: [resourceInstance: resourceInstance])
                return
            }
        }

        resourceInstance.properties = params

        if (!resourceInstance.save(flush: true)) {
            render(view: "edit", model: [resourceInstance: resourceInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'resource.label', default: 'Resource'), resourceInstance.id])
        redirect(action: "show", id: resourceInstance.id)
    }

    
}
