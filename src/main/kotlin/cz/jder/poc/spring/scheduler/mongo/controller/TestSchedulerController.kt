package cz.jder.poc.spring.scheduler.mongo.controller

import cz.jder.poc.spring.scheduler.mongo.domain.AsyncTask
import cz.jder.poc.spring.scheduler.mongo.domain.ConseqFundPricesImportAsyncTask
import cz.jder.poc.spring.scheduler.mongo.domain.ManualBalancesImportAsyncTask
import cz.jder.poc.spring.scheduler.mongo.entity.AsyncTaskEntity
import cz.jder.poc.spring.scheduler.mongo.entity.ConseqFundPricesImportAsyncTaskEntity
import cz.jder.poc.spring.scheduler.mongo.entity.ManualBalancesImportAsyncTaskEntity
import cz.jder.poc.spring.scheduler.mongo.repository.TaskRepo
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
        val task1 = ManualBalancesImportAsyncTaskEntity("test").apply {
            state = "test"
            executionStart = java.time.Instant.now()
            executionEnd = java.time.Instant.now()
            expiresOn = java.time.Instant.now().plusSeconds(60)
        }

        val task2 = ConseqFundPricesImportAsyncTaskEntity().apply {
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
                is ManualBalancesImportAsyncTask -> println("ManualBalancesImportAsyncTaskEntity: ${it.id}")
                is ConseqFundPricesImportAsyncTask -> println("ConseqFundPricesImportAsyncTaskEntity: ${it.id}")
            }
        }
        return result
    }
}