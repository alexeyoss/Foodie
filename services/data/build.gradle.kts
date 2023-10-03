import ru.alexeyoss.foodie.buildlogic.config.AppConfig
import ru.alexeyoss.foodie.buildlogic.config.Modules

plugins {
    id("foodie.android.library")
}

android {
    namespace = "${AppConfig.APP_DOMAIN}.services.data"
}

dependencies {
    implementation(libs.dagger)
    kapt(libs.dagger.compiler)

    implementation(project(Modules.core.common))
    implementation(project(Modules.services.network))
}
