package dev.milzipmoza.secutiry.controller.dto

data class EnrollUserRequest(
        val email: String,
        val name: String,
        val password: String
)

data class LoginUserRequest(
        val email: String,
        val password: String
)