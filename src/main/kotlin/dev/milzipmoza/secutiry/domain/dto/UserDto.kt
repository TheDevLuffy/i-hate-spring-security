package dev.milzipmoza.secutiry.domain.dto

import dev.milzipmoza.secutiry.domain.UserProvider
import javax.persistence.Column

data class UserDto(
        val name: String,
        val password: String
)