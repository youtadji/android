pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
    versionCatalogs {
        create("libs") {
            // Add this line
            version("nav-version", "2.7.7")
            plugin("navigation-safeargs", "androidx.navigation.safeargs.kotlin").versionRef("nav-version")
        }
    }
}

rootProject.name = "MyTOdo"
include(":app")
 