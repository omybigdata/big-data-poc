package Encryption.KeyManager

import groovyx.net.http.HTTPBuilder
import groovyx.net.http.*
import org.junit.Test;
import static org.junit.Assert.*;
import grails.plugins.springsecurity.*


class TestSecureController {
	
	SpringSecurityService springSecurityService = new SpringSecurityService()
	
	
	public static void main(String args[]) {
		
		def authSite = new HTTPBuilder( 'http://localhost:8080/KeyManager/rest/resources' )
		def authConfig = new AuthConfig(authSite)
		authConfig.basic("admin", springSecurityService.encodePassword('admin'))
		//authSite.setAuthConfig(authConfig)
		def data = authSite.get( path:'/KeyManager/rest/resources' )
		
		println(data.toString())
		
		data.each(){ it ->
				println "resourceName = ${it.name}"
				String name = it.name
				assertTrue name != null && !name.trim().equals("")
				
				println "Description = ${it.description}"
				String description = it.description
				assertTrue description != null && !description.trim().equals("")
		}
		
	}
}
