plugins {
    id("com.android.application")
    kotlin("kapt")
}

android {
    resourcePrefix("module_app_")
    defaultConfig{
        kapt {
            arguments {
                arg("AROUTER_MODULE_NAME", project.name)
            }
        }
    }
    //签名配置
    signingConfigs {
        register("release") {
            //别名
            keyAlias = "bobomee"
            //别名密码
            keyPassword = "123456"
            //路径
            storeFile = file("/src/main/jks/aivoice.jks")
            //密码
            storePassword = "123456"
        }
    }
    //编译类型
    buildTypes {
        getByName("release") {
            //自动签名打包
            signingConfig = signingConfigs.getByName("release")
        }
    }
}

//依赖
dependencies {
    kapt(DependenciesConfig.AROUTER_COMPILER)
    kapt(DependenciesConfig.AUTOSERVICE_COMPILER)
}
