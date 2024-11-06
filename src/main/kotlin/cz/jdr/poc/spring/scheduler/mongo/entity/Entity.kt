package cz.jdr.poc.spring.scheduler.mongo.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Field
import java.io.Serializable

sealed interface IMongoDbObject : Serializable

sealed interface IOperationsAllowedMongoDbObject : IMongoDbObject

sealed interface IEntity : IOperationsAllowedMongoDbObject

interface IEmbeddable : IMongoDbObject

interface IProjection : IOperationsAllowedMongoDbObject

open class Entity<ID : Serializable>(
    @Id
    @Field(FLD_ID)
    var id: ID? = null
) : IEntity {
    companion object {
        const val FLD_ID: String = "_id"
    }
}
