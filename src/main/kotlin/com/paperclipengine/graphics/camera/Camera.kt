package com.paperclipengine.graphics.camera

import org.joml.Matrix4f
import org.joml.Vector3f

abstract class Camera() {

    open var position: Vector3f = Vector3f(0.0f)
        set(value) {
            field = value
            calculateViewMatrix()
        }

    open var projectionMatrix = Matrix4f()
        protected set

    open var viewMatrix = Matrix4f()
        protected set

    open var aspectRatio = 16.0f / 9.0f
        set(value) {
            field = value
            calculateProjectionMatrix()
        }

    protected abstract fun calculateProjectionMatrix()
    protected abstract fun calculateViewMatrix()

}