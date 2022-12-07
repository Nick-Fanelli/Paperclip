package com.paperclipengine.graphics

import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL15.*
import org.lwjgl.opengl.GL20.glVertexAttribPointer
import org.lwjgl.opengl.GL30.*
import java.nio.FloatBuffer
import java.nio.IntBuffer

internal object MeshFactory {

    private val activeVAOList = ArrayList<Int>()
    private val activeVBOList = ArrayList<Int>()

    private fun createFloatBuffer(data: FloatArray): FloatBuffer {
        val buffer = BufferUtils.createFloatBuffer(data.size)
        buffer.put(data)
        buffer.flip()
        return buffer
    }

    private fun createIntBuffer(data: IntArray): IntBuffer {
        val buffer = BufferUtils.createIntBuffer(data.size)
        buffer.put(data)
        buffer.flip()
        return buffer
    }

    private fun storeDataInArrayBuffer(attribute: Int, dimensions: Int, data: FloatArray) {
        val vbo = glGenBuffers()
        val buffer = createFloatBuffer(data)

        activeVBOList.add(vbo)
        glBindBuffer(GL_ARRAY_BUFFER, vbo)

        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW)
        glVertexAttribPointer(attribute, dimensions, GL_FLOAT, false, 0, 0)
        glBindBuffer(GL_ARRAY_BUFFER, 0)
    }

    private fun bindIndices(data: IntArray) {
        val vbo = glGenBuffers()
        val buffer = createIntBuffer(data)

        activeVBOList.add(vbo)

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vbo)
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW)
    }

    private fun generateVaoID() : Int {
        val vaoID = glGenVertexArrays()
        activeVAOList.add(vaoID)

        glBindVertexArray(vaoID)

        return vaoID
    }

    internal fun createMesh(positions: FloatArray, indices: IntArray) : Mesh {
        val vaoID = generateVaoID()

        storeDataInArrayBuffer(0, 3, positions) // 3 floats per point (x, y, z)
        bindIndices(indices)

        glBindVertexArray(0)
        return Mesh(vaoID, positions.size / 3) // 3 floats per point (x, y, z)
    }

    internal fun cleanUp() {
        for(vbo in activeVBOList)
            glDeleteBuffers(vbo)
        activeVBOList.clear()

        for(vao in activeVAOList)
            glDeleteVertexArrays(vao)
        activeVAOList.clear()
    }

}

data class Mesh(val vaoID: Int, val vertexCount: Int)