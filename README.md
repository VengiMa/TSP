# TSP

## Build
```bash
mvn clean compile assembly:single
```

## Run

Windows:
```bash
set NUMBER_OF_CLUSTERS=4
set HOST_SINK=localhost
set FILE_PATH=src/main/ressources/coordinates.txt
java -jar target/TSP-0.1-SNAPSHOT-jar-with-dependencies.jar
```

Linux:
```bash
export NUMBER_OF_CLUSTERS=4
export HOST_SINK=localhost
export FILE_PATH=src/main/ressources/coordinates.txt
java -jar target/TSP-0.1-SNAPSHOT-jar-with-dependencies.jar
```
