plugins {
    id("java")
}

group = "ru.vk"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}
tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

dependencies {
    implementation("org.flywaydb:flyway-core:9.7.0")
    implementation("org.postgresql:postgresql:42.5.0")
    implementation("org.jetbrains:annotations:23.0.0")
    implementation("com.google.inject:guice:5.1.0")
    testImplementation("org.hamcrest:hamcrest-all:1.3")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}