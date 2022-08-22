import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.7.2"
    id("io.spring.dependency-management") version "1.0.12.RELEASE"
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
    kotlin("plugin.jpa") version "1.6.21"
}

group = "com.study"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

extra["springCloudVersion"] = "2021.0.2"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.springframework.cloud:spring-cloud-starter-config")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")

    // validation
    implementation("org.springframework.boot:spring-boot-starter-validation:2.7.2")

    // Security
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("io.jsonwebtoken:jjwt:0.9.1") // JWT
    compileOnly("javax.servlet:javax.servlet-api:4.0.1")

    // oauth2
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")

    // cache
    implementation("org.apache.commons:commons-pool2")
    implementation("org.springframework.boot:spring-boot-starter-cache:2.7.2")
    implementation("org.springframework.boot:spring-boot-starter-data-redis:2.7.2")

    // kotlin-logging
    implementation("io.github.microutils:kotlin-logging:2.1.23")

    // jackson
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.13.3")

    runtimeOnly("org.mariadb.jdbc:mariadb-java-client")

    // test
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude (group = "org.junit.vintage", module = "junit-vintage-engine")
        exclude (group = "org.mockito", module = "mockito-core")
    }
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.9.0")
    testImplementation("com.ninja-squad:springmockk:3.1.1")
    testImplementation("io.projectreactor:reactor-test")

    annotationProcessor ("org.springframework.boot:spring-boot-configuration-processor")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
