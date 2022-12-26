package com.paperclip.editor

import com.paperclip.engine.application.SingleWindowOpenGLApplication

fun main() {
    Paperclip()
}

class Paperclip {

    init {
        val application = SingleWindowOpenGLApplication("Paperclip", ::EditorScene)
        application.startApplication()
    }

}