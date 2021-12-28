package plugin

import org.gradle.api.Project

internal fun Project.configureAppPlugins() {
    plugins.apply("kotlin-android")
    plugins.apply("kotlin-android-extensions")

}

internal fun Project.configureLibraryPlugins(){
    plugins.apply("kotlin-android")
    plugins.apply("kotlin-android-extensions")
}