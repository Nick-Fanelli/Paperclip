package com.paperclip.engine.graphics.camera

import com.paperclip.engine.math.Vector3f
import org.joml.Matrix4f

abstract class Camera() {

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