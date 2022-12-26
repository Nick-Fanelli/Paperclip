package com.paperclip.samples.internal

import com.paperclip.engine.application.Application
import com.paperclip.engine.application.DisplayPreferences

fun main() {
    EntryPoint().run()
}

class EntryPoint {

    fun run() {
        val application = Application("Paperclip Engine", ::TestScene)
        application.startApplication()
    }

}