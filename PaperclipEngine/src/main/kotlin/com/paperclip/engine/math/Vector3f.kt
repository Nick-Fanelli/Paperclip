package com.paperclip.engine.math

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

    constructor(vec3f: org.joml.Vector3f) {
        this.x = vec3f.x
        this.y = vec3f.y
        this.z = vec3f.z
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

    operator fun plus(value: Float) = Vector3f(this.x + value, this.y + value, this.z + value)
    operator fun minus(value: Float) = Vector3f(this.x - value, this.y - value, this.z - value)
    operator fun times(value: Float) = Vector3f(this.x * value, this.y * value, this.z * value)
    operator fun div(value: Float) = Vector3f(this.x / value, this.y / value, this.z / value)
    
    operator fun plus(value: Vector3f) = Vector3f(this.x + value.x, this.y + value.y, this.z + value.z)
    operator fun minus(value: Vector3f) = Vector3f(this.x - value.x, this.y - value.y, this.z - value.z)
    operator fun times(value: Vector3f) = Vector3f(this.x * value.x, this.y * value.y, this.z * value.z)
    operator fun div(value: Vector3f) = Vector3f(this.x / value.x, this.y / value.y, this.z / value.z)

}