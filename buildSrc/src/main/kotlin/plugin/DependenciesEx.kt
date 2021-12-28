package plugin

import org.gradle.api.Project


internal fun Project.configureAppDeps() = dependencies.apply {
    add("implementation", (fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar")))))

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