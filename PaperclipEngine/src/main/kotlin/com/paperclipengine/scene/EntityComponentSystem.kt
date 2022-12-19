package com.paperclipengine.scene

import kotlin.reflect.KClass

data class Entity(val entityComponentSystem: EntityComponentSystem, val entityID: Int) {

    fun <ComponentType: Component> addComponent(component: ComponentType) : ComponentType = entityComponentSystem.addComponent(entityID, component)
    inline fun <reified ComponentType: Component> getComponent() : ComponentType? = entityComponentSystem.getComponent(entityID)
    inline fun <reified ComponentType: Component> removeComponent() = entityComponentSystem.removeComponent<ComponentType>(entityID)

    override fun toString(): String {
        return "Entity(entityID=$entityID)"
    }

}

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

    fun <ComponentType: Component> addComponent(entityID: Int, component: ComponentType) : ComponentType {
        if(components[component::class] == null) {
            components[component::class] = HashMap()
        }

        if(components[component::class]!!.containsKey(entityID))
            throw RuntimeException("Only allowed to have one instance")

        components[component::class]?.put(entityID, component)
        return components[component::class]!![entityID] as ComponentType
    }

    inline fun <reified ComponentType: Component> getComponent(entityID: Int) : ComponentType? {
        if(components[ComponentType::class] == null)
            return null

        if(!components[ComponentType::class]!!.containsKey(entityID))
            return null

        return components[ComponentType::class]!![entityID] as ComponentType
    }

    inline fun <reified ComponentType: Component> removeComponent(entityID: Int) {
        if(components[ComponentType::class] == null)
            throw RuntimeException("Invalid Component Type")

        if(!components[ComponentType::class]!!.containsKey(entityID))
            throw RuntimeException("Entity does not contain that component")

        components[ComponentType::class]!!.remove(entityID)
    }

    inline fun <reified ComponentType: Component> getAllComponentsByType() : List<ComponentType> {
        if(components[ComponentType::class] == null)
            return emptyList()

        return components[ComponentType::class]!!.values.toList() as List<ComponentType>
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