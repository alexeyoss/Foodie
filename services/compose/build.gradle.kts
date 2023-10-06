import ru.alexeyoss.foodie.buildlogic.config.AppConfig

plugins {
    id("foodie.android.feature.compose")
}

android {
    namespace = "${AppConfig.APP_DOMAIN}.services.compose"
}

dependencies {
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)
}
