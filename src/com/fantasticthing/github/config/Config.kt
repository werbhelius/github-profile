package com.fantasticthing.github.config

import com.fantasticthing.github.http.*
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.util.*
import io.ktor.util.pipeline.*

/**
 * Created by wanbo on 2019-01-11.
 */
class Config {

    @KtorExperimentalAPI
    fun configuration(pipeline: Application) {

    }

    companion object Feature : ApplicationFeature<Application, Config, Config> {

        private val phase: PipelinePhase = PipelinePhase("Config")

        override val key: AttributeKey<Config>
            get() = AttributeKey("Config")

        @KtorExperimentalAPI
        override fun install(pipeline: Application, configure: Config.() -> Unit): Config {
            val configuration = Config().apply(configure)
            pipeline.insertPhaseBefore(ApplicationCallPipeline.Setup, Config.phase)
            pipeline.intercept(phase) {
                configuration.configuration(this.application)
            }
            return configuration
        }

    }

}