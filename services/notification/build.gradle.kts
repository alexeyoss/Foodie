import ru.alexeyoss.foodie.buildlogic.config.AppConfig
import ru.alexeyoss.foodie.buildlogic.config.Modules

plugins {
    id("foodie.android.library")
}

android {
    namespace = "${AppConfig.APP_DOMAIN}.services.notification"
}

dependencies {
    implementation(libs.androidx.ktx)
    implementation(libs.dagger)
    kapt(libs.dagger.compiler)

    implementation(project(Modules.core.common))
    implementation(project(Modules.core_ui.presentation))
}
