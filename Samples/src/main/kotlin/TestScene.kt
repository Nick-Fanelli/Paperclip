import com.paperclipengine.application.Input
import com.paperclipengine.graphics.Transform
import com.paperclipengine.scene.*
import org.joml.Vector3f
import org.joml.Vector4f

class TestScene : GameScene() {

    private val cameraSpeed = 2.0f

    override fun onCreate() {
        super.onCreate()


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
            camera.zoom -= deltaTime * input.scrollPosition.y
            if(camera.zoom < 0f) camera.zoom = 0f
        }

        if(input.isKey(Input.KEY_ESCAPE)) {
            application.endApplication()
        }

        super.onRender()
    }

}