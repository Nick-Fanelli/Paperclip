import com.paperclipengine.graphics.render.QuadRenderer
import com.paperclipengine.graphics.Transform
import com.paperclipengine.scene.Scene
import org.joml.Vector3f

class TestScene : Scene() {

    private lateinit var quadRenderer: QuadRenderer

    private val transform = Transform(
        Vector3f(0.0f, 0.0f, 0.0f)
    )

    override fun onCreate() {
        super.onCreate()

        quadRenderer = QuadRenderer()
        quadRenderer.create()
    }

    override fun onUpdate(deltaTime: Float) {
        super.onUpdate(deltaTime)

        quadRenderer.begin()

        quadRenderer.drawQuad(transform)

        quadRenderer.end()

    }

    override fun onDestroy() {
        super.onDestroy()

        quadRenderer.destroy()
    }

}