package com.paperclip.engine.scene

import com.paperclip.engine.graphics.Transform
import com.paperclip.engine.math.Vector4f

abstract class Component() {
    var isActive = true

    open fun onAttach(ecs: EntityComponentSystem, entityID: Int) {}
    open fun onDetach(ecs: EntityComponentSystem, entityID: Int) {}
}

data class TransformComponent(val transform: Transform = Transform()) : Component()

data class QuadRendererComponent(val color: Vector4f = Vector4f(1.0f)) : Component()

data class CircleRendererComponent(val color: Vector4f = Vector4f(1.0f)) : Component()