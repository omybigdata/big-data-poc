package Encryption.KeyManager

class ResourceKey {
	String encryptionKey
	String encryptionScheme
    
	static hasMany = [resource:Resource]
	
	static constraints = {		
		encryptionKey(nullable: false, blank: false)
		encryptionScheme(nullable: false, blank: false)
    }
	
	static mapping = {
		datasources(['keystore1'])
	}
}
