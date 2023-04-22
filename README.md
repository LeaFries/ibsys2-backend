# Anleitung
## Vorraussetzungen

- Java 17
- Gradle Version wird im Projekt gemanaged
- Docker Desktop

## Starten
1. Datenbank  
- Docker Desktop starten und hochfahren lassen  
- Im Terminal ins ***docker*** directory gehen und folgenden Command ausführen:

>docker compose up  

2. Anwendung
- Ins root directory der Anwendung gehen und folgenden Command ausführen:

>./gradlew bootRun

ACHTUNG!: Bitte keine lokal installierte Version von Gradle verwenden