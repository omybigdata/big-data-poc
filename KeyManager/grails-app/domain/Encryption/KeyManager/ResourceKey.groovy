package Encryption.KeyManager

class ResourceKey {
	String encryptionKey
    
	static hasMany = [resource:Resource]
	
	static constraints = {		
		encryptionKey(nullable: false, blank: false)
    }
	
	static mapping = {
		datasources(['keystore1'])
	}
}
