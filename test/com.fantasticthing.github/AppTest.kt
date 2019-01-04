package com.fantasticthing.github

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.*

/**
 * Created by wanbo on 2019-01-04.
 */
class AppTest {

    @Test
    fun checkUsername() = withTestApplication(Application::main) {
        with(handleRequest(HttpMethod.Get, "/profile?username=werb")) {
            assertEquals(HttpStatusCode.OK, response.status())
            assertEquals("username is werb", response.content)
        }
        with(handleRequest(HttpMethod.Get, "/profile")) {
            assertEquals(HttpStatusCode.BadRequest, response.status())
            assertEquals("username is werb", response.content)
        }
    }

}