package Encryption.KeyManager

class DataSet {
	String name
	String description
	String eKey
	
    static constraints = {
		name(nullable: false, blank: false)
		description(nullable: true, blank: true)
		name(nullable: false, blank: false)
    }
	
	static mapping = {
		datasources(['keystore1'])
	}
}
