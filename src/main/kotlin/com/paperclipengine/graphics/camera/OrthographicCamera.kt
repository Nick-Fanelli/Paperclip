package com.paperclipengine.graphics.camera

import org.joml.Matrix4f
import org.joml.Vector3f

class OrthographicCamera : Camera() {

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
        viewMatrix.translate(position).invert()
    }

}