package org.example.project

//actual fun getPlatform(): org.example.project.Platform {
//    TODO("Not yet implemented")
//}
// 1. Create a class that implements the common interface
class JvmPlatform : Platform {
    override val name: String = "Desktop (JVM) - ${System.getProperty("os.name")}"
}

// 2. Fulfill the 'expect' promise with the 'actual' keyword
actual fun getPlatform(): Platform = JvmPlatform()