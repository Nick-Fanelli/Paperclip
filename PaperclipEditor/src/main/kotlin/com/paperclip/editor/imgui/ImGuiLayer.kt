package com.paperclip.editor.imgui

import com.paperclip.engine.application.Application
import imgui.ImGui
import imgui.ImGuiIO
import imgui.flag.*
import imgui.gl3.ImGuiImplGl3
import imgui.glfw.ImGuiImplGlfw
import org.lwjgl.glfw.GLFW

class ImGuiLayer() {

    private lateinit var imGuiImplGlfw: ImGuiImplGlfw
    private lateinit var imGuiImplGl3: ImGuiImplGl3

    private lateinit var io: ImGuiIO

    private val viewport = Viewport()

    fun onCreate(application: Application) {
        ImGui.createContext()

        io = ImGui.getIO()
        io.addConfigFlags(ImGuiConfigFlags.NavEnableKeyboard)
        io.addConfigFlags(ImGuiConfigFlags.DockingEnable)
        io.addConfigFlags(ImGuiConfigFlags.ViewportsEnable)

        imGuiImplGlfw = ImGuiImplGlfw()
        imGuiImplGlfw.init(application.display.windowPtr, true)

        imGuiImplGl3 = ImGuiImplGl3()
        imGuiImplGl3.init()

        applyImGuiColors()
    }

    fun onUpdate(deltaTime: Float) {
        imGuiImplGlfw.newFrame()
        ImGui.newFrame()

        this.renderDockspace()
        this.onImGuiRender()

        ImGui.render()
        imGuiImplGl3.renderDrawData(ImGui.getDrawData())

        if(io.hasConfigFlags(ImGuiConfigFlags.ViewportsEnable)) {
            val previousContext = GLFW.glfwGetCurrentContext()
            ImGui.updatePlatformWindows()
            ImGui.renderPlatformWindowsDefault()
            GLFW.glfwMakeContextCurrent(previousContext)
        }
    }

    private fun onImGuiRender() {
        viewport.onImGuiRender()
    }

    private val dockingWindowFlags = ImGuiWindowFlags.MenuBar or
            ImGuiWindowFlags.NoDocking or
            ImGuiWindowFlags.NoBackground or
            ImGuiWindowFlags.NoCollapse or
            ImGuiWindowFlags.NoTitleBar or
            ImGuiWindowFlags.NoMove or
            ImGuiWindowFlags.NoNav or
            ImGuiWindowFlags.NoResize or
            ImGuiWindowFlags.NoNavFocus or
            ImGuiWindowFlags.NoDecoration or
            ImGuiWindowFlags.NoInputs

    private val dockingFlags = ImGuiDockNodeFlags.None

    private fun renderDockspace() {
        val viewport = ImGui.getMainViewport()

        ImGui.setNextWindowPos(viewport.posX, viewport.posY)
        ImGui.setNextWindowSize(viewport.sizeX, viewport.sizeY)
        ImGui.setNextWindowViewport(viewport.id)

        ImGui.pushStyleVar(ImGuiStyleVar.WindowPadding, 0.0f, 0.0f)
        ImGui.begin("Dockspace", dockingWindowFlags)
        ImGui.popStyleVar()

        if(io.hasConfigFlags(ImGuiConfigFlags.DockingEnable)) {
            val dockspaceID = ImGui.getID("Dockspace")
            ImGui.dockSpace(dockspaceID, 0.0f, 0.0f, dockingFlags)
        }

        ImGui.end()
    }

    fun onDestroy() {
        imGuiImplGl3.dispose()
        imGuiImplGlfw.dispose()
        ImGui.destroyContext()
    }

}

private val brandAccentColor = floatArrayOf(0.396078f, 0.803921f, 0.992156f, 1.0f)

private fun applyImGuiColors() {
    val style = ImGui.getStyle()

    style.setColor(ImGuiCol.DockingPreview, 0.396078f, 0.803921f, 0.992156f, 1.0f)
    style.setColor(ImGuiCol.DragDropTarget, 0.396078f, 0.803921f, 0.992156f, 1.0f)
    style.setColor(ImGuiCol.NavHighlight, 0.396078f, 0.803921f, 0.992156f, 1.0f)
    style.setColor(ImGuiCol.ResizeGripActive, 0.396078f, 0.803921f, 0.992156f, 1.0f)

    style.setColor(ImGuiCol.WindowBg, 0.1f, 0.105f, 0.11f, 1.0f)

    // Headers
    style.setColor(ImGuiCol.Header, 0.2f, 0.205f, 0.21f, 1.0f)
    style.setColor(ImGuiCol.HeaderHovered, 0.3f, 0.305f, 0.31f, 1.0f)
    style.setColor(ImGuiCol.HeaderActive, 0.15f, 0.1505f, 0.151f, 1.0f)

    // Buttons
    style.setColor(ImGuiCol.Button, 0.2f, 0.205f, 0.21f, 1.0f)
    style.setColor(ImGuiCol.ButtonHovered, 0.3f, 0.305f, 0.31f, 1.0f)
    style.setColor(ImGuiCol.ButtonActive, 0.15f, 0.1505f, 0.151f, 1.0f)

    // Frame BG
    style.setColor(ImGuiCol.FrameBg, 0.2f, 0.205f, 0.21f, 1.0f)
    style.setColor(ImGuiCol.FrameBgHovered, 0.3f, 0.305f, 0.31f, 1.0f)
    style.setColor(ImGuiCol.FrameBgActive, 0.15f, 0.1505f, 0.151f, 1.0f)

    // Tabs
    style.setColor(ImGuiCol.Tab, 0.15f, 0.1505f, 0.151f, 1.0f)
    style.setColor(ImGuiCol.TabHovered, 0.38f, 0.3805f, 0.381f, 1.0f)
    style.setColor(ImGuiCol.TabActive, 0.28f, 0.2805f, 0.281f, 1.0f)
    style.setColor(ImGuiCol.TabUnfocused, 0.15f, 0.1505f, 0.151f, 1.0f)
    style.setColor(ImGuiCol.TabUnfocusedActive, 0.2f, 0.205f, 0.21f, 1.0f)

    // Title
    style.setColor(ImGuiCol.TitleBg, 0.15f, 0.1505f, 0.151f, 1.0f)
    style.setColor(ImGuiCol.TitleBgActive, 0.15f, 0.1505f, 0.151f, 1.0f)
    style.setColor(ImGuiCol.TitleBgCollapsed, 0.15f, 0.1505f, 0.151f, 1.0f)
}