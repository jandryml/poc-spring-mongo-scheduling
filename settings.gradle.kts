//plugins {
//    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
//}
rootProject.name = "poc-spring-scheduler-mongo"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("./gradle/libraries.versions.toml"))
        }
    }
}
