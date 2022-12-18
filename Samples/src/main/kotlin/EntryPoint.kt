import com.paperclipengine.application.SingleWindowOpenGLApplication

fun main() {
    EntryPoint().run()
}

class EntryPoint {

    fun run() {
        val application = SingleWindowOpenGLApplication("Paperclip Engine", ::TestScene)
        application.startApplication()
    }

}