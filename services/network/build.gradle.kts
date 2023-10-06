import ru.alexeyoss.foodie.buildlogic.config.AppConfig
import ru.alexeyoss.foodie.buildlogic.config.Modules

plugins {
    id("foodie.android.library")
}

android {
    namespace = "${AppConfig.APP_DOMAIN}.services.network"

    defaultConfig {
        buildConfigField("String", "BASE_URL", "\"https://run.mocky.io/\"")
        buildConfigField("String", "API_VERSION", "\"v3/\"")
    }
}

dependencies {
    implementation(libs.retrofit)
    implementation(libs.gsonConverter)
    implementation(libs.okhttp)
    implementation(libs.loggingInterceptor)
    implementation(libs.dagger)
    kapt(libs.dagger.compiler)
    implementation(libs.timber)

    implementation( project(Modules.core.common))
}