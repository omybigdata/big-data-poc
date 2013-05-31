package simple.util.rest


import groovyx.net.http.RESTClient
import groovy.util.slurpersupport.GPathResult
import static groovyx.net.http.ContentType.URLENC

import java.security.cert.X509Certificate

import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

import org.apache.http.conn.scheme.Scheme
import org.apache.http.conn.scheme.SchemeRegistry
import org.apache.http.conn.ssl.SSLSocketFactory

import simple.util.rest.SimpleRESTClient;

class SimpleRESTClient {
	
	RESTClient restClient
	
	/**
	 * @param url , example: http://localhost:8080
	 * @param doTrust - indicates whether to trust the remote server - way to override security exceptions
	 */
	SimpleRESTClient(String url, boolean doTrust) {
	   restClient = new RESTClient(url)		
	   if (doTrust == true){
		   SSLContext sc = SSLContext.getInstance("SSL")
		   sc.init(null, getTrustAllManager(), null)
		   SSLSocketFactory socketFactory = new SSLSocketFactory(sc)
		   Scheme sch = new Scheme("https", 443, socketFactory)
		   restClient.getClient().getConnectionManager().getSchemeRegistry().register(sch)
	   }
	}
	
	
	/**
	 * @param userName
	 * @param password
	 */
	void authenticate(String userName, String password){
		restClient.auth.basic(userName, password)
	}
	    
    /**
     * @return TrustManagers without verifying any certificate
     */
    private static TrustManager[] getTrustAllManager() {
        X509TrustManager tm = 
        new X509TrustManager() {
                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null
                    }
        
                    @Override
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        // Do nothing
                    }
        
                    @Override
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        // Do nothing
                    }            
        }
        TrustManager[] tma = new TrustManager[1]
        tma[0] = tm
        /**
         * @param rPath
         * @param rContentType
         * @return
         */
        return tma
    }
	
	/**
	 * @param rPath - resource relative path; example: "/amp-framework-cm-grails-1.0-SNAPSHOT/serviceVersion/showREST/11"
	 * @param rContentType - example: "text/xml"
	 * @return Parsed data Object 
	 */
	Object getData(String rPath, String rContentType){		
		def resp = restClient.get(path: rPath, 
			contentType: rContentType, 
			Authorization: "Basic YWRtaW46YWRtaW4=", 
			Connection: "keep-alive", 
			Accept: "text/xml")
		assert resp.status == 200 
		def parsedDataObject = resp.getData()
		assert parsedDataObject.size() > 0
		return parsedDataObject
	}
		
	/**
	 * @param rPath
	 * @param rContentType
	 * @param payload
	 * @return
	 */
	def post(rPath, rContentType, payload){
		restClient.post( path: rPath, body: payload, contentType: XML, requestContentType: XML ) { resp, xml ->	
		  println "Response status: ${resp.statusLine}"		  
		  resp.headers.each { it ->
			  println "${it.name} : ${it.value}"
		  }
		  
		  println resp.statusLine.statusCode
		}
	}
	
	    
    public static void main(String[] args) {
		
		SimpleRESTClient restClient = new SimpleRESTClient("http://localhost:8080", false)
		def service = restClient.getData("/amp-framework-cm-grails-1.0-SNAPSHOT/serviceVersion/showREST/11", "text/xml")
		
		println "Parasing "
						
		println "serviceName = ${service.serviceName}"
		println "serviceVersion = ${service.serviceVersion}"
		println "serViceVersionDesc = ${service.serViceVersionDesc}"
		
		println "global"
		service.global.property.each{ 
			println "	property.key = ${it.@key}"
			println "	property.value = ${it.@value}"
		}
		
		println "parameters"
		service.parameters.parameter.each{ 
			println "	parameter.key = ${it.@key}"
			println "	parameter.defaultValue = ${it.@defaultValue}"
		}
		
		println "properties"
		service.properties.property.each{
			println "	property.key = ${it.@key}"
			println "	property.value = ${it.@value}"
		}
    }    
}