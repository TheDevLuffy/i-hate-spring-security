package dev.milzipmoza.secutiry.util

import com.fasterxml.jackson.databind.ObjectMapper
import dev.milzipmoza.secutiry.domain.dto.UserDto
import java.io.InputStream

object ObjectMapperUtils {
    private val objectMapper = ObjectMapper()

    fun <T> readValue(src: InputStream, type: Class<T>): T = objectMapper.readValue(src, type)
}