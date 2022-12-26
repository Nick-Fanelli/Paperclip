package com.paperclip.engine.graphics

import com.paperclip.engine.math.Vector3f

data class Transform(val position: Vector3f = Vector3f(0.0f), val scale: Vector3f = Vector3f(1.0f), var rotation: Float = 0.0f)
