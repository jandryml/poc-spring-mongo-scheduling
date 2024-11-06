package cz.jder.poc.spring.scheduler.mongo.service

import cz.jder.poc.spring.scheduler.mongo.domain.AsyncTask
import cz.jder.poc.spring.scheduler.mongo.domain.ConseqFundPricesImportAsyncTask
import cz.jder.poc.spring.scheduler.mongo.domain.ManualBalancesImportAsyncTask
import cz.jder.poc.spring.scheduler.mongo.entity.AsyncTaskEntity
import cz.jder.poc.spring.scheduler.mongo.entity.ConseqFundPricesImportAsyncTaskEntity
import cz.jder.poc.spring.scheduler.mongo.entity.ManualBalancesImportAsyncTaskEntity
import cz.jder.poc.spring.scheduler.mongo.repository.TaskRepo
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
            is ManualBalancesImportAsyncTaskEntity -> mapToManualTask(taskEntity)
            is ConseqFundPricesImportAsyncTaskEntity -> mapToConseqTask(taskEntity)
        }
    }

    fun mapToManualTask(taskEntity: ManualBalancesImportAsyncTaskEntity): ManualBalancesImportAsyncTask {
        return ManualBalancesImportAsyncTask(
            metadataId = taskEntity.metadataId,
            state = taskEntity.state,
            executionStart = taskEntity.executionStart,
            executionEnd = taskEntity.executionEnd,
            expiresOn = taskEntity.expiresOn,
            id = taskEntity.id.toString()
        )
    }

    fun mapToConseqTask(taskEntity: ConseqFundPricesImportAsyncTaskEntity): ConseqFundPricesImportAsyncTask {
        return ConseqFundPricesImportAsyncTask(
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