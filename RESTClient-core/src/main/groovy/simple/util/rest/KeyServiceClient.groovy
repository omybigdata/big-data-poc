/**
 * 
 */
package simple.util.rest

/**
 * @author dsaha1
 *
 */
class KeyServiceClient {
	
	SimpleRESTClient restClient;
	
	/**
	 * @param hostUrl e.g., "http://localhost:8080"
	 */
	KeyServiceClient(String hostUrl){
		restClient = new SimpleRESTClient(hostUrl, false)
	}
	
	/**
	 * @param resourceName
	 * @return
	 */
	ResourceKey getKey(String resourceName){
		def resourceKey = restClient.getData("/KeyManager/rest/resource/${resourceName}", "text/xml")		
		return new ResourceKey(encryptionKey:"${resourceKey.encryptionKey}", encryptionScheme:"${resourceKey.encryptionScheme}")
	}
}
