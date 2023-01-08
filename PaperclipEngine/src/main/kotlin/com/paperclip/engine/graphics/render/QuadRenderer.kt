package com.paperclip.engine.graphics.render

import com.paperclip.engine.graphics.Shader
import com.paperclip.engine.graphics.ShaderUniformLocation
import com.paperclip.engine.math.Transform
import com.paperclip.engine.graphics.camera.Camera
import com.paperclip.engine.math.Vector2f
import com.paperclip.engine.math.Vector3f
import com.paperclip.engine.math.Vector4f
import com.paperclip.engine.scene.Scene
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL15
import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL30.*
import kotlin.math.cos
import kotlin.math.sin

private const val BATCH_SPRITE_COUNT = 20000
private const val BATCH_VERTEX_COUNT = BATCH_SPRITE_COUNT * 4
private const val BATCH_INDEX_COUNT = BATCH_SPRITE_COUNT * 6

private const val POSITION_FLOAT_COUNT = 3 // x, y, z
private const val POSITION_BYTE_COUNT = POSITION_FLOAT_COUNT * Float.SIZE_BYTES
private const val POSITION_OFFSET_BYTES = 0 // First

private const val COLOR_FLOAT_COUNT = 4 // r, g, b, a
private const val COLOR_BYTE_COUNT = COLOR_FLOAT_COUNT * Float.SIZE_BYTES
private const val COLOR_OFFSET_BYTES = POSITION_OFFSET_BYTES + POSITION_BYTE_COUNT

private const val TEXTURE_ID_FLOAT_COUNT = 1 // id
private const val TEXTURE_ID_BYTE_COUNT = TEXTURE_ID_FLOAT_COUNT * Float.SIZE_BYTES
private const val TEXTURE_ID_OFFSET_BYTES = COLOR_OFFSET_BYTES + COLOR_BYTE_COUNT

private const val TEXTURE_COORD_FLOAT_COUNT = 2 // x, y
private const val TEXTURE_COORD_BYTE_COUNT = TEXTURE_COORD_FLOAT_COUNT * Float.SIZE_BYTES
private const val TEXTURE_COORD_OFFSET_BYTES = TEXTURE_ID_OFFSET_BYTES + TEXTURE_ID_BYTE_COUNT

private const val VERTEX_FLOAT_COUNT = POSITION_FLOAT_COUNT + COLOR_FLOAT_COUNT + TEXTURE_ID_FLOAT_COUNT + TEXTURE_COORD_FLOAT_COUNT
private const val VERTEX_BYTE_COUNT = VERTEX_FLOAT_COUNT * Float.SIZE_BYTES

private val quadVertexPositions = arrayOf(
    Vector2f(-0.5f, -0.5f),
    Vector2f(0.5f, -0.5f),
    Vector2f(0.5f, 0.5f),
    Vector2f(-0.5f, 0.5f)
)

open class QuadRenderer(override val parentScene: Scene, private val camera: Camera) : Renderer(parentScene) {

    private lateinit var shader: Shader
    private lateinit var vertices: FloatArray

    private var vertexPtr = 0

    private var vaoID = 0
    private var vboID = 0
    private var iboID = 0

    private lateinit var uniformProjectionLocation: ShaderUniformLocation
    private lateinit var uniformViewLocation: ShaderUniformLocation

    override fun create() {
        shader = Shader("DefaultShader")
        shader.createShader()

        uniformProjectionLocation = shader.getUniformLocation("uProjection")
        uniformViewLocation = shader.getUniformLocation("uView")

        vertices = FloatArray(BATCH_VERTEX_COUNT * VERTEX_FLOAT_COUNT)

        vaoID = glGenVertexArrays()
        glBindVertexArray(vaoID)

        vboID = glGenBuffers()
        glBindBuffer(GL_ARRAY_BUFFER, vboID)

        glBufferData(GL_ARRAY_BUFFER, (VERTEX_BYTE_COUNT * BATCH_VERTEX_COUNT).toLong(), GL_DYNAMIC_DRAW)

        glVertexAttribPointer(0, POSITION_FLOAT_COUNT, GL_FLOAT, false, VERTEX_BYTE_COUNT, POSITION_OFFSET_BYTES.toLong())
        glVertexAttribPointer(1, COLOR_FLOAT_COUNT, GL_FLOAT, false, VERTEX_BYTE_COUNT, COLOR_OFFSET_BYTES.toLong())
        glVertexAttribPointer(2, TEXTURE_ID_FLOAT_COUNT, GL_FLOAT, false, VERTEX_BYTE_COUNT, TEXTURE_ID_OFFSET_BYTES.toLong())
        glVertexAttribPointer(3, TEXTURE_COORD_FLOAT_COUNT, GL_FLOAT, false, VERTEX_BYTE_COUNT, TEXTURE_COORD_OFFSET_BYTES.toLong())

        glBindBuffer(GL_ARRAY_BUFFER, 0)

        val indices = IntArray(BATCH_INDEX_COUNT)
        var offset = 0

        var i = 0
        while (i < BATCH_INDEX_COUNT) {
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
        glBufferSubData(GL_ARRAY_BUFFER, 0, vertices.take(vertexPtr * VERTEX_FLOAT_COUNT).toFloatArray())
        glBindBuffer(GL_ARRAY_BUFFER, 0)
    }

    private fun render() {
        shader.bind()

        shader.addUniformMat4(uniformProjectionLocation, camera.projectionMatrix)
        shader.addUniformMat4(uniformViewLocation, camera.viewMatrix)

        glBindVertexArray(vaoID)

        glEnableVertexAttribArray(0)
        glEnableVertexAttribArray(1)
        glEnableVertexAttribArray(2)
        glEnableVertexAttribArray(3)

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iboID)

        GL11.glDrawElements(GL_TRIANGLES, (vertexPtr / VERTEX_FLOAT_COUNT) * 6, GL_UNSIGNED_INT, 0) // 4 indices per vertex

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0)

        glDisableVertexAttribArray(0)
        glDisableVertexAttribArray(1)
        glDisableVertexAttribArray(2)
        glDisableVertexAttribArray(3)

        glBindVertexArray(0)

        glUseProgram(0)
    }

    private fun allocateQuad() {
        if(vertexPtr + (VERTEX_FLOAT_COUNT * 4) >= vertices.size) {
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

        // Texture ID
        vertices[vertexPtr + 7] = 0.0f

        // Texture Coord
        vertices[vertexPtr + 8] = 0.0f
        vertices[vertexPtr + 9] = 0.0f

        vertexPtr += VERTEX_FLOAT_COUNT
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
    }

}