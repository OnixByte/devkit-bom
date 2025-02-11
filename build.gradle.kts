import java.net.URI

plugins {
    id("java-platform")
    id("maven-publish")
    id("signing")
}

val artefactVersion: String by project

group = "com.onixbyte"
version = artefactVersion

repositories {
    mavenCentral()
}

dependencies {
    constraints {
        api("com.onixbyte:devkit-core:$artefactVersion")
        api("com.onixbyte:devkit-utils:$artefactVersion")
        api("com.onixbyte:guid:$artefactVersion")
        api("com.onixbyte:key-pair-loader:$artefactVersion")
        api("com.onixbyte:map-util-unsafe:$artefactVersion")
        api("com.onixbyte:num4j:$artefactVersion")
        api("com.onixbyte:simple-jwt-facade:$artefactVersion")
        api("com.onixbyte:simple-jwt-authzero:$artefactVersion")
        api("com.onixbyte:simple-jwt-spring-boot-starter:$artefactVersion")
        api("com.onixbyte:property-guard-spring-boot-starter:$artefactVersion")
    }
}

publishing {
    publications {
        create<MavenPublication>("devkitBom") {
            groupId = group.toString()
            artifactId = "devkit-bom"
            version = artefactVersion

            pom {
                name = "DevKit BOM"
                description = "Using BOM could use unified OnixByte JDevKit."
                url = "https://github.com/OnixByte/devkit-bom"

                licenses {
                    license {
                        name = "The Apache License, Version 2.0"
                        url = "https://www.apache.org/licenses/LICENSE-2.0.txt"
                    }
                }

                scm {
                    connection = "scm:git:git://github.com:OnixByte/devkit-bom.git"
                    developerConnection = "scm:git:git://github.com:OnixByte/devkit-bom.git"
                    url = "https://github.com/OnixByte/devkit-bom"
                }

                developers {
                    developer {
                        id = "zihluwang"
                        name = "Zihlu Wang"
                        email = "zihlu.wang@onixbyte.com"
                        timezone = "Asia/Hong_Kong"
                    }
                }
            }

            print(components)

            from(components["javaPlatform"])

            signing {
                sign(publishing.publications["devkitBom"])
            }
        }

        repositories {
            maven {
                name = "sonatypeNexus"
                url = URI(providers.gradleProperty("repo.maven-central.host").get())
                credentials {
                    username = providers.gradleProperty("repo.maven-central.username").get()
                    password = providers.gradleProperty("repo.maven-central.password").get()
                }
            }
        }
    }
}