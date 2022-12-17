package com.paperclipengine.graphics

import com.paperclipengine.utils.FileUtils
import org.joml.Matrix4f
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL20.*
import org.lwjgl.system.MemoryUtil.NULL

data class ShaderUniformLocation(val uniformLocationID: Int)

class Shader(private val shaderName: String, private val replacements: HashMap<String, String> = HashMap()) {

    private var programID = 0

    private val shaders = ArrayList<Int>()

    fun createShader() {
        programID = glCreateProgram()

        var vertShader: String = FileUtils.readResourceFileAsString("/shaders/$shaderName.vert.glsl")
        var fragShader: String = FileUtils.readResourceFileAsString("/shaders/$shaderName.frag.glsl")

        for(entry in replacements.entries) {
            vertShader = vertShader.replace(entry.key.toRegex(), entry.value)
            fragShader = fragShader.replace(entry.key.toRegex(), entry.value)
        }

        shaders.add(attachShader(GL_VERTEX_SHADER, vertShader))
        shaders.add(attachShader(GL_FRAGMENT_SHADER, fragShader))

        linkProgram()
    }

    private fun attachShader(shaderType: Int, shaderCode: String) : Int {
        val shaderID = glCreateShader(shaderType)

        if(shaderID == NULL.toInt()) {
            throw RuntimeException("Error creating shader. Type: $shaderType")
        }

        glShaderSource(shaderID, shaderCode)
        glCompileShader(shaderID)

        if(glGetShaderi(shaderID, GL_COMPILE_STATUS) == 0) {
            throw RuntimeException("Error compiling shader code: ${glGetShaderInfoLog(shaderID, 1024)}")
        }

        glAttachShader(programID, shaderID)

        return shaderID
    }

    private fun linkProgram() {
        glLinkProgram(programID)

        val comp = glGetProgrami(programID, GL_LINK_STATUS)
        val len = glGetProgrami(programID, GL_INFO_LOG_LENGTH)

        val err = glGetProgramInfoLog(programID, len)

        if(comp == GL_FALSE)
            throw RuntimeException(err)

        for(shader in shaders) {
            glDetachShader(programID, shader)
            glDeleteShader(shader)
        }

        shaders.clear()
    }

    fun bind() {
        glUseProgram(programID)
    }

    fun destroy() {
        glUseProgram(0)
        glDeleteProgram(programID)
    }

    fun getUniformLocation(varName: String) : ShaderUniformLocation {
        val location = glGetUniformLocation(programID, varName)
        return ShaderUniformLocation(location)
    }

    fun addUniformMat4(uniformLocation: ShaderUniformLocation, matrix4f: Matrix4f) {
        var floatBuffer = BufferUtils.createFloatBuffer(16)
        floatBuffer = matrix4f.get(floatBuffer)

        glUniformMatrix4fv(uniformLocation.uniformLocationID, false, floatBuffer)
    }

}