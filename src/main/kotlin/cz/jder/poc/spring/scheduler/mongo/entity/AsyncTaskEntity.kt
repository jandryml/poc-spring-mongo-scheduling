package cz.jder.poc.spring.scheduler.mongo.entity

import org.bson.types.ObjectId
import org.springframework.data.annotation.TypeAlias
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document("async_tasks")
sealed class AsyncTaskEntity : Entity<ObjectId>(){
    lateinit var state: String
    lateinit var executionStart: Instant
    lateinit var executionEnd: Instant
    @Indexed(name= "ttlIndex", expireAfterSeconds = 0)
    lateinit var expiresOn: Instant
    var errorMessage: String? = null
}

sealed class BatchAsyncTaskEntity : AsyncTaskEntity() {
    lateinit var from: Instant
    lateinit var to: Instant
}

@TypeAlias("ConseqFundPricesImportAsyncTask")
class ConseqFundPricesImportAsyncTaskEntity : BatchAsyncTaskEntity()

@TypeAlias("ManualBalancesImportAsyncTask")
class ManualBalancesImportAsyncTaskEntity(
    val metadataId: String
) : AsyncTaskEntity()
