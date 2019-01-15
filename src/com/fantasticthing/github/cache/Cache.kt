package com.fantasticthing.github.cache

import com.fantasticthing.github.feature.*
import com.fantasticthing.github.helper.*
import kotlinx.coroutines.*
import java.io.*
import java.util.concurrent.*


/**
 * Created by wanbo on 2019-01-15.
 */
object Cache {

    private const val path = "cache/userinfo"
    private val userProfiles = readUserProfilesFromDisk()

    fun putUserProfile(user: UserProfile.User) {
        userProfiles[user.login] = user.toJson()
        CoroutineScope(Dispatchers.IO).launch {
            with(ByteArrayOutputStream()) {
                ObjectOutputStream(this).writeObject(userProfiles)
                File(path).apply {
                    if (!exists()) {
                        parentFile.mkdirs()
                        createNewFile()
                    }
                    writeBytes(this@with.toByteArray())
                }
            }
        }
    }

    fun getUser(username: String) = userProfiles[username.toLowerCase()]?.toAny<UserProfile.User>()

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