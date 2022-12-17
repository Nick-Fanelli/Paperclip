package com.paperclipengine.scene

import com.paperclipengine.application.Input
import com.paperclipengine.graphics.Transform
import com.paperclipengine.graphics.camera.OrthographicCamera
import com.paperclipengine.graphics.render.QuadRenderer
import org.joml.Vector3f
import org.joml.Vector4f

open class GameScene : Scene() {

    protected lateinit var camera: OrthographicCamera

    private lateinit var quadRenderer: QuadRenderer

    override fun onCreate() {
        super.onCreate()

        camera = OrthographicCamera()

        quadRenderer = QuadRenderer(this, camera)
        quadRenderer.create()
    }

    protected fun onRender() {
        quadRenderer.begin()

        quadRenderer.drawQuad(Transform(
            Vector3f(0.0f, 0.0f, 0.0f),
        ), Vector4f(1.0f)
        )

        quadRenderer.end()
    }

    override fun onDestroy() {
        super.onDestroy()

        quadRenderer.destroy()
    }

    override fun onWindowResize(aspectRatio: Float) {
        camera.aspectRatio = aspectRatio
    }

}