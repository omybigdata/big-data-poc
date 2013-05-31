package Encryption.KeyManager

class Resource {
	String name
	String description
	ResourceKey rKey
	
    static constraints = {
		name(nullable: false, blank: false)
		description(nullable: true, blank: true)
    }
	
	static mapping = {
		datasources(['keystore1'])
	}
}
