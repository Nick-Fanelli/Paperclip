package com.paperclipengine.scene

import com.paperclipengine.graphics.Transform

abstract class Component()

data class TransformComponent(val transform: Transform = Transform()) : Component()