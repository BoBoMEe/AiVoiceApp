// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        maven { setUrl("https://jitpack.io") }
        //阿里云
        maven { setUrl("https://maven.aliyun.com/repository/public") }
        maven { setUrl("https://maven.aliyun.com/repository/google") }
        maven { setUrl("https://maven.aliyun.com/repository/gradle-plugin") }
    }
    dependencies {
        classpath(KotlinConstants.GRADLE_CLASSPATH)
        classpath(KotlinConstants.KOTLIN_GRADLE_PLUGIN)

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        maven { setUrl("https://jitpack.io") }
        //阿里云
        maven { setUrl("https://maven.aliyun.com/repository/public") }
        maven { setUrl("https://maven.aliyun.com/repository/google") }
        maven { setUrl("https://maven.aliyun.com/repository/gradle-plugin") }
    }
}

plugins {
    id("com.android.plugin") apply false
}

subprojects {
    project.apply(plugin = "com.android.plugin")
}

tasks {
    val clean by registering(Delete::class) {
        delete(buildDir)
    }
}
