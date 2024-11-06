package cz.jdr.poc.spring.scheduler.mongo.service

import cz.jdr.poc.spring.scheduler.mongo.domain.AsyncTask
import cz.jdr.poc.spring.scheduler.mongo.domain.ApiImportAsyncTask
import cz.jdr.poc.spring.scheduler.mongo.domain.ManualImportAsyncTask
import cz.jdr.poc.spring.scheduler.mongo.entity.AsyncTaskEntity
import cz.jdr.poc.spring.scheduler.mongo.entity.ActiveImportAsyncTaskEntity
import cz.jdr.poc.spring.scheduler.mongo.entity.ManualImportAsyncTaskEntity
import cz.jdr.poc.spring.scheduler.mongo.repository.TaskRepo
import org.springframework.stereotype.Service

@Service
class TaskService(
    private val taskRepo: TaskRepo
    
) {
    fun getTasks(): List<AsyncTask> {
        return taskRepo.findAll().map { mapToDomain(it) }
    }

    fun insertTask(task1: AsyncTaskEntity) {
        taskRepo.insert(task1)
    }

    fun mapToDomain(taskEntity: AsyncTaskEntity): AsyncTask {
        return when (taskEntity) {
            is ManualImportAsyncTaskEntity -> mapToManualTask(taskEntity)
            is ActiveImportAsyncTaskEntity -> mapToApiTask(taskEntity)
        }
    }

    fun mapToManualTask(taskEntity: ManualImportAsyncTaskEntity): ManualImportAsyncTask {
        return ManualImportAsyncTask(
            metadataId = taskEntity.metadataId,
            state = taskEntity.state,
            executionStart = taskEntity.executionStart,
            executionEnd = taskEntity.executionEnd,
            expiresOn = taskEntity.expiresOn,
            id = taskEntity.id.toString()
        )
    }

    fun mapToApiTask(taskEntity: ActiveImportAsyncTaskEntity): ApiImportAsyncTask {
        return ApiImportAsyncTask(
            state = taskEntity.state,
            executionStart = taskEntity.executionStart,
            executionEnd = taskEntity.executionEnd,
            expiresOn = taskEntity.expiresOn,
            from = taskEntity.from,
            to = taskEntity.to,
            id = taskEntity.id.toString()
        )
    }
}