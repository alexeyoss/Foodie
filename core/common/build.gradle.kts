import ru.alexeyoss.foodie.buildlogic.config.AppConfig

plugins {
    id("foodie.android.library")
}

android {
    namespace = "${AppConfig.APP_DOMAIN}.core.common"
}

dependencies {
    implementation(libs.fragment.ktx)
    implementation(libs.coroutine.core)
    implementation(libs.dagger)
    kapt(libs.dagger.compiler)
    implementation(libs.timber)
    implementation(libs.cicerone)
}
