package com.paperclipengine.scene

import kotlin.reflect.KClass

data class Entity(private val entityComponentSystem: EntityComponentSystem, val entityID: Int) {

    fun <ComponentType: Component> addComponent(component: ComponentType) : ComponentType = entityComponentSystem.addComponent(entityID, component)
    fun <ComponentType: Component> getComponent(componentType: KClass<ComponentType>) : ComponentType? = entityComponentSystem.getComponent(entityID, componentType)
    fun <ComponentType: Component> removeComponent(component: ComponentType) : Boolean = entityComponentSystem.removeComponent(entityID, component)

    override fun toString(): String {
        return "Entity(entityID=$entityID)"
    }

}

class EntityComponentSystem {

    private var entitiesSize: Int = 0
    private val erasedEntityIDs: ArrayList<Int> = ArrayList()

    private val components: HashMap<KClass<*>, HashMap<Int, ArrayList<Component>>> = HashMap()

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
        // TODO: Destroy all components
        erasedEntityIDs.add(entity.entityID)
    }

    fun <ComponentType: Component> addComponent(entityID: Int, component: ComponentType) : ComponentType {
        if(components[component::class] == null) {
            components[component::class] = HashMap()
        }

        if(!component.isMultipleInstancesPerEntityAllowed) {
            if(components[component::class]?.get(entityID) != null) {
                throw RuntimeException("Only allowed to have one instance")
            }

            components[component::class]?.put(entityID, arrayListOf(component))
            return components[component::class]?.get(entityID)?.get(0) as ComponentType
        }

        if(components[component::class]?.get(entityID) == null)
            components[component::class]?.put(entityID, ArrayList())

        val loc = components[component::class]?.get(entityID)?.size ?: throw RuntimeException("???")

        components[component::class]?.get(entityID)?.add(loc, component)
        return components[component::class]?.get(entityID)?.get(loc) as ComponentType
    }

    fun <ComponentType: Component> getComponent(entityID: Int, clazz: KClass<ComponentType>) : ComponentType? {
        if(components[clazz] == null)
            return null

        if(components[clazz]?.get(entityID) == null)
            return null

        if(components[clazz]?.get(entityID)?.size == 0)
            return null

        return components[clazz]?.get(entityID)?.get(0) as ComponentType
    }

    fun <ComponentType: Component> removeComponent(entityID: Int, component: ComponentType) : Boolean {
        if(components[component::class] == null)
            throw RuntimeException("Invalid Component Type")

        if(components[component::class]?.get(entityID) == null)
            throw RuntimeException("Entity does not contain that component")

        return components[component::class]?.get(entityID)?.remove(component) ?: false
    }

}