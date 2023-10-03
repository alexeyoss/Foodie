import ru.alexeyoss.foodie.buildlogic.config.AppConfig
import ru.alexeyoss.foodie.buildlogic.config.Modules

plugins {
    id("foodie.android.library")
}

android {
    namespace = "${AppConfig.APP_DOMAIN}.services.navigation"
}

dependencies {
    implementation(libs.fragment.ktx)
    implementation(libs.timber)
    implementation(libs.cicerone)
    implementation(libs.dagger)
    kapt(libs.dagger.compiler)

    implementation(project(Modules.core.common))
}
