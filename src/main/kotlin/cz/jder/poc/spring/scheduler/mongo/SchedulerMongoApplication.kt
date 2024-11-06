package cz.jder.poc.spring.scheduler.mongo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SchedulerMongoApplication

fun main(args: Array<String>) {
    runApplication<SchedulerMongoApplication>(*args)
}