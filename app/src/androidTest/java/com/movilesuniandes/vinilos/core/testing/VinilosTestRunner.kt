package com.movilesuniandes.vinilos.core.testing

import androidx.test.runner.AndroidJUnitRunner
import java.io.File

class VinilosTestRunner : AndroidJUnitRunner() {

    override fun onStart() {
        runCatching {
            val mediaDir = targetContext.externalMediaDirs.firstOrNull() ?: return@runCatching
            File(mediaDir, "additional_test_output").mkdirs()
        }
        super.onStart()
    }
}
