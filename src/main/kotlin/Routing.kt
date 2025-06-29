package com.ak1ena

import com.ak1ena.data.TaskRepository
import com.ak1ena.data.TaskRequest
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello, Siraphop Khatchamat")
        }

        route("/tasks") {

            get {
                call.respond(TaskRepository.getAll())
            }

            get("{id}") {
                val id = call.parameters["id"]?.toIntOrNull()
                val task = id?.let { TaskRepository.getById(it) }
                if (task != null) {
                    call.respond(task)
                } else {
                    call.respond(HttpStatusCode.NotFound, "Task not found")
                }
            }

            post {
                try {
                    val request = call.receive<TaskRequest>()
                    val task = TaskRepository.add(request)
                    call.respond(HttpStatusCode.Created, task)
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid Task data: ${e.localizedMessage}")
                }
            }

            put("{id}") {
                val id = call.parameters["id"]?.toIntOrNull()
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid ID")
                    return@put
                }
                try {
                    val request = call.receive<TaskRequest>()
                    val success = TaskRepository.update(id, request)
                    if (success) {
                        call.respond(HttpStatusCode.OK, "Updated successfully")
                    } else {
                        call.respond(HttpStatusCode.NotFound, "Task not found")
                    }
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid Task data for update: ${e.localizedMessage}")
                }
            }

            delete("{id}") {
                val id = call.parameters["id"]?.toIntOrNull()
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid ID")
                    return@delete
                }
                val success = TaskRepository.delete(id)
                if (success) {
                    call.respond(HttpStatusCode.NoContent)
                } else {
                    call.respond(HttpStatusCode.NotFound, "Task not found")
                }
            }
        }
    }
}
