package com.paperclip.engine.ldtk

import com.paperclip.engine.utils.JSONField
import org.json.simple.JSONArray
import org.json.simple.JSONObject

class LDTKWorldJSONTarget {

    @JSONField("__header__")
    lateinit var header: JSONObject

    @JSONField var levels = JSONArray()

}