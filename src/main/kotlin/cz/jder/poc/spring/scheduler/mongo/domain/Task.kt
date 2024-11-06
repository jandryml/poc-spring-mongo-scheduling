package cz.jder.poc.spring.scheduler.mongo.domain

import java.time.Instant

sealed class AsyncTask(
    val state: String,
    val executionStart: Instant,
    val executionEnd: Instant,
    val expiresOn: Instant,
    val id: String? = null,
    var errorMessage: String? = null
)

sealed class BatchAsyncTask(
    val from: Instant,
    val to: Instant,
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