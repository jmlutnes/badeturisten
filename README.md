# 🌟Badeturisme
Badeturisme er en applikasjon som tilbyr en detaljert oversikt om vær- og badeforhold, fasiliteter og tilgang til kollektiv transport for badesteder i Oslo-området! En applikasjon som passer perfekt for badeentusiaster i alle aldre som liker å bade i Oslo. 

I dette repositoriet finner du en oppskrift på hvordan man kan kjøre applikasjonen, informasjon om hvor dokumentasjon av prosjektet ligger og en oversikt over biblioteker som er brukt i prosjektet.

## 📸Skjermbilder av applikasjonen
<img width="984" alt="Screenshot 2024-05-14 at 08 48 45" src="https://media.github.uio.no/user/9646/files/a6ca5c21-a987-413a-b406-225aaa14e0cd">

## 🧑‍🍳Oppskrift
1. For å kunne få tilgang til applikasjonen må du først laste ned [latest version of Android Studio](https://developer.android.com/studio)
2. Klon dette repoet til din lokale maskin ved å bruke skrive inn denne kommandoen på terminalen din. \
 ```git clone + [url]``` 
3. Bygg og kjør prosjektet på en emulator eller en fysisk enhet som støtter API-nivåene som er henvist i rapporten under *brukerdokumentasjon*


## 📄Dokumentasjon 
[ARCHITECTURE.md](https://github.uio.no/IN2000-V24/team-37/blob/master/ARCHITECTURE.md) \
[MODELING.md](https://github.uio.no/IN2000-V24/team-37/blob/master/MODELING.md) 


## 📚Biblioteker 
### Google Play Services Location
- Formål: Gir lokasjonstjenester.
- Versjon: 21.2.0
- Dokumentasjon: [Play Services Location](https://developers.google.com/android/reference/com/google/android/gms/location/package-summary)

### Kotlinx Coroutines for Google Play Services
- Formål: Utvidelse av Kotlin Coroutines for å støtte Google Play Services-tjenester.
- Versjon: 1.7.3
- Dokumentasjon: [Kotlinx Coroutines](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-play-services)

### Android Data Transport Runtime
- Formål: Hjelper med datainnsamling og levering for tjenester.
- Versjon: 3.3.0
- Dokumentasjon: [Transport Runtime](https://github.com/google/dagger)

### AndroidX Room with KTX
- Formål: Forenkler databasearbeid med Kotlin flerspråkstillegg (KTX).
- Versjon: 2.6.1
- Dokumentasjon: [Room Database](https://developer.android.com/training/data-storage/room)

### Wear Compose Material
- Formål: Jetpack Compose-biblioteker spesifikt for Wear OS.
- Versjon: 1.3.1
- Dokumentasjon: [Wear Compose Material](https://developer.android.com/jetpack/compose)

### AndroidX Core KTX
- Formål: Forenkler bruken av sentrale Android API-er med Kotlin utvidelser.
- Versjon: 1.13.1 
- Dokumentasjon: [Core KTX](https://developer.android.com/kotlin/ktx#core)

### AndroidX Lifecycle Runtime KTX
- Formål: Tilbyr Kotlin spesifikke API-er for lifecycle runtime.
- Versjon: 2.7.0
- Dokumentasjon: [Lifecycle KTX](https://developer.android.com/kotlin/ktx#lifecycle-runtime)

### AndroidX Activity Compose
- Formål: Integrasjon av Jetpack Compose med Activity-biblioteker.
- Versjon: 1.8.2
- Dokumentasjon: [Activity Compose](https://developer.android.com/jetpack/compose/tooling#activity)

### AndroidX Compose UI
- Formål: Grunnleggende UI-terkomponenter for å bygge apps med Jetpack Compose.
- Dokumentasjon: [Compose UI](https://developer.android.com/jetpack/compose/setup#compose-compiler)

### AndroidX Compose UI Graphics
- Formål: Grafikkrelaterte API-er for Jetpack Compose.
- Dokumentasjon: [Compose Graphics](https://developer.android.com/jetpack/compose/graphics)

### AndroidX Compose UI Tooling Preview
- Formål: Forhåndsvisningsfunksjoner for UI-komponenter innenfor Android Studio.
- Dokumentasjon: [UI Tooling](https://developer.android.com/jetpack/compose/tooling)

### AndroidX Compose Material 3
- Formål: Material Design 3-komponenter for Jetpack Compose.
- Versjon: 1.2.1
- Dokumentasjon: [Material 3](https://developer.android.com/jetpack/androidx/releases/material3)

### AndroidX Lifecycle ViewModel Compose
- Formål: Integrasjon av ViewModel med Jetpack Compose.
- Versjon: 2.7.0
- Dokumentasjon: [ViewModel Compose](https://developer.android.com/jetpack/compose/state#viewmodel-state)

### JUnit
- Formål: Enhetstesting framework for Java og Android.
- Versjon: 4.13.2
- Dokumentasjon: [JUnit](https://junit.org/junit4/)

### AndroidX Test JUnit
- Formål: Android-spesifikk utvidelse av JUnit for integrasjonstester.
- Versjon: 1.1.5
- Dokumentasjon: [AndroidX Test](https://developer.android.com/training/testing/junit-rules)

### AndroidX Espresso Core
- Formål: UI-testing framework for Android.
- Versjon: 3.5.1
- Dokumentasjon: [Espresso](https://developer.android.com/training/testing/espresso)

### Jsoup
- Formål: Bibliotek for HTML parsing og manipulasjon.
- Versjon: 1.12.1
- Dokumentasjon: [Jsoup](https://jsoup.org/)

### Coil for Compose
- Formål: Image loading bibliotek designet for Jetpack Compose.
- Versjon: 2.6.0
- Dokumentasjon: [Coil Compose](https://coil-kt.github.io/coil/compose/)

### Ktor Client
- Formål: Asynkron nettverksklient for å lage HTTP-etterspørsler.
- Versjon: 2.3.10
- Dokumentasjon: [Ktor Client](https://ktor.io/docs/client.html)

### Ktor Serialization Gson
- Formål: Støtte for JSON serialisering gjennom ktor ved å bruke Gson.
- Versjon: 2.3.10 
- Dokumentasjon: [Ktor Gson](https://ktor.io/docs/gson.html)

### AndroidX Navigation Components for Compose
- Formål: Navigasjonshjelpemidler for apps bygget med Jetpack Compose.
- Versjon: 2.3.10
- Dokumentasjon: [Navigation Compose](https://developer.android.com/jetpack/compose/navigation)

### Lottie for Compose
- Formål: Bibliotek for å lage avanserte animasjoner med Adobe After Effects.
- Versjon: 6.4.0
- Dokumentasjon: [Lottie Compose][https://lottiefiles.com/lottie-for-android](https://lottiefiles.com/animations/fish-animation-w9ZWyXIvoI?from=search) 

### Dagger Hilt
- Formål: Dependency injection bibliotek.
- Versjon: 2.51.1
- Dokumentasjon: [Dagger Hilt](https://dagger.dev/hilt/)

### AndroidX Hilt Navigation Compose
- Formål: Integrerer Hilt DI med Navigation i Jetpack Compose.
- Versjon: 1.2.0
- Dokumentasjon: [Hilt Navigation Compose](https://developer.android.com/jetpack/androidx/releases/hilt)

### AndroidX ViewModel KTX
- Formål: Forenkler håndtering av UI-relaterte data ved å overleve konfigurasjonsendringer.
- Versjon: 2.7.0
- Dokumentasjon: [ViewModel KTX](https://developer.android.com/topic/libraries/architecture/viewmodel)

### DataStore Preferences Core
- Formål: Skrive og lese data asynkront, konsekvent og transaksjonelt.
- Versjon: 1.1.1
- Dokumentasjon: [DataStore Core](https://developer.android.com/topic/libraries/architecture/datastore)

### AndroidX DataStore Preferences
- Formål: Lagring av lettvektige preferansedata.
- Versjon: 1.1.1
- Dokumentasjon: [DataStore Preferences](https://developer.android.com/topic/libraries/architecture/datastore#preferences-datastore)

### Android UI Testing
- Formål: Testing bibliotek for Jetpack Compose.
- Versjon: 1.6.7
- Dokumentasjon: [Compose UI Testing](https://developer.android.com/jetpack/compose/testing)

### AndroidX UI Tooling
- Formål: Tilleggsverktøy for feilsøking og ytelsesanalyse av Compose UI.
- Dokumentasjon: [UI Tooling](https://developer.android.com/jetpack/compose/tooling#compose-tooling-library)

### Kotlinx Coroutines Test 
- Formål: Bibliotek som inneholder testingversktøy for Kotlin Coroutines.
- Versjon: 1.8.1-Beta
- Dokumentasjon: [Kotlinx Coroutines Test](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/)

### Android Test Manifest
- Formål: Hjelper med å inkludere manifestinformasjon for testing.
- Versjon: 3.5.1
- Dokumentasjon: [Test Manifest](https://developer.android.com/training/testing/set-up-project)

### Hilt Compiler
- Formål: Annotation Processor for Dagger Hilt.
- Versjon: 2.51.1
- Dokumentasjon: [Hilt Compiler](https://dagger.dev/hilt/compiler.html)


## Warnings fra Android Studio
Det er kun én IDE warning i prosjektet. I build.gradle.kts: "A newer version of androidx.activity:activity-compose than 1.8.2 is available: 1.9.0". Å oppdatere til nyeste versjon av dette biblioteket ødelegger deler av appens kode, og gjør at den ikke er kjørbar. Denne versjonen ble utgitt 2024-04-17, og vi oppdaget derfor ikke dette før svært nærme innleveringsfristen for prosjektet. Vi har derfor valgt å beholde versjon 1.8.2, som i per 2024-05-15 er nest nyeste stabile verson.


## 🧑‍💻Gruppemedlemmer 
Adam Nils Giæver (adamng) \
Bleron Strana (blerons) \
Danela Sabamali (danelas) \
Dan Thanh Ngu (dantn) \
Jöhan Markus Ludvigsen Utnes (jmutnes) \
Lan Anh Tran (lanatr)

