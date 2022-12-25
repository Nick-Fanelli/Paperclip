package com.paperclipengine.graphics.render

import com.paperclipengine.graphics.Shader
import com.paperclipengine.graphics.ShaderUniformLocation
import com.paperclipengine.graphics.Transform
import com.paperclipengine.graphics.camera.Camera
import com.paperclipengine.math.Vector2f
import com.paperclipengine.math.Vector3f
import com.paperclipengine.math.Vector4f
import com.paperclipengine.scene.Scene
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL15
import org.lwjgl.opengl.GL30
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

private const val uvCoordsFloatCount = 2
private const val uvCoordsByteCount = uvCoordsFloatCount * Float.SIZE_BYTES
private const val uvCoordsOffsetBytes = colorOffsetBytes + colorByteCount

private const val vertexFloatCount = positionFloatCount + colorFloatCount + uvCoordsFloatCount
private const val vertexByteCount = vertexFloatCount * Float.SIZE_BYTES

private val quadVertexPositions = arrayOf(
    Vector2f(-0.5f, -0.5f),
    Vector2f(0.5f, -0.5f),
    Vector2f(0.5f, 0.5f),
    Vector2f(-0.5f, 0.5f)
)

private val uvCoords = arrayOf(
    Vector2f(-1f, -1f),
    Vector2f(1f, -1f),
    Vector2f(1f, 1f),
    Vector2f(-1f, 1f)
)

class CircleRenderer(override val parentScene: Scene, private val camera: Camera) : Renderer(parentScene) {

    private lateinit var shader: Shader
    private lateinit var vertices: FloatArray

    private var vertexPtr = 0
    private var indexCount = 0

    private var vaoID = 0
    private var vboID = 0
    private var iboID = 0

    private lateinit var uniformProjectionLocation: ShaderUniformLocation
    private lateinit var uniformViewLocation: ShaderUniformLocation

    override fun create() {
        shader = Shader("CircleShader")
        shader.createShader()

        uniformProjectionLocation = shader.getUniformLocation("uProjection")
        uniformViewLocation = shader.getUniformLocation("uView")

        vertices = FloatArray(batchVertexCount * vertexFloatCount)

        vaoID = GL30.glGenVertexArrays()
        GL30.glBindVertexArray(vaoID)

        vboID = GL30.glGenBuffers()
        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, vboID)

        GL30.glBufferData(GL30.GL_ARRAY_BUFFER, (vertexByteCount * batchVertexCount).toLong(), GL30.GL_DYNAMIC_DRAW)

        GL30.glVertexAttribPointer(
            0,
            positionFloatCount,
            GL30.GL_FLOAT,
            false,
            vertexByteCount,
            positionOffsetBytes.toLong()
        )
        GL30.glVertexAttribPointer(1, colorFloatCount, GL30.GL_FLOAT, false, vertexByteCount, colorOffsetBytes.toLong())
        GL30.glVertexAttribPointer(2, uvCoordsFloatCount, GL30.GL_FLOAT, false, vertexByteCount, uvCoordsOffsetBytes.toLong())

        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, 0)

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
        iboID = GL30.glGenBuffers()
        GL30.glBindBuffer(GL30.GL_ELEMENT_ARRAY_BUFFER, iboID)
        GL30.glBufferData(GL30.GL_ELEMENT_ARRAY_BUFFER, indices, GL30.GL_STATIC_DRAW)

        // Unbind
        GL30.glBindBuffer(GL30.GL_ELEMENT_ARRAY_BUFFER, 0)
        GL30.glBindVertexArray(0)
    }

    private fun updateBatchVertexData() {
        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, vboID)
        GL30.glBufferSubData(GL30.GL_ARRAY_BUFFER, 0, vertices)
        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, 0)
    }

    private fun render() {
        shader.bind()

        shader.addUniformMat4(uniformProjectionLocation, camera.projectionMatrix)
        shader.addUniformMat4(uniformViewLocation, camera.viewMatrix)

        GL30.glBindVertexArray(vaoID)

        GL30.glEnableVertexAttribArray(0)
        GL30.glEnableVertexAttribArray(1)
        GL30.glEnableVertexAttribArray(2)

        GL30.glBindBuffer(GL30.GL_ELEMENT_ARRAY_BUFFER, iboID)

        GL11.glDrawElements(GL30.GL_TRIANGLES, indexCount, GL30.GL_UNSIGNED_INT, 0)

        GL30.glBindBuffer(GL30.GL_ELEMENT_ARRAY_BUFFER, 0)

        GL30.glDisableVertexAttribArray(0)
        GL30.glDisableVertexAttribArray(1)
        GL30.glDisableVertexAttribArray(2)

        GL30.glBindVertexArray(0)

        GL30.glUseProgram(0)
    }

    private fun allocateQuad() {
        if(vertexPtr + (vertexFloatCount * 4) >= vertices.size) {
            end()
            begin()
        }
    }

    override fun begin() {
        vertexPtr = 0
    }

    override fun end() {
        updateBatchVertexData()
        render()
    }

    override fun destroy() {
        shader.destroy()

        GL30.glDeleteVertexArrays(vaoID)
        GL15.glDeleteBuffers(vboID)
        GL15.glDeleteBuffers(iboID)
    }

    private fun addVertex(position: Vector3f, color: Vector4f, uvCoords: Vector2f) {
        // Position
        vertices[vertexPtr + 0] = position.x
        vertices[vertexPtr + 1] = position.y
        vertices[vertexPtr + 2] = position.z

        // Color
        vertices[vertexPtr + 3] = color.x
        vertices[vertexPtr + 4] = color.y
        vertices[vertexPtr + 5] = color.z
        vertices[vertexPtr + 6] = color.w

        // UV Coords
        vertices[vertexPtr + 7] = uvCoords.x
        vertices[vertexPtr + 8] = uvCoords.y

        vertexPtr += vertexFloatCount
    }

    fun drawCircle(transform: Transform, color: Vector4f = Vector4f(1.0f)) {
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

            addVertex(Vector3f(xPos, yPos, transform.position.z), color, uvCoords[i])
        }

        indexCount += 6 // 6 indices per quad
    }


}