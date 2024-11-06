package cz.jder.poc.spring.scheduler.mongo.service

import cz.jder.poc.spring.scheduler.mongo.entity.AsyncTaskEntity
import cz.jder.poc.spring.scheduler.mongo.repository.TaskRepo
import org.springframework.stereotype.Service

@Service
class TaskService(
    private val taskRepo: TaskRepo
    
) {
    fun getTasks(): List<AsyncTaskEntity> {
        return taskRepo.findAll()
    }

    fun insertTask(task1: AsyncTaskEntity) {
        taskRepo.insert(task1)
    }
}