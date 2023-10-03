import ru.alexeyoss.foodie.buildlogic.config.AppConfig

plugins {
    id("foodie.android.feature")
}

android {
    namespace = "${AppConfig.APP_DOMAIN}.services.compose"
}

dependencies {
    implementation(libs.lifecycle.runtime)
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)
}
