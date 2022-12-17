import com.paperclipengine.application.SingleWindowOpenGLApplication
import com.paperclipengine.scene.GameScene

fun main() {
    EntryPoint().run()
}

class EntryPoint {

    fun run() {
        val application = SingleWindowOpenGLApplication("Paperclip Engine", ::TestScene)
        application.startApplication()
    }

}