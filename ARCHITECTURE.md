# Introduksjon
Badeturisten er et nettverksbasert mobilapp der primær målet er å vise badetemperatur på badesteder rundtom i Oslo-området. Appen lar bruekre se værmelding for oslo, samt informasjon for badesteder som er registrert i oslo kommune og favorittere ønskelige strender. Dette dokumentet skal definere arkitekturen i appen, interaksjon og teknologi som er brukt i utviklingen av appen.
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
6.  Datasources representerer de eksterne APIene som faktisk henter og lagrer data. Dette kan omfatte kommunikasjon med server-baserte APIer, databaser, filsystemer, etc.
# Dataflyt
Dataflyten i appen starter ved brukerforespørsler via UI, som går gjennom ViewModel til Repositories og til slutt til Datasources for å hente data fra våre utvalgte API. Data returneres via samme vei til brukergrensesnittet som oppdateres dynamisk.
# Sikkerhetsvurderinger
- Dataintegritet: Vi benytter HTTPS for å sikre integriteten og sikkerheten til brukerdata som overføres.
# Fremtidige Forbedringer
- Utvidelse til flere geografiske områder utenfor Oslo
- Forbedring av brukergrensesnittet basert på brukertilbakemeldinger.
- Implementere OAuth2.0 for sikker autentisering av brukere.
