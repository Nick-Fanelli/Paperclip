import com.paperclipengine.application.Input
import com.paperclipengine.graphics.Transform
import com.paperclipengine.graphics.camera.OrthographicCamera
import com.paperclipengine.graphics.render.QuadRenderer
import com.paperclipengine.scene.GameScene
import com.paperclipengine.scene.Scene
import org.joml.Vector3f
import org.joml.Vector4f

class TestScene : GameScene() {

    private val cameraSpeed = 2.0f

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

        super.onRender()
    }

}