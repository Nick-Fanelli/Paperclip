package com.paperclip.engine.ldtk

import com.paperclip.engine.utils.JSONField
import com.paperclip.engine.utils.JSONTarget
import org.json.simple.JSONArray
import org.json.simple.JSONObject

class LDTKWorldJSONTarget : JSONTarget() {

    @JSONField("__header__")
    lateinit var header: JSONObject

    @JSONField private var levels = JSONArray()

}