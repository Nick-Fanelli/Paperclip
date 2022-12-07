package com.paperclipengine.application

abstract class SingleWindowApplication(applicationName: String, displayPreferences: DisplayPreferences = DisplayPreferences())
    : Application(applicationName) {

    protected val display = Display(applicationName, displayPreferences)

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