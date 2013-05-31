package Encryption.KeyManager

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.*

class ResourceKeyController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [resourceKeyInstanceList: ResourceKey.list(params), resourceKeyInstanceTotal: ResourceKey.count()]
    }

    def create() {
        [resourceKeyInstance: new ResourceKey(params)]
    }

    def save() {
        def resourceKeyInstance = new ResourceKey(params)
        if (!resourceKeyInstance.save(flush: true)) {
            render(view: "create", model: [resourceKeyInstance: resourceKeyInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'resourceKey.label', default: 'ResourceKey'), resourceKeyInstance.id])
        redirect(action: "show", id: resourceKeyInstance.id)
    }

    def show(Long id) {
        def resourceKeyInstance = ResourceKey.get(id)
        if (!resourceKeyInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'resourceKey.label', default: 'ResourceKey'), id])
            redirect(action: "list")
            return
        }

        [resourceKeyInstance: resourceKeyInstance]
    }
	
	def showREST(Long id) {
		def resourceKeyInstance = ResourceKey.get(id)
		if (!resourceKeyInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'resourceKey.label', default: 'ResourceKey'), id])
			redirect(action: "list")
			return
		}

		render resourceKeyInstance as  XML
	}

    def edit(Long id) {
        def resourceKeyInstance = ResourceKey.get(id)
        if (!resourceKeyInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'resourceKey.label', default: 'ResourceKey'), id])
            redirect(action: "list")
            return
        }

        [resourceKeyInstance: resourceKeyInstance]
    }

    def update(Long id, Long version) {
        def resourceKeyInstance = ResourceKey.get(id)
        if (!resourceKeyInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'resourceKey.label', default: 'ResourceKey'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (resourceKeyInstance.version > version) {
                resourceKeyInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'resourceKey.label', default: 'ResourceKey')] as Object[],
                          "Another user has updated this ResourceKey while you were editing")
                render(view: "edit", model: [resourceKeyInstance: resourceKeyInstance])
                return
            }
        }

        resourceKeyInstance.properties = params

        if (!resourceKeyInstance.save(flush: true)) {
            render(view: "edit", model: [resourceKeyInstance: resourceKeyInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'resourceKey.label', default: 'ResourceKey'), resourceKeyInstance.id])
        redirect(action: "show", id: resourceKeyInstance.id)
    }

    def delete(Long id) {
        def resourceKeyInstance = ResourceKey.get(id)
        if (!resourceKeyInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'resourceKey.label', default: 'ResourceKey'), id])
            redirect(action: "list")
            return
        }

        try {
            resourceKeyInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'resourceKey.label', default: 'ResourceKey'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'resourceKey.label', default: 'ResourceKey'), id])
            redirect(action: "show", id: id)
        }
    }
}
