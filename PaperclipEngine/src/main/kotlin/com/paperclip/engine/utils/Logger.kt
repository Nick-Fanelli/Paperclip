package com.paperclip.engine.utils

import java.time.LocalTime
import java.time.format.DateTimeFormatter

object PaperclipLogger {

    enum class LogLevel {
        TRACE, DEBUG, INFO, WARN, ERROR, DISABLE
    }

    var logLevel: LogLevel = if(BuildConfig.DEBUG) LogLevel.TRACE else LogLevel.WARN

    internal fun shouldLog(l: LogLevel) : Boolean {
        return l.ordinal >= logLevel.ordinal
    }

}

internal object Logger {

    private const val ANSI_RESET = "\u001B[0m"
    private const val ANSI_RED = "\u001B[31m"
    private const val ANSI_GREEN = "\u001B[32m"
    private const val ANSI_YELLOW = "\u001B[33m"
    private const val ANSI_BLUE = "\u001B[34m"

    var logHeaderTimeFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS")

    private fun getLogHeader() : String {
        val time = LocalTime.now().format(logHeaderTimeFormat)
        val thread = Thread.currentThread().name

        return "$time [$thread]"
    }

    internal fun <T> trace(message: T) {
        if(PaperclipLogger.shouldLog(PaperclipLogger.LogLevel.TRACE))
            println("$ANSI_BLUE${getLogHeader()} TRACE - $message$ANSI_RESET")
    }

    internal fun <T> debug(message: T) {
        if(PaperclipLogger.shouldLog(PaperclipLogger.LogLevel.DEBUG))
            println("$ANSI_GREEN${getLogHeader()} DEBUG - $message$ANSI_RESET")
    }

    internal fun <T> info(message: T) {
        if(PaperclipLogger.shouldLog(PaperclipLogger.LogLevel.INFO))
            println("${getLogHeader()} INFO - $message")
    }

    internal fun <T> warn(message: T) {
        if(PaperclipLogger.shouldLog(PaperclipLogger.LogLevel.WARN))
            println("$ANSI_YELLOW${getLogHeader()} WARN - $message$ANSI_RESET")
    }

    internal fun <T> error(message: T) {
        if(PaperclipLogger.shouldLog(PaperclipLogger.LogLevel.ERROR))
            println("$ANSI_RED${getLogHeader()} ERROR - $message$ANSI_RESET")
    }


}