# Introduksjon
Dette dokumentet beskriver den overordnede arkitekturen og designprinsippene for Badeturisten, en mobilapplikasjon utviklet for å vise vanntemperaturen og vannkvaliteten for badestrender rundtom i Oslo-området. Dokumentet er rettet mot utviklere, driftspersonell og andre tekniske interessenter som vil vedlikeholde og videreutvikle løsningen.
# Systemarkitektur
Høy-Nivå Oversikt
Badeturisten er designet med en klar separasjon av bekymringer gjennom en flerlagsarkitektur, som inkluderer:
- Presentasjonslag – Håndterer all brukerinteraksjon og presenterer data. Utviklet med Jetpack Compose.
- Forretningslogikklag – Inneholder all forretningslogikk og applikasjonsstatshåndtering. Dette laget bruker MVVM og UDF prinsipper for å sikre høy kohesjon og lav kobling.
- Data Access Layer (DAL) – Abstraherer tilgang til datakilder som nettverks APIer ved bruk av ktor og lokale databaser. Implementert ved bruk av Dagger Hilt i et Repository-mønstret for fleksibilitet og isolasjon. -- "lokale databser" endre hvis det ikke blir implementert datastore for favoritter.
# Arkitektoniske Mønstre og Prinsipper
- Model-View-ViewModel (MVVM): Brukes for å isolere forretningslogikken fra UI-koden. ViewModeller håndterer presentasjonslogikk og kommuniserer med modeller som representerer ekte data og forretningsoperasjoner.
- Unidirectional Data Flow (UDF): Garanterer at all tilstandsforvaltning er forutsigbar og enkel å følge, ved at data kun strømmer i en retning gjennom systemet.
# Teknologivalg
Frontend: Utviklet med Jetpack Compose for å maksimere gjenbruk av kode mellom plattformene og forbedre vedlikeholdbarheten.
Backend: Implementert med ktor(json to kotlin), og tilbyr APIer til mobilklientene.
# Komponentbeskrivelse
1. Brukerens Interaksjon:
Brukeren interagerer med applikasjonens brukergrensesnitt gjennom en mobilapp. Dette inkluderer å klikke, scrolle, og andre former for input.
2. Skjermer med ViewModels:
Skjermbaserte komponenter (screens) som kommuniserer direkte med ViewModels, og som fungerer som abstraksjonslag mellom UI og  presentasjonslogikken.
3. ViewModel:
ViewModel-komponenter håndterer tilstandsstyringen for applikasjonen. De henter nødvendig data fra Repositories og oppdaterer UI-komponentene med tilsvarende informasjon.
4. Repositories:
Repositories abstraherer tilgangen til datakilder, og tillater ViewModel å hente data uten å måtte kjenne til detaljene om datakildene. De sikrer en ren separasjon og kapsling av datahåndtering.
5. Datasources (API):
Datasources representerer de eksterne APIene som faktisk henter og lagrer data. Dette kan omfatte kommunikasjon med server-baserte APIer, databaser, filsystemer, etc.
# API-Nivå og Plattformvalg
Valget av API-nivå er en balanse mellom å støtte eldre enheter og å kunne utnytte nyere plattformfunksjoner som forbedrer ytelse, sikkerhet og brukeropplevelse.
- Minimum API-nivå: Appen krever Android API-nivå 25
- Anbefalt API-nivå: Android API-nivå 34 er anbefalt for å sikre optimal ytelse og for bruk av de nyeste plattformsfunksjonene.
# Sikkerhetsvurderinger
- Dataintegritet: Vi benytter HTTPS for å sikre integriteten og sikkerheten til brukerdata som overføres.
# Fremtidige Forbedringer
- Utvidelse til flere geografiske områder utenfor Oslo
- Forbedring av brukergrensesnittet basert på brukertilbakemeldinger.
- Funksjonalitet som kan utvides for å inkludere flere plattformer eller integrere med flere tjenester
- Implementere OAuth2.0 for sikker autentisering av brukere.
- Teknologiske oppdateringer, som inkluderer oppdatering til nyere API-nivåer og potensiell migrering til nyere teknologier.
