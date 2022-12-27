package com.paperclip.editor

import com.paperclip.editor.imgui.ImGuiLayer
import com.paperclip.engine.application.Input
import com.paperclip.engine.graphics.Transform
import com.paperclip.engine.math.Vector2f
import com.paperclip.engine.math.Vector3f
import com.paperclip.engine.math.Vector4f
import com.paperclip.engine.scene.GameScene
import com.paperclip.engine.scene.QuadRendererComponent
import com.paperclip.engine.scene.TransformComponent

class EditorScene : GameScene() {

    private val imGuiLayer = ImGuiLayer()

    private lateinit var windowSize: Pair<Int, Int>

    override fun onCreate() {
        super.onCreate()

        this.imGuiLayer.onCreate(super.application)

        var x = -10.0f
        while(x < 10.0f) {
            var y = -10.0f
            while(y < 10.0f) {
                val entity = createEntity()
                entity.addComponent(TransformComponent(Transform(Vector3f(x, y, 0.0f), Vector3f(0.25f))))
                entity.addComponent(QuadRendererComponent(Vector4f((x + 10.0f) / 20.0f, 0.2f, (y + 10.0f) / 20.0f, 1.0f)))
                y += 0.25f
            }
            x += 0.25f
        }

        windowSize = this.application.display.getWindowSize()
        this.application.display.addWindowResizeCallback(this::onWindowResize)
    }

    override fun onUpdate(deltaTime: Float) {
        super.onUpdate(deltaTime)

        if(input.isMouseButton(Input.MOUSE_BUTTON_MIDDLE)) {
            val deltaMouse = input.deltaMousePosition

            this.camera.position.x -= deltaMouse.x / (this.windowSize.first / (this.camera.aspectRatio * 2.0f))
            this.camera.position.y += deltaMouse.y / (this.windowSize.second / 2.0f)
        }

        if(input.scrollPosition.y != 0f) {
            camera.zoom -= (deltaTime * input.scrollPosition.y * this.camera.zoom * EditorConfig.editorZoomSpeed)
            if(camera.zoom < 0f) camera.zoom = 0f
        }

        if(input.isKey(Input.KEY_ESCAPE)) {
            application.endApplication()
        }
    }

    override fun onRender() {
        super.onRender()

        this.imGuiLayer.onRender()
    }

    override fun onDestroy() {
        super.onDestroy()

        this.imGuiLayer.onDestroy()
    }

    private fun onWindowResize(width: Int, height: Int) {
        this.windowSize = Pair(width, height)
    }

}