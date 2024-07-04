package tech.upref

import io.ktor.http.*
import tech.upref.plugins.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.application.*
import io.ktor.server.plugins.cors.*
import tech.upref.plugins.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    install(CORS) {
        anyHost()
        allowSameOrigin = true
        allowHost("0.0.0.0:8000")
        allowHeader(HttpHeaders.ContentType)
    }
    configureSecurity()
    configureSerialization()
    configureDatabases()
    configureRouting()
}
