package Encryption.KeyManager

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.* 

class DataSetController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [dataSetInstanceList: DataSet.list(params), dataSetInstanceTotal: DataSet.count()]
    }

    def create() {
        [dataSetInstance: new DataSet(params)]
    }

    def save() {
        def dataSetInstance = new DataSet(params)
        if (!dataSetInstance.save(flush: true)) {
            render(view: "create", model: [dataSetInstance: dataSetInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'dataSet.label', default: 'DataSet'), dataSetInstance.id])
        redirect(action: "show", id: dataSetInstance.id)
    }
	
	def saveREST = {
		def xml = request.XML
		def d = new DataSet()
		d.name = xml.name.text()
		d.description = xml.description.text()
		d.eKey = xml.eKey.text()
		
		if (!d.save(flush: true)) {
			render d.errors
		}

		response.status = 201 // Created
		render d as XML
		
		
	}

    def show(Long id) {
        def dataSetInstance = DataSet.get(id)
        if (!dataSetInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'dataSet.label', default: 'DataSet'), id])
            redirect(action: "list")
            return
        }

        [dataSetInstance: dataSetInstance]
    }
	
	def showREST(Long id) {
		def dataSetInstance = DataSet.get(id)
		if (!dataSetInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'dataSet.label', default: 'DataSet'), id])
			redirect(action: "list")
			return
		}

		render dataSetInstance as XML
	}

    def edit(Long id) {
        def dataSetInstance = DataSet.get(id)
        if (!dataSetInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'dataSet.label', default: 'DataSet'), id])
            redirect(action: "list")
            return
        }

        [dataSetInstance: dataSetInstance]
    }

    def update(Long id, Long version) {
        def dataSetInstance = DataSet.get(id)
        if (!dataSetInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'dataSet.label', default: 'DataSet'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (dataSetInstance.version > version) {
                dataSetInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'dataSet.label', default: 'DataSet')] as Object[],
                          "Another user has updated this DataSet while you were editing")
                render(view: "edit", model: [dataSetInstance: dataSetInstance])
                return
            }
        }

        dataSetInstance.properties = params

        if (!dataSetInstance.save(flush: true)) {
            render(view: "edit", model: [dataSetInstance: dataSetInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'dataSet.label', default: 'DataSet'), dataSetInstance.id])
        redirect(action: "show", id: dataSetInstance.id)
    }

    def delete(Long id) {
        def dataSetInstance = DataSet.get(id)
        if (!dataSetInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'dataSet.label', default: 'DataSet'), id])
            redirect(action: "list")
            return
        }

        try {
            dataSetInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'dataSet.label', default: 'DataSet'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'dataSet.label', default: 'DataSet'), id])
            redirect(action: "show", id: id)
        }
    }
}
