package com.paperclipengine.math

class Vector4f {

    var x: Float
    var y: Float
    var z: Float
    var w: Float

    constructor(v: Float = 0.0f) {
        this.x = v
        this.y = v
        this.z = v
        this.w = v
    }

    constructor(x: Float = 0.0f, y: Float = 0.0f, z: Float = 0.0f, w: Float = 0.0f) {
        this.x = x
        this.y = y
        this.z = z
        this.w = w
    }

    fun zero() {
        this.x = 0.0f
        this.y = 0.0f
        this.z = 0.0f
        this.w = 0.0f
    }

    fun set(x: Float, y: Float, z: Float, w: Float) {
        this.x = x
        this.y = y
        this.z = z
        this.w = w
    }

}