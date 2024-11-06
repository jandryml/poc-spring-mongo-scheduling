package cz.jdr.poc.spring.scheduler.mongo.domain

import java.time.Instant

class ManualImportAsyncTask(
    val metadataId: String,
    state: String,
    executionStart: Instant,
    executionEnd: Instant,
    expiresOn: Instant,
    id: String? = null
) : AsyncTask(
    id = id,
    state = state,
    executionStart = executionStart,
    executionEnd = executionEnd,
    expiresOn = expiresOn
)
