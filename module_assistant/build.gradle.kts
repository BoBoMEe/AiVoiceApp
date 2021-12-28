plugins {
    id("com.android.library")
    kotlin("kapt")
}

android {
    resourcePrefix("module_assistant_")
    defaultConfig {
        kapt {
            arguments {
                arg("AROUTER_MODULE_NAME", project.name)
            }
        }
    }
}

dependencies {
    implementation(project(":lib_base"))
    implementation(project(":binder_server"))
    kapt(DependenciesConfig.AROUTER_COMPILER)
    kapt(DependenciesConfig.AUTOSERVICE_COMPILER)
}