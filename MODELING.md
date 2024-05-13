# Applikasjonens funksjonelle krav 
Applikasjonen vår tilbyr en rekke ulike funksjonaliteter for brukerne, der de viktigste er; visning av badestedsinformasjon, filtrert søk, visning av kollektivtransport og favorisering av badesteder. 


## Funksjonalitet: Visning av badestedsinformasjon 
Når brukeren trykker på et badested, presenteres de for nyttig informasjon om det aktuelle badestedet. Dersom dette er tilgjengelig, inneholder visningen informasjon om vannkvalitet, badetemperatur, fasiliteter og kollektivruter. 

### Tekstlig beskrivelse 
1. Brukeren får en oversikt over badesteder 
2. Brukeren velger et badested 
3. Systemet henter informasjon om badestedet fra API-er 
4. Brukeren får tilgjengelig informasjon om badestedet 

### Use Case-diagram 

<img width="757" alt="Skjermbilde 2024-05-13 kl  11 57 58" src="https://media.github.uio.no/user/9706/files/602f387d-7273-4ddc-b267-a262e3e90fbf">


## Funksjonalitet: Filtrert søk 
Applikasjonen tilbyr filtrering av søk slik at brukerne kan filtrere badestedene etter ulike kategorier/fasiliteter som de blir presentert for (hentet fra Oslo Kommune); badevakt, barnevennlig, grill, kiosk, tilpasset bevegelseshemmede, toalett, badebrygge. Dersom flere fasiliteter blir huket av, presenteres krysningen av resultatene fra hvert valgte fasilitetskategori. 

### Tekstlig beskrivelse 
1. Brukeren går inn til funksjonen gjennom Navigation-bar 
2. Systemet henter badestedene i Oslo fra API-er 
3. Brukeren får en oversikt over badesteder i Oslo og ulike filtreringskategorier (fasiliteter) 
4. Brukeren trykker på kategorier som badestedene filtreres etter 
5. Systemet viser oversikt over badestedene som kun går under disse kategoriene
6. Brukeren trykker på ett av de resulterende badestedene 
7. Systemet henter informasjon om badestedet fra API-er 
8. Brukeren får tilgjengelig informasjon om badestedet (vannkvalitet, badetemperatur, fasiliteter og kollektivruter) 


### Use Case-diagram

<img width="708" alt="Skjermbilde 2024-05-06 kl  15 49 32" src="https://media.github.uio.no/user/9706/files/4585cd92-c538-46ad-a768-0eb3feaa5165">

### Tilstandsdiagram

<img width="814" alt="Skjermbilde 2024-05-09 kl  22 46 10" src="https://media.github.uio.no/user/9706/files/21bb8908-7f37-44aa-b107-ae8e7eeaff44">

### Sekvensdiagram

<img width="1163" alt="Skjermbilde 2024-05-06 kl  16 00 15" src="https://media.github.uio.no/user/9706/files/6b1027ae-4a98-423d-8fca-18f17490250c">


## Funksjonalitet: Visning av kollektivtransport
Når brukeren trykker på et badekort, vises det et utvalg av kollektivtransport brukeren kan benytte seg av for å komme seg til badestedet.

### Tekstlig beskrivelse 
1. Brukeren får en oversikt over badesteder 
2. Brukeren trykker på et badested 
3. Systemet henter informasjon om badestedet fra API-er 
4. Brukeren får en oversikt over kollektivruter i nærheten av badestedet 

### Use Case-diagram

<img width="823" alt="Skjermbilde 2024-05-06 kl  15 51 03" src="https://media.github.uio.no/user/9706/files/5584640a-3e67-4199-a964-5075bca36123">


## Funksjonalitet: Legge til favoritter 
Applikasjonen gir brukeren muligheten til å favorisere badesteder. Disse badestedene blir lagt i en egen skjerm slik at det blir lettere for brukeren å finne fram til dem på et senere tidspunkt. Badestedene kan også enkelt fjernes fra favoritter dersom det er ønskelig.

### Tekstlig beskrivelse 
**Hovedflyt**
1. Brukeren velger et badested 
2. Systemet viser badestedsprofil 
3. Brukeren trykker på "favorittknappen" 
4. Systemet legger til badestedet i favoritter

**Alternativ flyt 1, steg 4:** Badestedet er allerede i favoritter \
A1.1. Systemet fjerner badestedet fra favoritter 

### Sekvensdiagram

<img width="457" alt="Skjermbilde 2024-05-09 kl  22 42 45" src="https://media.github.uio.no/user/9706/files/cc837fc2-7b97-44ce-8278-b2ee4965e5fc">

### Tilstandsdiagram

<img width="757" alt="Skjermbilde 2024-05-09 kl  22 44 25" src="https://media.github.uio.no/user/9706/files/42fda48c-d58b-45a0-8ccd-e305424c98cc">

## Funksjonalitet: Sortering av badesteder 
Applikasjonen vår har to ulike måter å sortere badestedene i hjemskjermen på; alfabetisk eller etter avstand. Hvis brukeren tillater stedstilgang, så blir badestedene sortert etter avstand, slik at brukerne lett kan finne ut om deres nærmeste badesteder tilfredsstiller deres ønsker. Hvis brukeren derimot avslår stedstilgang, blir badestedene sortert etter alfabetisk rekkefølge.

### Sekvensdiagram

<img width="581" alt="Skjermbilde 2024-05-06 kl  15 45 19" src="https://media.github.uio.no/user/9706/files/cc9ca785-14d4-4dec-b9de-ae67a60bdd60">


