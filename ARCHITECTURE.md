I appen fordeler vi koden inn i data, dependency injection, domain, Model og UI pakker.
Data pakke:
Denne pakken omfatter datalaget til applikasjonen vår. Den håndterer datakilder(data source) og depoter(repositories). Vi har satt opp pakken slik at den er delt etter hvilket API den skal hente informajson fra. I hver underliggende pakke ligger det en datakile(datasource) og en depote(repository) implementasjon som håndterer informasjonen som den får fra den tilhørende datakilden(datasource).'

Dependency injection pakke:
Denne pakken håndterer avhengighetsinjeksjon(Dependency injection) ved bruk av Dagger Hilt. Modulklassene gir avhengigheter som brukes gjennom hele applikasjonen.
AppModule.kt: Gir generelle avhengigheter på et applikasjonsnivå.
NetworkModule.kt: håndterer avhengigheter med nettverkes tilkobling og datakilde(datasource) oppretting.
Qualifiers.kt:
ViewModelModule.kt:
RepositoryModule.kt: håndterer avhengigheter med depoter og sørger for at det finnes kun en instans av en depot.

Domain pakke:

Model pakke:

UI pakke:
