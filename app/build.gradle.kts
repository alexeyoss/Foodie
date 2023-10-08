import ru.alexeyoss.foodie.buildlogic.config.Modules

plugins {
    id("foodie.android.application")
}

kapt {
    correctErrorTypes = true
}

dependencies {
    implementation(libs.androidx.ktx)
    implementation(libs.appCompat)
    implementation(libs.startup)
    implementation(libs.activity.ktx)
    implementation(libs.fragment.ktx)
    implementation(libs.constraintLayout)
    implementation(libs.lifecycle.viewModel)
    implementation(libs.timber)
    implementation(libs.cicerone)
    implementation(libs.circleImageView)
    implementation(libs.material)
    implementation(libs.dagger)
    kapt(libs.dagger.compiler)
//    debugImplementation(libs.leakCanary)

    implementation(project(Modules.core.common))
    implementation(project(Modules.core_ui.presentation))

    implementation(project(Modules.services.data))
    implementation(project(Modules.services.network))
    implementation(project(Modules.services.location))
    implementation(project(Modules.services.navigation))
    implementation(project(Modules.services.compose))

    implementation(project(Modules.features.cart))
    implementation(project(Modules.features.categories))
    implementation(project(Modules.features.dishes))
}
