package com.paperclip.editor

import com.paperclip.editor.imgui.ImGuiLayer
import com.paperclip.engine.graphics.Transform
import com.paperclip.engine.graphics.render.QuadRenderer
import com.paperclip.engine.math.Vector3f
import com.paperclip.engine.scene.GameScene
import com.paperclip.engine.scene.QuadRendererComponent
import com.paperclip.engine.scene.Scene
import com.paperclip.engine.scene.TransformComponent

class EditorScene : GameScene() {

    private val imGuiLayer = ImGuiLayer()

    override fun onCreate() {
        super.onCreate()

        this.imGuiLayer.onCreate(super.application)

        val entity = createEntity()
        entity.addComponent(TransformComponent(Transform(Vector3f(0.0f), Vector3f(1f))))
        entity.addComponent(QuadRendererComponent())
    }

    override fun onUpdate(deltaTime: Float) {
        super.onUpdate(deltaTime)

        this.imGuiLayer.onUpdate(deltaTime)

        super.onRender()
    }

    override fun onDestroy() {
        super.onDestroy()

        this.imGuiLayer.onDestroy()
    }

}