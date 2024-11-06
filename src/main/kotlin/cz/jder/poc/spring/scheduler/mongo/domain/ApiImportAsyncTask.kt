package cz.jder.poc.spring.scheduler.mongo.domain

import java.time.Instant

class ApiImportAsyncTask(
    state: String,
    executionStart: Instant,
    executionEnd: Instant,
    expiresOn: Instant,
    from: Instant,
    to: Instant,
    id: String? = null
) : BatchAsyncTask(
    id = id,
    state = state,
    executionStart = executionStart,
    executionEnd = executionEnd,
    expiresOn = expiresOn,
    from = from,
    to = to
)
