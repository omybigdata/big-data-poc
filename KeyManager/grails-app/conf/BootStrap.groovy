import Encryption.KeyManager.*

class BootStrap {

    def init = { servletContext ->
		def adminRole = new Role(authority: 'ROLE_ADMIN').save(flush: true)
		def userRole = new Role(authority: 'ROLE_USER').save(flush: true)
  
		def adminUser = new User(username: 'admin', enabled: true, password: 'admin')
		adminUser.save(flush: true)
		UserRole.create adminUser, adminRole, true
		
		def aUser = new User(username: 'auser', enabled: true, password: 'password')
		aUser.save(flush: true)
		UserRole.create aUser, userRole, true
		
  
		assert User.count() == 2
		assert Role.count() == 2
		assert UserRole.count() == 2
    }
	
    def destroy = {
    }
}
