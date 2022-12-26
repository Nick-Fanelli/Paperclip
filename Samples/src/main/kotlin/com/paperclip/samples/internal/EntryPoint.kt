package com.paperclip.samples.internal

import com.paperclip.engine.application.DisplayPreferences
import com.paperclip.engine.application.SingleWindowOpenGLApplication

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