plugins {
    java
    id("org.springframework.boot") version "3.2.5"
    id("io.spring.dependency-management") version "1.1.4"

}

group = "ru.rendaxx"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
//    runtimeOnly("com.jetbrains.intellij.java:java-gui-forms-rt:241.15989.178")
//    runtimeOnly("com.jetbrains.intellij.java:java-gui-forms-compiler:241.15989.178")
    implementation("org.apache.commons:commons-lang3:3.14.0")
    implementation("org.springframework.boot:spring-boot-starter-websocket")
    implementation("org.springframework.boot:spring-boot-starter-web")
    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    implementation("org.openjfx:javafx-swing:22.0.1")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation("com.google.code.gson:gson:2.11.0")

}

tasks.withType<Test> {
    useJUnitPlatform()
}
