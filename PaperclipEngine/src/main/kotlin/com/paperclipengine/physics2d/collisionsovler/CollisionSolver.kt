package com.paperclipengine.physics2d.collisionsovler

import com.paperclipengine.physics2d.PhysicsComponentData

interface CollisionSolver {

    fun solveCollisions(entities: ArrayList<PhysicsComponentData>)

}