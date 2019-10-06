package com.vngrs.githubchallange

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.facebook.imagepipeline.core.MemoryChunkType

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        initFresco()
    }

    private fun initFresco() {
        val config = ImagePipelineConfig.newBuilder(this)
            .setMemoryChunkType(MemoryChunkType.BUFFER_MEMORY)
            .build()
        Fresco.initialize(this, config)
    }

}