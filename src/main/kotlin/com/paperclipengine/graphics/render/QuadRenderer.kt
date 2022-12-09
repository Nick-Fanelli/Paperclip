package com.paperclipengine.graphics.render

import com.paperclipengine.graphics.Shader
import com.paperclipengine.graphics.Transform
import org.joml.Vector2f
import org.joml.Vector3f
import org.joml.Vector4f
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL15
import org.lwjgl.opengl.GL30.*
import kotlin.math.cos
import kotlin.math.sin

private const val batchSpriteCount = 20000
private const val batchVertexCount = batchSpriteCount * 4
private const val batchIndexCount = batchSpriteCount * 6

private const val positionFloatCount = 3 // x, y, z
private const val positionByteCount = positionFloatCount * Float.SIZE_BYTES
private const val positionOffsetBytes = 0 // First

private const val colorFloatCount = 4 // r, g, b, a
private const val colorByteCount = colorFloatCount * Float.SIZE_BYTES
private const val colorOffsetBytes = positionOffsetBytes + positionByteCount

private const val vertexFloatCount = positionFloatCount + colorFloatCount
private const val vertexByteCount = vertexFloatCount * Float.SIZE_BYTES

private val quadVertexPositions = arrayOf(
    Vector2f(-0.5f, -0.5f),
    Vector2f(0.5f, -0.5f),
    Vector2f(0.5f, 0.5f),
    Vector2f(-0.5f, 0.5f)
)

class QuadRenderer {

    private lateinit var shader: Shader

    private lateinit var vertices: FloatArray

    private var vertexPtr = 0
    private var indexCount = 0

    private var vaoID = 0
    private var vboID = 0
    private var iboID = 0

    fun create() {
        shader = Shader("DefaultShader")
        shader.createShader()

        vertices = FloatArray(batchVertexCount * vertexFloatCount)

        vaoID = glGenVertexArrays()
        glBindVertexArray(vaoID)

        vboID = glGenBuffers()
        glBindBuffer(GL_ARRAY_BUFFER, vboID)

        glBufferData(GL_ARRAY_BUFFER, (vertexByteCount * batchVertexCount).toLong(), GL_DYNAMIC_DRAW)

        glVertexAttribPointer(0, positionFloatCount, GL_FLOAT, false, vertexByteCount, positionOffsetBytes.toLong())
        glVertexAttribPointer(1, colorFloatCount, GL_FLOAT, false, vertexByteCount, colorOffsetBytes.toLong())

        glBindBuffer(GL_ARRAY_BUFFER, 0)

        val indices = IntArray(batchIndexCount)
        var offset = 0

        var i = 0
        while (i < batchIndexCount) {
            // First Triangle
            indices[i] = offset
            indices[i + 1] = offset + 1
            indices[i + 2] = offset + 2

            // Second Triangle
            indices[i + 3] = offset + 2
            indices[i + 4] = offset + 3
            indices[i + 5] = offset

            offset += 4 // Four Vertices Per Quad

            i += 6
        }

        // Bind the indices
        iboID = glGenBuffers()
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iboID)
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW)

        // Unbind
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0)
        glBindVertexArray(0)
    }

    private fun updateBatchVertexData() {
        glBindBuffer(GL_ARRAY_BUFFER, vboID)
        glBufferSubData(GL_ARRAY_BUFFER, 0, vertices)
        glBindBuffer(GL_ARRAY_BUFFER, 0)
    }

    private fun render() {
        shader.bind()

        glBindVertexArray(vaoID)

        glEnableVertexAttribArray(0)
        glEnableVertexAttribArray(1)

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iboID)

        GL11.glDrawElements(GL_TRIANGLES, indexCount, GL_UNSIGNED_INT, 0)

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0)

        glDisableVertexAttribArray(0)
        glDisableVertexAttribArray(1)

        glBindVertexArray(0)

        glUseProgram(0)
    }

    private fun allocateQuad() {
        if(vertexPtr + (vertexFloatCount * 4) >= vertices.size) { // TODO: Test by setting quad count to 1
            end()
            begin()
        }
    }

    fun begin() {
        vertexPtr = 0
    }

    fun end() {
        updateBatchVertexData()
        render()
    }

    fun destroy() {
        shader.destroy()

        glDeleteVertexArrays(vaoID)
        GL15.glDeleteBuffers(vboID)
        GL15.glDeleteBuffers(iboID)
    }

    private fun addVertex(position: Vector3f, color: Vector4f) {
        // Position
        vertices[vertexPtr + 0] = position.x
        vertices[vertexPtr + 1] = position.y
        vertices[vertexPtr + 2] = position.z

        // Color
        vertices[vertexPtr + 3] = color.x
        vertices[vertexPtr + 4] = color.y
        vertices[vertexPtr + 5] = color.z
        vertices[vertexPtr + 6] = color.w

        vertexPtr += vertexFloatCount
    }

    fun drawQuad(transform: Transform, color: Vector4f = Vector4f(1.0f)) {
        allocateQuad()

        for(i in 0..3) {
            var xPos = transform.position.x + (quadVertexPositions[i].x * transform.scale.x)
            var yPos = transform.position.y + (quadVertexPositions[i].y * transform.scale.y)

            if(transform.rotation != 0.0f) {
                val radAngle: Float = Math.toRadians(transform.rotation.toDouble()).toFloat()

                val rx = (transform.position.x + (xPos - transform.position.x) * cos(radAngle.toDouble()) - (yPos - transform.position.y) * sin(radAngle.toDouble())).toFloat()
                val ry = (transform.position.y + (xPos - transform.position.x) * sin(radAngle.toDouble()) + (yPos - transform.position.y) * cos(radAngle.toDouble())).toFloat()

                xPos = rx
                yPos = ry
            }

            addVertex(Vector3f(xPos, yPos, transform.position.z), color)
        }

        indexCount += 6 // 6 indices per quad
    }

}