package com.fantasticthing.github.cache

import com.fantasticthing.github.feature.*
import com.fantasticthing.github.helper.*
import kotlinx.coroutines.*
import java.io.*
import java.time.*
import java.time.temporal.*
import java.util.concurrent.*


/**
 * Created by wanbo on 2019-01-15.
 */
object Cache {

    private const val path = "cache/userinfo"
    private lateinit var users: ConcurrentHashMap<String, String>

    fun init() {
        users = readUserProfilesFromDisk()
    }

    fun putUser(user: UserProfile.User) {
        users[user.login] = user.toJson()
        writeFile()
    }

    fun getUser(username: String): UserProfile.User? {
        users[username]?.toAny<UserProfile.User>()?.also {
            return if (ChronoUnit.HOURS.between(Instant.ofEpochMilli(it.requestTime), Instant.now()) > 6) {
                null
            } else {
                it
            }
        }
        return null
    }

    @Synchronized
    private fun writeFile() = runBlocking(Dispatchers.IO) {
        with(ByteArrayOutputStream()) {
            ObjectOutputStream(this).writeObject(users)
            File(path).apply {
                if (!exists()) {
                    parentFile.mkdirs()
                    createNewFile()
                }
                writeBytes(this@with.toByteArray())
            }
        }
    }

    private fun readUserProfilesFromDisk(): ConcurrentHashMap<String, String> {
        File(path).apply {
            if (exists()) {
                ObjectInputStream(ByteArrayInputStream(readBytes())).also {
                    val obj = it.readObject()

                    @Suppress("UNCHECKED_CAST")
                    if (obj is ConcurrentHashMap<*, *>)
                        return obj as ConcurrentHashMap<String, String>
                }
            }
        }
        return ConcurrentHashMap()
    }

}