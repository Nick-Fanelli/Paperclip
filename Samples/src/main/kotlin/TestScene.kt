import com.paperclipengine.application.Input
import com.paperclipengine.scene.Entity
import com.paperclipengine.scene.EntityComponentSystem
import com.paperclipengine.scene.GameScene
import com.paperclipengine.scene.TransformComponent

private class Player(val ecs: EntityComponentSystem) {

    val entity: Entity = ecs.createEntity()

    init {
        entity.addComponent(TransformComponent())
    }

}

class TestScene : GameScene() {

    private val cameraSpeed = 2.0f

    override fun onCreate() {
        super.onCreate()

        val ecs = EntityComponentSystem()

        for(i in 0..5) {
            val player = Player(ecs)
            ecs.destroyEntity(player.entity)
        }

        println(ecs.getAllComponentsByType<TransformComponent>())
//
//        val entity2 = ecs.createEntity()
//        val entity3 = ecs.createEntity()
//        val entity4 = ecs.createEntity()
//        val entity5 = ecs.createEntity()
//
////        println("$entity1 !$entity2 $entity3 !$entity4 $entity5")
//
//        ecs.destroyEntity(entity2)
//        ecs.destroyEntity(entity4)
//
//        val entity6 = ecs.createEntity()
//        val entity7 = ecs.createEntity()

//        println("$entity6 $entity7")

    }

    override fun onUpdate(deltaTime: Float) {
        super.onUpdate(deltaTime)

        if(input.isKey(Input.KEY_D)) {
            camera.position.x += deltaTime * cameraSpeed
        }

        if(input.isKey(Input.KEY_A)) {
            camera.position.x -= deltaTime * cameraSpeed
        }

        if(input.isKey(Input.KEY_S)) {
            camera.position.y -= deltaTime * cameraSpeed
        }

        if(input.isKey(Input.KEY_W)) {
            camera.position.y += deltaTime * cameraSpeed
        }

        if(input.scrollPosition.y != 0f) {
            camera.zoom -= deltaTime * input.scrollPosition.y * cameraSpeed
        }

        if(input.isKey(Input.KEY_ESCAPE)) {
            application.endApplication()
        }

        super.onRender()
    }

}