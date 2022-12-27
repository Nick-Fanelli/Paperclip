package com.paperclip.engine.math

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

    fun set(v: Vector2f) {
        this.x = v.x
        this.y = v.y
    }

    fun set(x: Float, y: Float) {
        this.x = x
        this.y = y
    }

    operator fun plus(value: Vector2f) : Vector2f {
        return Vector2f(this.x + value.x, this.y + value.y)
    }

    operator fun minus(value: Vector2f) : Vector2f {
        return Vector2f(this.x - value.x, this.y - value.y)
    }

    operator fun times(value: Vector2f) : Vector2f {
        return Vector2f(this.x * value.x, this.y * value.y)
    }

    operator fun times(value: Float) : Vector2f {
        return Vector2f(this.x * value, this.y * value)
    }

    operator fun plusAssign(value: Vector2f) {
        this.x += value.x
        this.y += value.y
    }

    override fun toString(): String {
        return "Vector2f($x, $y)"
    }

}