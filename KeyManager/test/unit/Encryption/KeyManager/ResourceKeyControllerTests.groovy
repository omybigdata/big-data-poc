package Encryption.KeyManager



import org.junit.*
import grails.test.mixin.*

@TestFor(ResourceKeyController)
@Mock(ResourceKey)
class ResourceKeyControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/resourceKey/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.resourceKeyInstanceList.size() == 0
        assert model.resourceKeyInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.resourceKeyInstance != null
    }

    void testSave() {
        controller.save()

        assert model.resourceKeyInstance != null
        assert view == '/resourceKey/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/resourceKey/show/1'
        assert controller.flash.message != null
        assert ResourceKey.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/resourceKey/list'

        populateValidParams(params)
        def resourceKey = new ResourceKey(params)

        assert resourceKey.save() != null

        params.id = resourceKey.id

        def model = controller.show()

        assert model.resourceKeyInstance == resourceKey
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/resourceKey/list'

        populateValidParams(params)
        def resourceKey = new ResourceKey(params)

        assert resourceKey.save() != null

        params.id = resourceKey.id

        def model = controller.edit()

        assert model.resourceKeyInstance == resourceKey
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/resourceKey/list'

        response.reset()

        populateValidParams(params)
        def resourceKey = new ResourceKey(params)

        assert resourceKey.save() != null

        // test invalid parameters in update
        params.id = resourceKey.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/resourceKey/edit"
        assert model.resourceKeyInstance != null

        resourceKey.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/resourceKey/show/$resourceKey.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        resourceKey.clearErrors()

        populateValidParams(params)
        params.id = resourceKey.id
        params.version = -1
        controller.update()

        assert view == "/resourceKey/edit"
        assert model.resourceKeyInstance != null
        assert model.resourceKeyInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/resourceKey/list'

        response.reset()

        populateValidParams(params)
        def resourceKey = new ResourceKey(params)

        assert resourceKey.save() != null
        assert ResourceKey.count() == 1

        params.id = resourceKey.id

        controller.delete()

        assert ResourceKey.count() == 0
        assert ResourceKey.get(resourceKey.id) == null
        assert response.redirectedUrl == '/resourceKey/list'
    }
}
