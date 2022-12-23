package com.paperclipengine.physics2d

import com.paperclipengine.scene.Component

abstract class Collider(var isCollidable: Boolean = true) : Component() {}

class CircleCollider(_isCollidable: Boolean = true) : Collider(_isCollidable) {}