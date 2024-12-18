@file:JvmName("Lwjgl3Launcher")

package Tisawem.GameTesting.Vol1KTXVersion.lwjgl3

import Tisawem.GameTesting.Vol1KTXVersion.Main
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration


/** Launches the desktop (LWJGL3) application. */
fun main() {
    // This handles macOS support and helps on Windows.
    if (StartupHelper.startNewJvmIfRequired())
        return
    try {
        Lwjgl3Application(Main, Lwjgl3ApplicationConfiguration().apply {
            setTitle("GameTestingVol1KTXVersion")
            setWindowedMode(800, 600)
            setWindowIcon(*(arrayOf(128, 64, 32, 16).map { "libgdx$it.png" }.toTypedArray()))
        })
    } catch (e: Throwable) {
        Lwjgl3Application(ErrorScreen(e.message + e.stackTraceToString()), Lwjgl3ApplicationConfiguration().apply {
            setTitle("程序报错")
            setWindowedMode(800, 600)
            setWindowIcon(*(arrayOf(128, 64, 32, 16).map { "libgdx$it.png" }.toTypedArray()))
        })
    }
}
