plugins {
	id("com.android.application")
	id("org.jetbrains.kotlin.android")
	id("androidx.navigation.safeargs")
	id("kotlin-kapt")
	id("kotlin-parcelize")
	id("com.google.gms.google-services")
	id("com.google.firebase.crashlytics")
}

android {
	namespace = "com.example.challenge_4_ilyasa_adam_naufal"
	compileSdk = 34

	defaultConfig {
		applicationId = "com.example.challenge_4_ilyasa_adam_naufal"
		minSdk = 24
		targetSdk = 34
		versionCode = 1
		versionName = "1.0"

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

		javaCompileOptions {
			annotationProcessorOptions {
				arguments["room.schemaLocation"] =
					"$projectDir/schemas"
			}
		}
	}

	buildTypes {
		release {
			isMinifyEnabled = false
			proguardFiles(
				getDefaultProguardFile("proguard-android-optimize.txt"),
				"proguard-rules.pro"
			)
		}
	}
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_1_8
		targetCompatibility = JavaVersion.VERSION_1_8
	}
	kotlinOptions {
		jvmTarget = "1.8"
	}

	buildFeatures {
		viewBinding = true
	}
}

dependencies {

	implementation("androidx.core:core-ktx:1.12.0")
	implementation("androidx.appcompat:appcompat:1.6.1")
	implementation("com.google.android.material:material:1.10.0")
	implementation("androidx.constraintlayout:constraintlayout:2.1.4")
	implementation("androidx.recyclerview:recyclerview:1.3.2")

	//Navigation
	implementation("androidx.navigation:navigation-fragment-ktx:2.7.5")
	implementation("androidx.navigation:navigation-ui-ktx:2.7.5")

	//ViewModel
	implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
	implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:2.6.2")

	//LiveData
	implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
	implementation("androidx.lifecycle:lifecycle-livedata-core-ktx:2.6.2")
	implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
	implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")

	// Coroutines
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

	//Database
	implementation("androidx.room:room-runtime:2.6.0")
	implementation("androidx.room:room-ktx:2.6.0")
	implementation("com.google.firebase:firebase-database-ktx:20.3.0")
	annotationProcessor("androidx.room:room-compiler:2.6.0")
	kapt("androidx.room:room-compiler:2.6.0")

	//Glide
	implementation("com.github.bumptech.glide:glide:4.16.0")

	//Retrofit
	implementation("com.squareup.retrofit2:retrofit:2.9.0")
	implementation("com.squareup.retrofit2:converter-gson:2.9.0")
	implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")

	//Firebase
	implementation(platform("com.google.firebase:firebase-bom:32.3.1"))
	implementation("com.google.firebase:firebase-analytics-ktx:21.5.0")
	implementation("com.google.firebase:firebase-crashlytics-ktx:18.5.1")
	implementation("com.google.firebase:firebase-auth-ktx:22.2.0")
	implementation("com.google.firebase:firebase-database-ktx:20.3.0")
	implementation("com.google.firebase:firebase-firestore:24.9.1")

	// Koin
	implementation("io.insert-koin:koin-core:3.5.0")
	implementation("io.insert-koin:koin-android:3.5.0")
	implementation("io.insert-koin:koin-android-compat:3.5.0")
	implementation("io.insert-koin:koin-androidx-workmanager:3.3.0")
	implementation("io.insert-koin:koin-androidx-navigation:3.3.0")

	testImplementation("junit:junit:4.13.2")
	androidTestImplementation("androidx.test.ext:junit:1.1.5")
	androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

}