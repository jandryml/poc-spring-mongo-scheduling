package cz.jder.poc.spring.scheduler.mongo.controller

import cz.jder.poc.spring.scheduler.mongo.domain.AsyncTask
import cz.jder.poc.spring.scheduler.mongo.domain.ApiImportAsyncTask
import cz.jder.poc.spring.scheduler.mongo.domain.ManualImportAsyncTask
import cz.jder.poc.spring.scheduler.mongo.entity.ActiveImportAsyncTaskEntity
import cz.jder.poc.spring.scheduler.mongo.entity.ManualImportAsyncTaskEntity
import cz.jder.poc.spring.scheduler.mongo.service.TaskService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/")
class TestSchedulerController(
    private val taskService: TaskService
) {

    @GetMapping("/scheduler")
    fun runScheduler(): String {
        return "Scheduler is running"
    }

    @GetMapping("/addEntity")
    fun addEntities() {
        val task1 = ManualImportAsyncTaskEntity("test").apply {
            state = "test"
            executionStart = java.time.Instant.now()
            executionEnd = java.time.Instant.now()
            expiresOn = java.time.Instant.now().plusSeconds(60)
        }

        val task2 = ActiveImportAsyncTaskEntity().apply {
            state = "test2"
            executionStart = java.time.Instant.now()
            executionEnd = java.time.Instant.now()
            from = java.time.Instant.now()
            to = java.time.Instant.now().plusSeconds(1000)
            expiresOn = java.time.Instant.now().plusSeconds(60)
        }
        taskService.insertTask(task1)
        taskService.insertTask(task2)
    }

    @GetMapping("/getEntity")
    fun getEntities(): List<AsyncTask> {
        val result = taskService.getTasks()
        result.forEach { 
            when(it) {
                is ManualImportAsyncTask -> println("ManualImportAsyncTask: ${it.id}")
                is ApiImportAsyncTask -> println("ApiImportAsyncTaskEntity: ${it.id}")
            }
        }
        return result
    }
}