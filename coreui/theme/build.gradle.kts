import ru.alexeyoss.foodie.buildlogic.config.AppConfig

plugins {
    id("foodie.android.library")
}

android {
    namespace = "${AppConfig.APP_DOMAIN}.core_ui.theme"
}

dependencies {
    implementation(libs.androidx.ktx)
    implementation(libs.appCompat)
    implementation(libs.material)
}
