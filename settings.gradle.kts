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
}

rootProject.name = "rokrok"
include(":app:rokrok")
include(":common")
include(":core")
include(":core:common")
include(":core:ui")
include(":data")
include(":di")
include(":di:injectRepository")
include(":domain")
include(":domain:interfaceRepository")
include(":domain:model")
include(":domain:usecase")
include(":presentation")
include(":presentation:main")
include(":app:rokrok")
include(":test")
