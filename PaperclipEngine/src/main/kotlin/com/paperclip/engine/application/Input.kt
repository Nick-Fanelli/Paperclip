package com.paperclip.engine.application

import com.paperclip.engine.math.Vector2f
import org.lwjgl.glfw.GLFW.*

class Input {

    companion object {
        // Taken from glfw.h
        // Keys
        const val KEY_SPACE = 32
        const val KEY_APOSTROPHE = 39 /* ' */
        const val KEY_COMMA = 44 /* , */
        const val KEY_MINUS = 45 /* - */
        const val KEY_PERIOD = 46 /* . */
        const val KEY_SLASH = 47 /* / */
        const val KEY_0 = 48
        const val KEY_1 = 49
        const val KEY_2 = 50
        const val KEY_3 = 51
        const val KEY_4 = 52
        const val KEY_5 = 53
        const val KEY_6 = 54
        const val KEY_7 = 55
        const val KEY_8 = 56
        const val KEY_9 = 57
        const val KEY_SEMICOLON = 59 /* ; */
        const val KEY_EQUAL = 61 /* = */
        const val KEY_A = 65
        const val KEY_B = 66
        const val KEY_C = 67
        const val KEY_D = 68
        const val KEY_E = 69
        const val KEY_F = 70
        const val KEY_G = 71
        const val KEY_H = 72
        const val KEY_I = 73
        const val KEY_J = 74
        const val KEY_K = 75
        const val KEY_L = 76
        const val KEY_M = 77
        const val KEY_N = 78
        const val KEY_O = 79
        const val KEY_P = 80
        const val KEY_Q = 81
        const val KEY_R = 82
        const val KEY_S = 83
        const val KEY_T = 84
        const val KEY_U = 85
        const val KEY_V = 86
        const val KEY_W = 87
        const val KEY_X = 88
        const val KEY_Y = 89
        const val KEY_Z = 90
        const val KEY_LEFT_BRACKET = 91 /* [ */
        const val KEY_BACKSLASH = 92 /* \ */
        const val KEY_RIGHT_BRACKET = 93 /* ] */
        const val KEY_GRAVE_ACCENT = 96 /* ` */
        const val KEY_WORLD_1 = 161 /* non-US #1 */
        const val KEY_WORLD_2 = 162 /* non-US #2 */
        const val KEY_ESCAPE = 256
        const val KEY_ENTER = 257
        const val KEY_TAB = 258
        const val KEY_BACKSPACE = 259
        const val KEY_INSERT = 260
        const val KEY_DELETE = 261
        const val KEY_RIGHT = 262
        const val KEY_LEFT = 263
        const val KEY_DOWN = 264
        const val KEY_UP = 265
        const val KEY_PAGE_UP = 266
        const val KEY_PAGE_DOWN = 267
        const val KEY_HOME = 268
        const val KEY_END = 269
        const val KEY_CAPS_LOCK = 280
        const val KEY_SCROLL_LOCK = 281
        const val KEY_NUM_LOCK = 282
        const val KEY_PRINT_SCREEN = 283
        const val KEY_PAUSE = 284
        const val KEY_F1 = 290
        const val KEY_F2 = 291
        const val KEY_F3 = 292
        const val KEY_F4 = 293
        const val KEY_F5 = 294
        const val KEY_F6 = 295
        const val KEY_F7 = 296
        const val KEY_F8 = 297
        const val KEY_F9 = 298
        const val KEY_F10 = 299
        const val KEY_F11 = 300
        const val KEY_F12 = 301
        const val KEY_F13 = 302
        const val KEY_F14 = 303
        const val KEY_F15 = 304
        const val KEY_F16 = 305
        const val KEY_F17 = 306
        const val KEY_F18 = 307
        const val KEY_F19 = 308
        const val KEY_F20 = 309
        const val KEY_F21 = 310
        const val KEY_F22 = 311
        const val KEY_F23 = 312
        const val KEY_F24 = 313
        const val KEY_F25 = 314
        const val KEY_KP_0 = 320
        const val KEY_KP_1 = 321
        const val KEY_KP_2 = 322
        const val KEY_KP_3 = 323
        const val KEY_KP_4 = 324
        const val KEY_KP_5 = 325
        const val KEY_KP_6 = 326
        const val KEY_KP_7 = 327
        const val KEY_KP_8 = 328
        const val KEY_KP_9 = 329
        const val KEY_KP_DECIMAL = 330
        const val KEY_KP_DIVIDE = 331
        const val KEY_KP_MULTIPLY = 332
        const val KEY_KP_SUBTRACT = 333
        const val KEY_KP_ADD = 334
        const val KEY_KP_ENTER = 335
        const val KEY_KP_EQUAL = 336
        const val KEY_LEFT_SHIFT = 340
        const val KEY_LEFT_CONTROL = 341
        const val KEY_LEFT_ALT = 342
        const val KEY_LEFT_SUPER = 343
        const val KEY_RIGHT_SHIFT = 344
        const val KEY_RIGHT_CONTROL = 345
        const val KEY_RIGHT_ALT = 346
        const val KEY_RIGHT_SUPER = 347
        const val KEY_MENU = 348
        const val MOUSE_BUTTON_1 = 0
        const val MOUSE_BUTTON_2 = 1
        const val MOUSE_BUTTON_3 = 2
        const val MOUSE_BUTTON_4 = 3
        const val MOUSE_BUTTON_5 = 4
        const val MOUSE_BUTTON_6 = 5
        const val MOUSE_BUTTON_7 = 6
        const val MOUSE_BUTTON_8 = 7

        // Mouse Buttons
        const val MOUSE_BUTTON_LEFT = MOUSE_BUTTON_1
        const val MOUSE_BUTTON_RIGHT = MOUSE_BUTTON_2
        const val MOUSE_BUTTON_MIDDLE = MOUSE_BUTTON_3

        private const val NUM_KEYS = 348
        private const val NUM_MOUSE_BUTTONS = 7
    }

