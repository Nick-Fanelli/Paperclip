package com.paperclip.engine.utils

import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL20.GL_MAX_TEXTURE_IMAGE_UNITS

object RuntimeConfig {

    private var isInitialized = false

    internal fun initialize() {
        if(isInitialized)
            return

        // Initialize Variables

        isInitialized = true
    }

    object OpenGLRuntimeConfig {

        private var isInitialized = false

        var availableGUPTextureSlots = -1
            private set

        internal fun initialize() {
            if(isInitialized)
                return

            // Initialize Variables
            initializeFields()

            isInitialized = true
        }

        private fun initializeFields() {
            queryGPU()
        }

        private fun queryGPU() {
            val intBuffer = BufferUtils.createIntBuffer(1)
            glGetIntegerv(GL_MAX_TEXTURE_IMAGE_UNITS, intBuffer)
            this.availableGUPTextureSlots = intBuffer[0]
        }

    }

}