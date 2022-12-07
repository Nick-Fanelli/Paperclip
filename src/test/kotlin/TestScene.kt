import com.paperclipengine.graphics.Mesh
import com.paperclipengine.graphics.MeshFactory
import com.paperclipengine.scene.Scene
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL30.*

class TestScene : Scene() {

    private val vertices = floatArrayOf(
        -0.5f, -0.5f, 0f,
        0.5f, -0.5f, 0f,
        0f, 0.5f, 0f
    )

    private val indices = intArrayOf(0, 1, 2)

    private lateinit var mesh: Mesh

    override fun onCreate() {
        super.onCreate()

        mesh = MeshFactory.createMesh(vertices, indices)
    }

    override fun onUpdate(deltaTime: Float) {
        super.onUpdate(deltaTime)

        glBindVertexArray(mesh.vaoID)
        glEnableVertexAttribArray(0)
        GL11.glDrawElements(GL_TRIANGLES, mesh.vertexCount, GL_UNSIGNED_INT, 0)
        glDisableVertexAttribArray(0)
        glBindVertexArray(0)
    }

}