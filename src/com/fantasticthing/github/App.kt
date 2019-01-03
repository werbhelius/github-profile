package com.fantasticthing.github

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.response.*
import io.ktor.routing.*


@Suppress("unused") // auto start by EngineMain in application.conf
fun Application.main() {
    install(DefaultHeaders)
    install(CallLogging)
    install(Routing) {
        get("/") {
            call.respondText("Hello World~~~~")
        }
    }
}

