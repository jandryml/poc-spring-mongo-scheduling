package cz.jdr.poc.spring.scheduler.mongo.repository

import cz.jdr.poc.spring.scheduler.mongo.entity.IEntity
import cz.jdr.poc.spring.scheduler.mongo.entity.IOperationsAllowedMongoDbObject
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.FindAndModifyOptions
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import kotlin.reflect.KClass

/**
 * Base class for MongoDB CRUD operations.
 */
sealed class MongoDbOperations<T : IOperationsAllowedMongoDbObject> {
    abstract val mongoTemplate: MongoTemplate

    @Suppress("UNCHECKED_CAST")
    private val classToMap =
        (
            this::class
                .supertypes
                .first()
                .arguments
                .first()
                .type!!
                .classifier as KClass<T>
        ).java

    // Create operations
    // ----------------
    fun insert(entity: T) {
        isClassToMapEntityOrThrow()
        mongoTemplate.insert(entity)
    }

    fun insertAll(entities: Collection<T>) {
        isClassToMapEntityOrThrow()
        mongoTemplate.insert(entities)
    }

    // Be careful with use this method, it will overwrite the entity if it already exists
    fun save(entity: T): T {
        isClassToMapEntityOrThrow()
        mongoTemplate.save<T>(entity)
        return entity
    }

    protected fun upsert(
        query: Query,
        update: Update
    ): Long {
        isClassToMapEntityOrThrow()
        return mongoTemplate.upsert(query, update, classToMap).modifiedCount
    }

    // Read operations
    // ---------------
    fun findById(id: Any): T? = mongoTemplate.findById(id, classToMap)

    fun findAll(): List<T> = mongoTemplate.findAll(classToMap)

    fun totalCount(): Long = count(Query())

    protected fun find(query: Query): List<T> = mongoTemplate.find(query, classToMap)

    protected fun findOne(query: Query): T? = mongoTemplate.findOne(query, classToMap)

    protected fun findAllSorted(sort: Sort): List<T> {
        val query = Query().apply { with(sort) }
        return mongoTemplate.find(query, classToMap)
    }

    protected fun findPage(pageable: Pageable): Page<T> {
        val query = Query().apply { with(pageable) }
        val total: Long = count(query)
        val content: List<T> = find(query)
        return PageImpl(content, pageable, total)
    }

    protected fun count(query: Query): Long = mongoTemplate.count(query, classToMap)

    // Update operations
    // -----------------
    protected fun updateFirst(
        query: Query,
        update: Update
    ): Boolean {
        isClassToMapEntityOrThrow()
        val updateResult = mongoTemplate.updateFirst(query, update, classToMap)
        return updateResult.matchedCount > 0
    }

    protected fun findAndModify(
        query: Query,
        update: Update,
        options: FindAndModifyOptions = FindAndModifyOptions.none()
    ): T? {
        isClassToMapEntityOrThrow()
        return mongoTemplate.findAndModify(query, update, options, classToMap)
    }

    protected fun updateById(
        id: Any,
        update: Update
    ): Boolean {
        isClassToMapEntityOrThrow()
        val query = Query().apply { addCriteria(Criteria.where("_id").`is`(id)) }
        return updateFirst(query, update)
    }

    protected fun updateMulti(
        query: Query,
        update: Update
    ): Long {
        isClassToMapEntityOrThrow()
        return mongoTemplate.updateMulti(query, update, classToMap).modifiedCount
    }

    protected fun updateAll(update: Update) = updateMulti(Query(), update)

    // Delete operations
    // -----------------
    fun removeById(id: Any): Boolean {
        isClassToMapEntityOrThrow()
        val query = Query().apply { addCriteria(Criteria.where("_id").`is`(id)) }
        return remove(query) > 0
    }

    fun removeAll(): Long = remove(Query())

    protected fun remove(query: Query): Long {
        isClassToMapEntityOrThrow()
        return mongoTemplate.remove(query, classToMap).deletedCount
    }

    private fun isClassToMapEntityOrThrow() {
        check(IEntity::class.java.isAssignableFrom(classToMap)) {
            "Operation is allowed only for entities"
        }
    }
}

fun Query.addProjections(vararg fields: String): Query {
    fields.forEach {
        this.fields().include(it)
    }
    return this
}

fun Query.excludeProjections(vararg fields: String): Query {
    fields.forEach {
        this.fields().exclude(it)
    }
    return this
}
