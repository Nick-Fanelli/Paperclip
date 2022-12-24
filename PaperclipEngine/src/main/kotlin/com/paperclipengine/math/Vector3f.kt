package com.paperclipengine.math

class Vector3f {

    var x: Float
    var y: Float
    var z: Float

    constructor(v: Float = 0.0f) {
        this.x = v
        this.y = v
        this.z = v
    }

    constructor(vec2f: Vector2f, z: Float) {
        this.x = vec2f.x
        this.y = vec2f.y
        this.z = z
    }

    constructor(x: Float = 0.0f, y: Float = 0.0f, z: Float = 0.0f) {
        this.x = x
        this.y = y
        this.z = z
    }

    fun zero() {
        this.x = 0.0f
        this.y = 0.0f
        this.z = 0.0f
    }

    fun set(x: Float, y: Float, z: Float) {
        this.x = x
        this.y = y
        this.z = z
    }

    fun toVector2f() : Vector2f {
        return Vector2f(this.x, this.y)
    }

}