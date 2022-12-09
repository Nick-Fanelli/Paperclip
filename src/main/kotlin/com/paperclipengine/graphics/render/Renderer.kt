package com.paperclipengine.graphics.render

import com.paperclipengine.scene.Scene

abstract class Renderer(protected open val parentScene: Scene) {

    abstract fun create()
    abstract fun begin()
    abstract fun end()
    abstract fun destroy()

}