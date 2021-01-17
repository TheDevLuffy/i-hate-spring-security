package dev.milzipmoza.secutiry.controller.dto

data class EnrollUserResponse(
        val email: String,
        val name: String
)

data class LoginUserResponse(
        val email: String,
        val name: String,
        val jwtToken: String
)