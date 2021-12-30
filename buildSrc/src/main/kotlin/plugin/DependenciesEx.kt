package plugin

import org.gradle.api.Project
import org.gradle.kotlin.dsl.project


internal fun Project.configureAppDeps() = dependencies.apply {
    add("implementation", (fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar")))))
    add("implementation", project(":lib_base"))

    //voice
    add("voiceImplementation",project(":binder_server"))
    add("voiceImplementation",project(":module_assistant"))
    add("voiceImplementation",project(":module_developer"))
    add("voiceImplementation",project(":module_joke"))
    add("voiceImplementation",project(":module_map"))
    add("voiceImplementation",project(":module_setting"))
    add("voiceImplementation",project(":module_voice_setting"))
    add("voiceImplementation",project(":module_weather"))

    //assistant
    add("assistantImplementation",project(":module_assistant"))

    //developer
    add("developerImplementation",project(":module_developer"))

    //joke
    add("jokeImplementation",project(":module_joke"))

    //map
    add("mapImplementation",project(":module_map"))

    //setting
    add("settingImplementation",project(":module_setting"))

    //voiceSetting
    add("voiceSettingImplementation",project(":module_voice_setting"))

    //weather
    add("weatherImplementation",project(":module_weather"))
}

internal fun Project.configureLibraryDeps()= dependencies.apply {
    add("api", (fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar")))))
    //Kotlin基础库
    add("api",DependenciesConfig.STD_LIB)
    //Android标准库
    add("api",DependenciesConfig.APP_COMPAT)
    //Kotlin核心库
    add("api",DependenciesConfig.KTX_CORE)
    //EventBus
    add("api",DependenciesConfig.EVENT_BUS)
    //ARouter
    add("api",DependenciesConfig.AROUTER)
    //AutoService
    add("api",DependenciesConfig.AUTOSERVICE)
    //MMKV
    add("api",DependenciesConfig.MMKV)
    //RecyclerView
    add("api",DependenciesConfig.RECYCLERVIEW)
    //AndPermissions
    add("api",DependenciesConfig.AND_PERMISSIONS)
    //ViewPager
    add("api",DependenciesConfig.VIEWPAGER)
    add("api",DependenciesConfig.MATERIAL)
    //Lottie
    add("api",DependenciesConfig.LOTTIE)
    //刷新
    add("api",DependenciesConfig.REFRESH_KERNEL)
    add("api",DependenciesConfig.REFRESH_HEADER)
    add("api",DependenciesConfig.REFRESH_FOOT)
    //图表
    add("api",DependenciesConfig.CHART)
    //屏幕适配
    add("api",DependenciesConfig.AUTO_SIZE)
    //状态栏
    add("api",DependenciesConfig.ACTION_BAR)
    //波浪
    add("api",DependenciesConfig.VOICE_LINE)
}