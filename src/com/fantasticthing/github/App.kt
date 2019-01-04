package com.fantasticthing.github

import com.fantasticthing.github.exception.*
import com.fantasticthing.github.router.*
import com.fasterxml.jackson.databind.*
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.jackson.*
import io.ktor.routing.*


@Suppress("unused") // auto start by EngineMain in application.conf
fun Application.main() {
    install(DefaultHeaders)
    install(CallLogging)
    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }
    install(StatusPages) {
        handlerException()
    }
    install(Routing) {
       api()
    }
}

