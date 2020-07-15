//import org.springframework.boot.gradle.tasks.bundling.BootWar

val springBootVersion = "2.2.6.RELEASE"
val springVersion = "5.2.5.RELEASE"
val h2Version = "1.4.200"
val postgresVersion = "9.4.1208-atlassian-hosted"
val hikariVersion = "3.4.2"
val validationApiVersion = "2.0.1.Final"
val log4jVersion = "2.13.3"
val persistenceApiVersion = "2.2"

plugins {
    id("org.springframework.boot") version "2.2.6.RELEASE"
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
	id("java")
    war
}

group = "org.pensatocode.loadtester"
version = "0.0.1"
java.sourceCompatibility = JavaVersion.VERSION_11

val developmentOnly by configurations.creating
configurations {
    runtimeClasspath {
        extendsFrom(developmentOnly)
    }
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
//    all {
//        exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
//    }
}


repositories {
    mavenCentral()
    jcenter()

    maven { url = uri("https://maven.atlassian.com/3rdparty/") }

    flatDir {
        dirs("libs")
    }
}

dependencies {
    // Spring Boot
    implementation("org.springframework.boot:spring-boot-starter-web:${springBootVersion}")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf:${springBootVersion}")
    providedRuntime("org.springframework.boot:spring-boot-starter-tomcat:${springBootVersion}")

    // Java extras
    implementation("javax.validation:validation-api:${validationApiVersion}")
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    // Spring Simplicity
    implementation(":spring-simplicity-java")
    implementation("org.springframework:spring-jdbc:${springVersion}")
    implementation("org.springframework.data:spring-data-commons:${springBootVersion}")
    implementation("javax.persistence:javax.persistence-api:${persistenceApiVersion}")

    // Database
    runtimeOnly("postgresql:postgresql:${postgresVersion}")
    implementation("com.zaxxer:HikariCP:${hikariVersion}")

    // Log4j2
//    implementation("org.apache.logging.log4j:log4j-api:${log4jVersion}")
//    implementation("org.apache.logging.log4j:log4j-core:${log4jVersion}")
//    implementation("org.apache.logging.log4j:log4j-slf4j-impl:${log4jVersion}")

    // Dev Tools
    developmentOnly("org.springframework.boot:spring-boot-devtools:${springBootVersion}")

    // Test
    testImplementation("org.springframework.boot:spring-boot-starter-test:${springBootVersion}") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    testImplementation("org.mockito:mockito-core:3.3.3")
    testImplementation("org.dbunit:dbunit:2.7.0")
    testImplementation("com.h2database:h2:1.4.200")
}

//tasks.getByName<BootWar>("bootWar") {
//    resources(sourceSets["main"])
//}

tasks.withType<Test> {
    useJUnitPlatform()
}
