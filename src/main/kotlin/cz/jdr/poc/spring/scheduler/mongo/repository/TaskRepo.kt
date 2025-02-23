package cz.jdr.poc.spring.scheduler.mongo.repository

import cz.jdr.poc.spring.scheduler.mongo.entity.AsyncTaskEntity
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Component

@Component
class TaskRepo(override val mongoTemplate: MongoTemplate): MongoDbOperations<AsyncTaskEntity>() {

}
