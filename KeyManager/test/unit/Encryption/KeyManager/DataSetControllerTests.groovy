package Encryption.KeyManager



import org.junit.*
import grails.test.mixin.*

@TestFor(DataSetController)
@Mock(DataSet)
class DataSetControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/dataSet/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.dataSetInstanceList.size() == 0
        assert model.dataSetInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.dataSetInstance != null
    }

    void testSave() {
        controller.save()

        assert model.dataSetInstance != null
        assert view == '/dataSet/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/dataSet/show/1'
        assert controller.flash.message != null
        assert DataSet.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/dataSet/list'

        populateValidParams(params)
        def dataSet = new DataSet(params)

        assert dataSet.save() != null

        params.id = dataSet.id

        def model = controller.show()

        assert model.dataSetInstance == dataSet
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/dataSet/list'

        populateValidParams(params)
        def dataSet = new DataSet(params)

        assert dataSet.save() != null

        params.id = dataSet.id

        def model = controller.edit()

        assert model.dataSetInstance == dataSet
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/dataSet/list'

        response.reset()

        populateValidParams(params)
        def dataSet = new DataSet(params)

        assert dataSet.save() != null

        // test invalid parameters in update
        params.id = dataSet.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/dataSet/edit"
        assert model.dataSetInstance != null

        dataSet.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/dataSet/show/$dataSet.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        dataSet.clearErrors()

        populateValidParams(params)
        params.id = dataSet.id
        params.version = -1
        controller.update()

        assert view == "/dataSet/edit"
        assert model.dataSetInstance != null
        assert model.dataSetInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/dataSet/list'

        response.reset()

        populateValidParams(params)
        def dataSet = new DataSet(params)

        assert dataSet.save() != null
        assert DataSet.count() == 1

        params.id = dataSet.id

        controller.delete()

        assert DataSet.count() == 0
        assert DataSet.get(dataSet.id) == null
        assert response.redirectedUrl == '/dataSet/list'
    }
}
