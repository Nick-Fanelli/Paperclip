package com.paperclipengine.scene

import com.paperclipengine.graphics.Transform
import kotlin.reflect.KClass

abstract class Component(val isMultipleInstancesPerEntityAllowed: Boolean)

data class TransformComponent(val transform: Transform = Transform()) : Component(false)