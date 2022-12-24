package com.paperclipengine.math

class Vector2f {

    var x: Float
    var y: Float

    constructor(v: Float = 0.0f) {
        this.x = v
        this.y = v
    }

    constructor(x: Float = 0.0f, y: Float = 0.0f) {
        this.x = x
        this.y = y
    }

    fun zero() {
        this.x = 0.0f
        this.y = 0.0f
    }

    fun set(x: Float, y: Float) {
        this.x = x
        this.y = y
    }

    operator fun plus(value: Vector2f) : Vector2f {
        return Vector2f(this.x + value.x, this.y + value.y)
    }

    operator fun times(value: Vector2f) : Vector2f {
        return Vector2f(this.x * value.x, this.y * value.y)
    }

    operator fun plusAssign(value: Vector2f) {
        this.x += value.x
        this.y += value.y
    }

}