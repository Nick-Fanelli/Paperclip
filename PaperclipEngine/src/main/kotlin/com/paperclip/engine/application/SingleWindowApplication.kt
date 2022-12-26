package com.paperclip.engine.application

abstract class SingleWindowApplication(applicationName: String, displayPreferences: DisplayPreferences = DisplayPreferences())
    : com.paperclip.engine.application.Application(applicationName) {

    val display = Display(applicationName, displayPreferences)

    init {
        display.createDisplay(::onUpdate)
        DisplayStack.pushDisplay(display)
    }

    override fun startApplication() {
        // Start the game loop
        DisplayStack.iterateStackUntilEmpty()

        // Clean Up After Game Loop is Complete
        onDestroy()
    }

    open fun onDestroy() {
        display.cleanUp()
    }

}