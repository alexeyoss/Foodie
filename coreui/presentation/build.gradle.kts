import ru.alexeyoss.foodie.buildlogic.config.AppConfig
import ru.alexeyoss.foodie.buildlogic.config.Modules

plugins {
    id("foodie.android.feature")
}

android {
    namespace = "${AppConfig.APP_DOMAIN}.core_ui.presentation"
}

dependencies {
    implementation(libs.androidx.ktx)
    implementation(libs.appCompat)
    implementation(libs.lifecycle.runtime)
    implementation(libs.material)

    api(project(Modules.core_ui.theme))
}
