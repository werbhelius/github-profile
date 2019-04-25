package com.fantasticthing.github

import com.fantasticthing.github.cache.Cache
import com.fantasticthing.github.exception.*
import com.fantasticthing.github.router.*
import com.fasterxml.jackson.databind.*
import freemarker.cache.*
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.freemarker.*
import io.ktor.jackson.*
import io.ktor.locations.*
import io.ktor.routing.*


@KtorExperimentalLocationsAPI
@Suppress("unused") // auto start by EngineMain in application.conf
fun Application.main() {
    Cache.init()
    install()
}

@KtorExperimentalLocationsAPI
fun Application.install() {
    install(DefaultHeaders)
    install(CallLogging)
    install(Locations)
    install(FreeMarker) {
        templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
    }
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