    private var keys                = BooleanArray(NUM_KEYS)
    private var keysLast            = BooleanArray(NUM_KEYS)

    private var mouseButtons        = BooleanArray(NUM_MOUSE_BUTTONS)
    private var mouseButtonsLast    = BooleanArray(NUM_MOUSE_BUTTONS)

    var mousePosition       = Vector2f()
        private set
    var deltaMousePosition = Vector2f()
        private set
    private var mousePositionLast   = Vector2f()

    var scrollPosition      = Vector2f()
        private set
    private val absScrollPosition   = Vector2f()

    fun bindCallbacks(windowPtr: Long) {
        glfwSetKeyCallback(windowPtr, ::keyCallback)
        glfwSetMouseButtonCallback(windowPtr, ::mouseButtonCallback)
        glfwSetCursorPosCallback(windowPtr, ::mousePositionCallback)
        glfwSetScrollCallback(windowPtr, ::mouseScrollCallback)
    }

    fun update() {
        keys.copyInto(keysLast)
        mouseButtons.copyInto(mouseButtonsLast)

        deltaMousePosition = mousePosition - mousePositionLast

        mousePositionLast.set(mousePosition)
        scrollPosition.zero()
    }

    private fun keyCallback(window: Long, key: Int, scancode: Int, action: Int, mods: Int) {
        if(key in 0..NUM_KEYS)
            keys[key] = (action == GLFW_PRESS) || (action == GLFW_REPEAT)
    }

    private fun mouseButtonCallback(window: Long, button: Int, action: Int, mods: Int) {
        if (button <= NUM_MOUSE_BUTTONS)
            mouseButtons[button] = (action == GLFW_PRESS)
    }

    private fun mousePositionCallback(window: Long, xPos: Double, yPos: Double) {
        mousePosition.set(xPos.toFloat(), yPos.toFloat())
    }

    private fun mouseScrollCallback(window: Long, xOffset: Double, yOffset: Double) {
        scrollPosition.set(xOffset.toFloat(), yOffset.toFloat())
        absScrollPosition += Vector2f(xOffset.toFloat(), yOffset.toFloat())
    }

    fun isKey(keycode: Int): Boolean {
        return keys[keycode]
    }

    fun isKeyUp(keycode: Int): Boolean {
        return !keys[keycode] && keysLast[keycode]
    }

    fun isKeyDown(keycode: Int): Boolean {
        return keys[keycode] && !keysLast[keycode]
    }

    fun isMouseButton(button: Int): Boolean {
        return mouseButtons[button]
    }

    fun isMouseButtonUp(button: Int): Boolean {
        return !mouseButtons[button] && mouseButtonsLast[button]
    }

    fun isMouseButtonDown(button: Int): Boolean {
        return mouseButtons[button] && !mouseButtonsLast[button]
    }

}