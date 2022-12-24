package com.paperclipengine.graphics.camera

import com.paperclipengine.math.Vector3f
import org.joml.Matrix4f

class ObservableVector3f(val observer: () -> Unit, _x: Float = 0.0f, _y: Float = 0.0f, _z: Float = 0.0f) {

    constructor(observer: () -> Unit, value: Float) : this(observer, value, value, value)
    constructor(observer: () -> Unit, vector3f: Vector3f) : this(observer, vector3f.x, vector3f.y, vector3f.z)

    var x: Float = _x
        set(value) {
            field = value
            observer()
        }

    var y: Float = _y
        set(value) {
            field = value
            observer()
        }

    var z: Float = _z
        set(value) {
            field = value
            observer()
        }

    fun set(x: Float, y: Float, z: Float) {
        this.x = x
        this.y = y
        this.z = z
    }

}

abstract class Camera() {

    val position: ObservableVector3f = ObservableVector3f(::calculateViewMatrix)

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