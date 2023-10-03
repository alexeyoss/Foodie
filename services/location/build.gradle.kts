import ru.alexeyoss.foodie.buildlogic.config.AppConfig
import ru.alexeyoss.foodie.buildlogic.config.Modules

plugins {
    id("foodie.android.library")
}

android {
    namespace = "${AppConfig.APP_DOMAIN}.services.location"
}

dependencies {
    implementation(libs.coroutine.core)
    implementation(libs.dagger)
    implementation(libs.locationService)
    api(libs.locationService)
    kapt(libs.dagger.compiler)

    implementation(project(Modules.core.common))
}
