import java.net.URI

fun getLocalProperty(key: String, file: String = "local.properties"): Any? {
    val properties = java.util.Properties()
    val localProperties = File(file)
    if (localProperties.isFile) {
        java.io.InputStreamReader(java.io.FileInputStream(localProperties), Charsets.UTF_8).use { reader ->
            properties.load(reader)
        }
    } else {
        return null
    }

    return properties.getProperty(key)
}

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        mavenLocal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven {
            name = "GitHubPackages"
            url = URI("https://maven.pkg.github.com/${System.getenv("GITHUB_REPOSITORY")}")
            credentials {
                username = System.getenv("GITHUB_ACTOR") ?: getLocalProperty("gpr.usr").toString()
                password = System.getenv("GITHUB_TOKEN") ?: getLocalProperty("gpr.key").toString()// Github PAT
            }
        }
        google()
        mavenCentral()
        mavenLocal()
    }
}
rootProject.name = "Employeepedia"
include (":app")
