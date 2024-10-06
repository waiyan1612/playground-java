plugins {
    `kotlin-dsl`
}

// https://github.com/gradle/gradle/issues/17963#issuecomment-939207895
// https://discuss.gradle.org/t/applying-a-plugin-version-inside-a-convention-plugin/42160/2
fun Provider<PluginDependency>.text(): String {
    val dependency = get()
    return "${dependency.pluginId}:${dependency.pluginId}.gradle.plugin:${dependency.version}"
}

dependencies {
    implementation(buildSrcLibs.plugins.springBoot.text())
    implementation(buildSrcLibs.plugins.sonarqube.text())

    // Runtime libs for Flyway
    implementation(buildSrcLibs.plugins.flyway.text())
    implementation(buildSrcLibs.flyway.postgres)
    implementation(buildSrcLibs.postgres)
}

repositories {
    gradlePluginPortal()
    mavenCentral()
}
