package com.paperclip.engine.utils

import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.*

private const val JSONFieldNULL = "_!NULL!_"

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class JSONField(val name: String = JSONFieldNULL)

internal object JSONUtils {

    private val jsonParser = JSONParser()

    internal fun parseJson(json: String) = jsonParser.parse(json) as JSONObject

    internal inline fun <reified T: Any> jsonToObject(jsonObject: JSONObject) : T {
        val obj = T::class.createInstance()

        for(member in obj::class.memberProperties) {
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
                        (member as KMutableProperty<*>).setter.call(obj, jsonObject[memberName])
                    }
                }
            }
        }

        return obj
    }

}