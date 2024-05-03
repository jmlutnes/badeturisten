# Introduksjon
Badeturisten er et nettverksbasert mobilapp der primær målet er å vise badetemperatur på badesteder rundtom i Oslo-området. Appen lar bruekre se værmelding for oslo, samt informasjon for badesteder som er registrert i oslo kommune og favorittere ønskelige strender. Dette dokumentet skal definere arkitekturen i appen, interaksjon og teknologi som er brukt i utviklingen av appen.
# Komponentbeskrivelse
1. Brukerens Interaksjon
Beskrivelse: Brukeren interagerer med applikasjonens brukergrensesnitt gjennom en mobilapp. Dette inkluderer å klikke, scrolle, og andre former for input.
2. Skjermer med ViewModels
Beskrivelse: Skjermbaserte komponenter (screens) som kommuniserer direkte med ViewModels, og som fungerer som abstraksjonslag mellom UI og  presentasjonslogikken.
3. ViewModel
Beskrivelse: ViewModel-komponenter håndterer tilstandsstyringen for applikasjonen. De henter nødvendig data fra Repositories og oppdaterer UI-komponentene med tilsvarende informasjon.
4. Repositories
Beskrivelse: Repositories abstraherer tilgangen til datakilder, og tillater ViewModel å hente data uten å måtte kjenne til detaljene om datakildene. De sikrer en ren separasjon og kapsling av datahåndtering.
5. Datasources (API)
Beskrivelse: Datasources representerer de eksterne APIene som faktisk henter og lagrer data. Dette kan omfatte kommunikasjon med server-baserte APIer, databaser, filsystemer, etc.
# Dataflyt
# Sikkerhetsvurderinger
# Fremtidige Forbedringer
