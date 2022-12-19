package com.paperclipengine.scene

import kotlin.reflect.KClass

data class Entity(val entityComponentSystem: EntityComponentSystem, val entityID: Int) {

    fun <T: Component> addComponent(component: T) : T = entityComponentSystem.addComponent(entityID, component)
    inline fun <reified T: Component> getComponent() : T? = entityComponentSystem.getComponent(entityID)
    inline fun <reified T: Component> removeComponent() = entityComponentSystem.removeComponent<T>(entityID)

    override fun toString(): String {
        return "Entity(entityID=$entityID)"
    }

}

data class ComponentPair<T: Component, V: Component>(val first: T, val second: V)

class EntityComponentSystem {

    private var entitiesSize: Int = 0
    private val erasedEntityIDs: ArrayList<Int> = ArrayList()

    val components: HashMap<KClass<*>, HashMap<Int, Component>> = HashMap()

    private fun generateNewEntityID() : Int {
        if(erasedEntityIDs.size > 0) {
            val entityID = erasedEntityIDs[0]
            erasedEntityIDs.removeAt(0)
            return entityID
        }

        entitiesSize++
        return entitiesSize - 1
    }

    fun createEntity() : Entity {
        val entityID = generateNewEntityID()
        return Entity(this, entityID)
    }

    fun destroyEntity(entity: Entity) {
        deleteAllComponentsOfEntity(entity.entityID)
        erasedEntityIDs.add(entity.entityID)
    }

    fun <T: Component> addComponent(entityID: Int, component: T) : T {
        if(components[component::class] == null) {
            components[component::class] = HashMap()
        }

        if(components[component::class]!!.containsKey(entityID))
            throw RuntimeException("Only allowed to have one instance")

        components[component::class]?.put(entityID, component)
        return components[component::class]!![entityID] as T
    }

    inline fun <reified T: Component> getComponent(entityID: Int) : T? {
        if(components[T::class] == null)
            return null

        if(!components[T::class]!!.containsKey(entityID))
            return null

        return components[T::class]!![entityID] as T
    }

    inline fun <reified T: Component> removeComponent(entityID: Int) {
        if(components[T::class] == null)
            throw RuntimeException("Invalid Component Type")

        if(!components[T::class]!!.containsKey(entityID))
            throw RuntimeException("Entity does not contain that component")

        components[T::class]!!.remove(entityID)
    }

    inline fun <reified T: Component> getAllComponentsByType() : List<T> {
        if(components[T::class] == null)
            return emptyList()

        return components[T::class]!!.values.toList() as List<T>
    }

    inline fun <reified T: Component, reified V: Component> forEachComponentByType(secondType: KClass<V>, callback: (ComponentPair<T, V>) -> Unit) {

        if(components[T::class] == null || components[V::class] == null)
            return

        components[T::class]!!.forEach {
            callback(ComponentPair(it.value as T, components[V::class]!![it.key] as V))
        }

    }

    inline fun <reified T: Component, reified V: Component> getAllComponentsByType(secondType: KClass<V>) : ArrayList<ComponentPair<T, V>> {

        val results = ArrayList<ComponentPair<T, V>>()

        forEachComponentByType(secondType) {
            results.add(it)
        }

        return results
    }

    fun getAllComponentsOfEntity(entityID: Int) : ArrayList<Component> {
        val allComponents = ArrayList<Component>()

        components.forEach {
            if(it.value.containsKey(entityID)) {
                allComponents.add(it.value[entityID]!!)
            }
        }

        return allComponents
    }

    private fun deleteAllComponentsOfEntity(entityID: Int) {
        components.forEach {
            it.value.remove(entityID)
        }
    }

}