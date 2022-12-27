package com.paperclip.engine.graphics.camera

import com.paperclip.engine.math.Vector3f


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

class OrthographicCamera : Camera() {

    val position: ObservableVector3f = ObservableVector3f(::calculateViewMatrix)

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