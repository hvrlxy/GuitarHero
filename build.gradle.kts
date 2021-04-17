plugins {
    id("org.jetbrains.kotlin.jvm").version("1.3.41")
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.processing:core:3.3.6")
    implementation("org.jogamp.jogl:jogl-all-main:2.3.2")
    implementation("net.compartmental.code:minim:2.2.2")
}

application {
    // Define the main class for the application.
    mainClassName = "GuitarHeroKt"
}
