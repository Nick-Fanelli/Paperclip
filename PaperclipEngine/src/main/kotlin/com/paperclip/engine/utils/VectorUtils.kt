package com.paperclip.engine.utils

import com.paperclip.engine.math.Vector3f
import org.joml.Quaternionf

object VectorUtils {

    fun rotate(quaternion: Quaternionf, vector3f: Vector3f) : Vector3f {
        return Vector3f(org.joml.Vector3f().rotate(quaternion, org.joml.Vector3f(vector3f.x, vector3f.y, vector3f.z)))
    }

}