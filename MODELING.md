# Applikasjonens funksjonelle krav 
Applikasjonen vår tilbyr en rekke ulike funksjonaliteter, der de viktigste er; visning av badestedsinformasjon, filtrert søk, visning av kollektivtransport, favorisering av badesteder og visning av nærmeste badesteder. 


## Funksjonalitet: Visning av badestedsinformasjon 
Når brukeren trykker på et badested, presenteres de for nyttig informasjon om det aktuelle badestedet. Dersom dette er tilgjengelig, inneholder visningen informasjon om vannkvalitet, badetemperatur, fasiliteter og kollektivruter. 

### Tekstlig beskrivelse 
1. Brukeren får en oversikt over badesteder 
2. Brukeren velger et badested 
3. Systemet henter informasjon om badestedet fra API-er 
4. Brukeren får tilgjengelig informasjon om badestedet (vannkvalitet, badetemperatur, fasiliteter og kollektivruter) 

### Use Case-diagram 

<img width="757" alt="Skjermbilde 2024-05-13 kl  11 57 58" src="https://media.github.uio.no/user/9706/files/602f387d-7273-4ddc-b267-a262e3e90fbf">

### Klassediagram

<img width="681" alt="Skjermbilde 2024-05-14 kl  22 34 38" src="https://media.github.uio.no/user/9706/files/82f1c89a-cb57-4c43-9907-f17df65398e8">


## Funksjonalitet: Filtrert søk 
Applikasjonen tilbyr filtrering av søk slik at brukerne kan filtrere badestedene etter ulike fasilitetskategorier (hentet fra Oslo Kommune) som de blir presentert for; badevakt, barnevennlig, grill, kiosk, tilpasset bevegelseshemmede, toalett og badebrygge. Dersom flere fasiliteter blir huket av, presenteres krysningen av resultatene fra hvert valgte fasilitetskategori. 

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

<img width="921" alt="Skjermbilde 2024-05-14 kl  22 36 31" src="https://media.github.uio.no/user/9706/files/fd63b7b1-0084-4cb3-9d4f-224ad691895b">

### Klassediagram 

<img width="444" alt="Skjermbilde 2024-05-14 kl  22 33 13" src="https://media.github.uio.no/user/9706/files/6126e663-e8f8-4e95-86d9-6007cd0ced59">


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
Applikasjonen gir brukeren muligheten til å favorisere badesteder, som gjøres ved å trykke på hjerteikonet inne på valgt badestedsprofil. Disse badestedene blir lagt inn i en egen skjerm slik at det blir lettere for brukeren å finne fram til dem på et senere tidspunkt. Badestedene kan også enkelt fjernes fra favoritter dersom det er ønskelig. Dette gjøres ved å trykke på hjerteikonet som allerede har blitt trykket på. 

### Tekstlig beskrivelse 
**Hovedflyt**
1. Brukeren velger et badested 
2. Systemet viser badestedsprofil 
3. Brukeren trykker på hjerteikonet 
4. Systemet legger til badestedet i favoritter

**Alternativ flyt 1, steg 4:** Badestedet er allerede i favoritter \
A1.1. Systemet fjerner badestedet fra favoritter 

### Sekvensdiagram

<img width="457" alt="Skjermbilde 2024-05-09 kl  22 42 45" src="https://media.github.uio.no/user/9706/files/cc837fc2-7b97-44ce-8278-b2ee4965e5fc">

### Tilstandsdiagram

<img width="757" alt="Skjermbilde 2024-05-09 kl  22 44 25" src="https://media.github.uio.no/user/9706/files/42fda48c-d58b-45a0-8ccd-e305424c98cc">

## Funksjonalitet: Visning av nærmeste badesteder 
Hvis brukeren tillater stedstilgang, blir badestedene sortert etter avstand. På denne måten kan brukerne lett finne fram til sine nærmeste badesteder uten å måtte scrolle langt. Hvis brukeren derimot avslår stedstilgang, blir badestedene sortert alfabetisk.

### Tekstlig beskrivelse 
**Hovedflyt**
1. Systemet ber om stedstilgang 
2. Brukeren godtar forespørselen 
3. Brukeren får en oversikt over badesteder sortert etter avstand 

**Alternativ flyt 1, steg 2:** Brukeren avslår forespørselen \
A1.1. Brukeren får en oversikt over badesteder i alfabetisk rekkefølge \
Avslutt flyt 

### Sekvensdiagram

<img width="540" alt="Skjermbilde 2024-05-14 kl  22 37 13" src="https://media.github.uio.no/user/9706/files/75497acf-21c3-4124-97fe-0a1d076a49b4">



