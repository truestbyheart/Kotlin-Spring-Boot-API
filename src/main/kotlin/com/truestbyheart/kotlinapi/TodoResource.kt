package com.truestbyheart.kotlinapi

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import java.lang.Exception
import java.time.Instant
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id


interface TodoRespository: JpaRepository<Todo, Long>

@RestController @RequestMapping(value = ["/todo"]) @EnableWebMvc
class TodoResource(val todoRepo: TodoRespository) {
   @GetMapping(value = ["/"])
   fun getAll(): List<Todo> = todoRepo.findAll()

   @GetMapping(value= ["/{id}"])
   fun getOne(@PathVariable id: Long) = todoRepo.findById(id)

   @PostMapping(value = ["/"])
   fun new(@RequestBody text: String) = todoRepo.save(Todo(text = text))

   @DeleteMapping(value = ["/{id}"])
   fun delete(@PathVariable id: Long) = todoRepo.deleteById(id)

   @PutMapping(value =["/{id}"])
   fun update(@PathVariable id: Long, @RequestBody todo: Todo): Todo {
      val toUpdate: Todo = todoRepo.findById(id).orElseThrow { Exception("Server error") }
      toUpdate.text = todo.text
      toUpdate.done = todo.done
      return todoRepo.save(toUpdate)
   }

}

@Entity
class Todo(@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   val Id: Long = 0, var text:String="", var done: Boolean = false, val createdAt: Instant = Instant.now() ) {}