package com.paperclip.engine.utils.json

import com.paperclip.engine.utils.Logger
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.*
import kotlin.reflect.jvm.javaSetter

private const val JSONFieldNULL = "_!NULL!_"

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class JSONField(val name: String = JSONFieldNULL)

abstract class JSONTarget {
    override fun toString(): String {

        val stringBuilder = StringBuilder()

        this.javaClass.declaredFields.forEach {
            stringBuilder.append("${it.name} = ${it.get(this)}")
            stringBuilder.append("\n")
        }

        return stringBuilder.toString()
    }
}

internal object JSONUtils {

    private val jsonParser = JSONParser()

    internal fun parseJson(json: String, target: JSONTarget) : JSONObject {
        val jsonObject = jsonParser.parse(json) as JSONObject

        for(member in target::class.memberProperties) {
            // Contains JSON Field Annotation
            val memberAnnotation = member.findAnnotation<JSONField>()

            if(memberAnnotation != null) {

                val memberName: String =
                    if(memberAnnotation.name != JSONFieldNULL)
                        memberAnnotation.name
                    else
                        member.name

                val jsonValue = jsonObject[memberName]

                if(jsonValue == null) {
                    Logger.error("JSON value of $memberName does not exist\n\tStatus: Not initializing")
                } else {
                    if (jsonObject[memberName] == null) {
                        Logger.warn("JSON object at $memberName is equal to null\n\tStatus: Not initializing")
                    } else {
                        (member as KMutableProperty<*>).setter.call(target, jsonObject[memberName])
                    }

                }

            }

        }

        return jsonObject
    }

}