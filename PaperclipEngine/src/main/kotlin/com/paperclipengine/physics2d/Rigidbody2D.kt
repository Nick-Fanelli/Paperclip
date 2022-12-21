package com.paperclipengine.physics2d

import com.paperclipengine.scene.Component
import org.joml.Vector2f
import org.joml.Vector3f

class Rigidbody2D : Component() {

    val velocity = Vector2f()

    var hasGravity = true

}