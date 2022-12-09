package com.paperclipengine.graphics

import org.joml.Vector3f

data class Transform(val position: Vector3f = Vector3f(0.0f), val scale: Vector3f = Vector3f(1.0f), val rotation: Float = 0.0f)
