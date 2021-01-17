package dev.milzipmoza.secutiry.controller

import dev.milzipmoza.secutiry.controller.dto.EnrollUserRequest
import dev.milzipmoza.secutiry.controller.dto.EnrollUserResponse
import dev.milzipmoza.secutiry.domain.User
import dev.milzipmoza.secutiry.domain.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
        private val userRepository: UserRepository,
        private val passwordEncoder: BCryptPasswordEncoder
) {

    @PostMapping("/users/enroll")
    fun enroll(@RequestBody request: EnrollUserRequest): ResponseEntity<EnrollUserResponse> {
        val savedUser = userRepository.save(
                User.noProviderOf(
                        email = request.email,
                        name = request.name,
                        password = passwordEncoder.encode(request.password)
                )
        )
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(EnrollUserResponse(
                        email = savedUser.email,
                        name = savedUser.name
                ))
    }

    @GetMapping("/user/findAll")
    fun findAll(): ResponseEntity<List<String>> =
        ResponseEntity.ok(userRepository.findAll()
                .map { it.email }
                .toList())
}