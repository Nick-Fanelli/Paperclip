package com.paperclip.editor

import com.paperclip.engine.application.Application

fun main() {
    Paperclip()
}

class Paperclip {

    init {
        val application = Application("Paperclip", ::EditorScene)
        application.startApplication()
    }

}