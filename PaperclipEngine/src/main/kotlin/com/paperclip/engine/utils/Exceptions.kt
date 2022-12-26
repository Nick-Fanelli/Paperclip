package com.paperclip.engine.utils

class PaperclipEngineException(message: String = "") : Exception(message)
class PaperclipEngineFatalException(message: String = "") : RuntimeException(message)