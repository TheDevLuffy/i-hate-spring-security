package dev.milzipmoza.secutiry.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ErrorController {

    @GetMapping("/error/unauthorized")
    fun unAuthorized(): ResponseEntity<String> =
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증 에러")
}