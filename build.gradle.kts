// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
    alias(libs.plugins.secrets) apply false
    kotlin("plugin.serialization") version "1.9.24"
    id("com.google.dagger.hilt.android") version "2.51.1" apply false
}