import com.paperclipengine.application.DisplayPreferences
import com.paperclipengine.application.SingleWindowOpenGLApplication

fun main() {
    EntryPoint().run()
}

class EntryPoint {

    fun run() {
        val displayPreferences = DisplayPreferences()
        displayPreferences.isVsyncEnabled = true

        val application = SingleWindowOpenGLApplication("Paperclip Engine", ::TestScene, displayPreferences)
        application.startApplication()
    }

}