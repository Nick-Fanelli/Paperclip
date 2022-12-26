package com.paperclip.engine.graphics.render

import com.paperclip.engine.scene.Scene

abstract class Renderer(protected open val parentScene: Scene) {

    abstract fun create()
    abstract fun begin()
    abstract fun end()
    abstract fun destroy()

}