package com.paperclip.engine.graphics.camera

class OrthographicCamera : Camera() {

    var zoom: Float = 1.0f
        set(value) {
            field = value

            calculateViewMatrix()
        }

    init {
        calculateProjectionMatrix()
        calculateViewMatrix()
    }

    override fun calculateProjectionMatrix() {
        projectionMatrix.identity()
        projectionMatrix.ortho2D(-aspectRatio, aspectRatio, -1.0f, 1.0f)
    }

    override fun calculateViewMatrix() {
        viewMatrix.identity()
        viewMatrix.translate(position.x, position.y, position.z).invert()
        viewMatrix.scale(zoom, zoom, zoom)
    }

}