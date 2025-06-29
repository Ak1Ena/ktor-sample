package com.ak1ena.data

import kotlinx.serialization.Serializable

@Serializable
data class Task(val id: Int, val content: String, val isDone: Boolean)
@Serializable
data class TaskRequest(val content: String, val isDone: Boolean)
object TaskRepository {
    private val tasks = mutableListOf<Task>(
        Task(id = 1, content= "Learn Ktor",isDone = false),
        Task(id = 2, content = "Build RESTAPI",isDone = false),
        Task(id = 3, content = "UNIT TEST",isDone = false),
    )

    private var nextId = (tasks.maxOfOrNull { it.id as Int } ?: 0) + 1
    fun getAll(): List<Task> = tasks

    fun getById(id: Int?): Task? = tasks.find { it.id == id }

    fun add(request: TaskRequest): Task {
        val task = Task(nextId++, request.content, request.isDone)
        tasks.add(task)
        println("TEST") // This line can be removed or used for debugging
        return task
    }

    fun update(id: Int?, request: TaskRequest): Boolean {
        val index = tasks.indexOfFirst { it.id == id }
        if (index == -1) return false
        tasks[index] = Task(id!!, request.content, request.isDone)
        return true
    }
    fun delete(id: Int): Boolean {
        return tasks.removeIf { it.id == id }
    }
}