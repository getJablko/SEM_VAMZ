package com.example.sem_nova.Navigation

/**
 * Interface pre navigáciu v aplikácii, definuje cesty
 * Prevzatý kód z projektu dostupného na: https://github.com/google-developer-training/basic-android-kotlin-compose-training-inventory-app.git
 *
 */
interface NavigationDestination {
    /**
     * Unique path name
     */
    val route: String
}