package com.paperclip.editor

import com.paperclip.engine.application.Application

fun main() {
    Paperclip()
}

class Paperclip {

    init {
        // TODO: Load the Editor Config Data into EditorConfig.kt

        val application = Application("Paperclip", ::EditorScene)
        application.startApplication()
    }

}