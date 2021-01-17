package dev.milzipmoza.secutiry.domain.dto

data class UserDto(
        val email: String? = null,
        val password: String? = null
) {
    val ofEmail: String
        get() = email!!

    val ofPassword: String
        get() = password!!
}