package com.paperclip.engine.application

import com.paperclip.engine.utils.Logger
import com.paperclip.engine.utils.PaperclipEngineFatalException
import org.lwjgl.glfw.Callbacks.glfwFreeCallbacks
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.glfw.GLFWWindowSizeCallback
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GLCapabilities
import org.lwjgl.system.MemoryStack.stackPush
import org.lwjgl.system.MemoryUtil.NULL
import java.awt.Dimension

private val DISPLAYS = ArrayList<Display>()

private fun initializeGLFW() {
    // Setup an error callback. The default implementation
    // will print the error message in System.err.
    // Setup an error callback. The default implementation
    // will print the error message in System.err.
    Logger.info("Initializing GLFW")
    GLFWErrorCallback.createPrint(System.err).set()

    // Initialize GLFW. Most GLFW functions will not work before doing this.
    check(glfwInit()) { "Unable to initialize GLFW" }

}

private fun disposeGLFW() {
    Logger.info("Disposing GLFW")
    glfwTerminate()
    glfwSetErrorCallback(null)?.free()
}

class DisplayPreferences {

    var displayName: String? = null
    var displaySize = Dimension(1280, 720)
    var isVsyncEnabled = true

}

class Display(private val windowTitle: String, private var displayPreferences: DisplayPreferences) {

    var windowPtr: Long = 0
        private set

    var fps = 0
        private set

    var aspectRatio = 0.0f
        private set

    private lateinit var updateCallback: (_: Float) -> Any

    var windowResizeCallback: ((_: Float) -> Any)? = null

    private lateinit var glCapabilities: GLCapabilities

    private var endTime = 0.0f
    private var startTime = 0.0f
    private var deltaTime = 0.0f
    private var accumulatedDeltaTime = 0.0f

    private var frameCount = 0

    fun createDisplay(updateCallback: (_: Float) -> Any) {
        if(DISPLAYS.size <= 0)
            initializeGLFW()
        DISPLAYS.add(this)

        this.updateCallback = updateCallback

        // Configure GLFW
        glfwDefaultWindowHints() // optional, the current window hints are already the default

        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE) // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE) // the window will be resizable

        // Create the window
        windowPtr = glfwCreateWindow(displayPreferences.displaySize.width, displayPreferences.displaySize.height,
            if(displayPreferences.displayName == null) windowTitle else displayPreferences.displayName!! , NULL, NULL)
        if (windowPtr == NULL) throw PaperclipEngineFatalException("Failed to create the GLFW window")

        aspectRatio = displayPreferences.displaySize.width.toFloat() / displayPreferences.displaySize.height.toFloat()

        // Get the thread stack and push a new frame
        stackPush().use { stack ->
            val pWidth = stack.mallocInt(1) // int*
            val pHeight = stack.mallocInt(1) // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(windowPtr, pWidth, pHeight)

            // Get the resolution of the primary monitor
            val vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor())

            // Center the window
            glfwSetWindowPos(
                windowPtr,
                (vidMode!!.width() - pWidth[0]) / 2,
                (vidMode.height() - pHeight[0]) / 2
            )
        }

        // Make the OpenGL context current
        glfwMakeContextCurrent(windowPtr)

        // Enable v-sync
        glfwSwapInterval(if(displayPreferences.isVsyncEnabled) 1 else 0)

        glfwSetWindowSizeCallback(windowPtr, object : GLFWWindowSizeCallback() {
            override operator fun invoke(window: Long, width: Int, height: Int) {
                aspectRatio = width.toFloat() / height.toFloat()
                glViewport(0, 0, width, height)

                windowResizeCallback?.let { it(aspectRatio) }

            }
        })

        // Make the window visible
        glfwShowWindow(windowPtr)

        this.glCapabilities = GL.createCapabilities()

        glEnable(GL_BLEND)
        glBlendFunc(GL_SRC_ALPHA,GL_ONE_MINUS_SRC_ALPHA)
        glCullFace(GL_BACK)

        Logger.info("Created Display")
    }

    fun update() {
        GL.setCapabilities(this.glCapabilities)

        glClearColor(0.2f, 0.2f, 0.2f, 1.0f)
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)

        if(deltaTime >= 0) {
            updateCallback(deltaTime)
        }

        glfwSwapBuffers(windowPtr)
        glfwPollEvents()

        endTime = glfwGetTime().toFloat()
        deltaTime = endTime - startTime
        startTime = endTime

        accumulatedDeltaTime += deltaTime

        if(accumulatedDeltaTime > 1.0f) {
            fps = frameCount
            frameCount = 0
            accumulatedDeltaTime = 0.0f
        }

        frameCount++
    }

    fun continuouslyUpdateDisplayUntilCloseRequested() {
        while(!glfwWindowShouldClose(this.windowPtr))
            this.update()
    }

    fun closeDisplay() {
        glfwSetWindowShouldClose(windowPtr, true)
        Logger.info("Set window to close")
    }

    fun cleanUp() {
        glfwFreeCallbacks(windowPtr)
        glfwDestroyWindow(windowPtr)

        DISPLAYS.remove(this)
        if(DISPLAYS.size <= 0)
            disposeGLFW()
    }

}