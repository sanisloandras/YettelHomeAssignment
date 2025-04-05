# --------------------------------------------
# BASIC PROGUARD RULES FOR ANDROID APPS
# --------------------------------------------

# Keep class and members annotated with @Keep
-keep @androidx.annotation.Keep class * { *; }

# Keep Application class
-keep class com.szaniszlo.yettelhomeassignment.ui.core.YettelApplication { *; }

# Keep all native method names
-keepclasseswithmembernames class * {
    native <methods>;
}

# Prevent obfuscation of Kotlin metadata
-keep class kotlin.Metadata { *; }

# --------------------------------------------
# RETROFIT (if using)
# --------------------------------------------
# Keep Retrofit interfaces
-keep interface retrofit2.** { *; }

# Retrofit annotations (needed for requests to work)
-keepattributes RuntimeVisibleAnnotations, RuntimeInvisibleAnnotations

# Keep Retrofit models (if needed)
-keep class com.szaniszlo.yettelhomeassignment.data.dto.** { *; }
-keep class com.szaniszlo.yettelhomeassignment.domain.model.** { *; }

# GSON (if using Gson for serialization)
-keep class com.google.gson.** { *; }
-keep class * implements com.google.gson.TypeAdapter
-keep class * implements com.google.gson.JsonDeserializer
-keep class * implements com.google.gson.JsonSerializer

# --------------------------------------------
# JETPACK COMPOSE + NAVIGATION
# --------------------------------------------

# Compose UI and related internals
-keep class androidx.compose.** { *; }
-dontwarn androidx.compose.**

# Compose Navigation
-keep class androidx.navigation.** { *; }
-dontwarn androidx.navigation.**

# Keep ViewModels used in Compose
-keep class androidx.lifecycle.viewmodel.** { *; }
-dontwarn androidx.lifecycle.viewmodel.**

# Keep Composable functions with runtime reflection
-keepclassmembers class ** {
    @androidx.compose.runtime.Composable *;
}

# Compose Preview (optional)
-keep class androidx.compose.ui.tooling.preview.PreviewParameterProvider { *; }
-dontwarn androidx.compose.ui.tooling.**

# --------------------------------------------
# OKHTTP (if using)
# --------------------------------------------
-keep class okhttp3.** { *; }

# --------------------------------------------
# DAGGER HILT
# --------------------------------------------

# Keep Hilt components and inject targets
-keep class dagger.** { *; }
-dontwarn dagger.hilt.**
-keep class javax.inject.** { *; }
-dontwarn javax.inject.**

# Hilt generated components
-keep class * extends dagger.hilt.internal.GeneratedComponent { *; }

# Hilt internal
-keep class dagger.hilt.internal.** { *; }

# Needed to prevent crashes related to code generation
-keepnames class * {
    @dagger.* <methods>;
    @dagger.* <fields>;
}

# --------------------------------------------
# COROUTINES
# --------------------------------------------

-keep class kotlinx.coroutines.** { *; }
-dontwarn kotlinx.coroutines.**

# --------------------------------------------
# Misc Android
# --------------------------------------------
# Keep parcelables
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

# For logging (optional)
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
    public static *** w(...);
    public static *** e(...);
}