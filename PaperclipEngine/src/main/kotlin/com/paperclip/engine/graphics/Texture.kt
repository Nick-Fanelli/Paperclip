package com.paperclip.engine.graphics

import com.paperclip.engine.asset.Asset
import com.paperclip.engine.utils.FileUtils
import com.paperclip.engine.utils.Logger
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL11.*
import kotlin.reflect.KClass

import org.lwjgl.stb.STBImage.*


class Texture : Asset {

    var textureID = -1
        private set
    var width = 0
        private set
    var height = 0
        private set

    override fun createFromPath(kClass: KClass<*>, path: String): Asset {
        stbi_set_flip_vertically_on_load(true)

        textureID = glGenTextures()
        glBindTexture(GL_TEXTURE_2D, textureID)

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST)

        val width = BufferUtils.createIntBuffer(1)
        val height = BufferUtils.createIntBuffer(1)
        val channels = BufferUtils.createIntBuffer(1)

        val bytes = FileUtils.readResourceFileAsBytes(kClass, path)

        val buffer = BufferUtils.createByteBuffer(bytes.size)
        buffer.put(bytes)
        buffer.flip()

        val image = stbi_load_from_memory(buffer, width, height, channels, 0)

        if(image != null) {
            this.width = width[0]
            this.height = height[0]

            if(channels[0] == 4) { // RGBA
                glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width.get(0), height.get(0),
                    0, GL_RGBA, GL_UNSIGNED_BYTE, image)
            } else if(channels[0] == 3) { // RGB
                glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width.get(0), height.get(0),
                    0, GL_RGB, GL_UNSIGNED_BYTE, image)
            } else {
                Logger.error("Unknown number of channels when loading image \n\tNumber of Channels: ${channels[0]}\n\tAt Path: $path")
            }

            stbi_image_free(image)
        } else {
            Logger.error("An unknown error occurred when loading image \n\tAt Path: $path")
        }

        return this
    }

    override fun onDestroy() {
        glDeleteTextures(textureID)
    }
}