package plugin

import com.android.build.gradle.AppExtension
import com.android.build.gradle.BaseExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType

internal fun BaseExtension.configureBase(project: Project) {
    compileSdkVersion(AppConfig.compileSdkVersion)
    defaultConfig {
        minSdk = AppConfig.minSdkVersion
        targetSdk = AppConfig.targetSdkVersion
        ndk {
            abiFilters.add("armeabi")
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }

        getByName("debug") {
            isTestCoverageEnabled = true
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    project.tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
}

internal fun AppExtension.configureApplicationBase(project: Project){
    productFlavors {
        create("voice"){
            applicationId = AppConfig.applicationId
        }
        create("assistant"){
            applicationId = ModuleConfig.MODULE_ASSISTANT
        }
        create("developer"){
            applicationId = ModuleConfig.MODULE_DEVELOPER
        }
        create("joke"){
            applicationId = ModuleConfig.MODULE_JOKE
        }
        create("map"){
            applicationId = ModuleConfig.MODULE_MAP
        }
        create("setting"){
            applicationId = ModuleConfig.MODULE_SETTING
        }
        create("voiceSetting"){
            applicationId = ModuleConfig.MODULE_VOICE_SETTING
        }
        create("weather"){
            applicationId = ModuleConfig.MODULE_WEATHER
        }
    }

    productFlavors.all {
        manifestPlaceholders["app_name"] = name
    }
}