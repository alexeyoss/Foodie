import ru.alexeyoss.foodie.buildlogic.config.AppConfig
import ru.alexeyoss.foodie.buildlogic.config.Modules

plugins {
    id("foodie.android.library")
}

android {
    namespace = "${AppConfig.APP_DOMAIN}.services.preference"
}

dependencies {
    implementation(libs.datastore)
    implementation(libs.timber)

    implementation(libs.dagger)
    kapt(libs.dagger.compiler)

    implementation(project(Modules.core.common))
}
