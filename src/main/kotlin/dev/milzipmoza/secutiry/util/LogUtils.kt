package dev.milzipmoza.secutiry.util

import org.slf4j.LoggerFactory

fun <T> logger(t: T) = LoggerFactory.getLogger(t!!::class.java)