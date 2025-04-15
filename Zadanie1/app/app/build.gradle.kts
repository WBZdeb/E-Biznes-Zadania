plugins {
    java
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.xerial:sqlite-jdbc:3.44.1.0")
}

application {
    mainClass.set("org.example.App")
}