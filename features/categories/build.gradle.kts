import ru.alexeyoss.foodie.buildlogic.config.AppConfig
import ru.alexeyoss.foodie.buildlogic.config.Modules

plugins {
    id("foodie.android.feature")
}

android {
    namespace = "${AppConfig.APP_DOMAIN}.features.categories"
}

dependencies {

    implementation(libs.androidx.ktx)
    implementation(libs.appCompat)
    implementation(libs.constraintLayout)
    implementation(libs.fragment.ktx)
    implementation(libs.recyclerView)
    implementation(libs.material)
    implementation(libs.shimmer)
    implementation(libs.dagger)
    kapt(libs.dagger.compiler)
    implementation(libs.glide)
    implementation(libs.timber)

    implementation(project(Modules.core.common))
    implementation(project(Modules.core_ui.presentation))
}
