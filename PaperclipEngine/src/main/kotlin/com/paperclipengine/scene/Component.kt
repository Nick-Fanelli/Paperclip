package com.paperclipengine.scene

import com.paperclipengine.graphics.Transform
import org.joml.Vector2f
import org.joml.Vector4f

abstract class Component()

data class TransformComponent(val transform: Transform = Transform()) : Component()

data class QuadRendererComponent(val color: Vector4f = Vector4f(1.0f)) : Component()