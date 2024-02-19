plugins {
    id("java")
}

group = "de.redsmiletv"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("com.github.twitch4j:twitch4j:1.19.0")
}

tasks.test {
    useJUnitPlatform()
}