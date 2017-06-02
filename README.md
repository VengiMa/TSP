# TSP

## Build
```bash
mvn clean compile assembly:single
```

## Run

### TaskVent
Windows:
```bash
set NUMBER_OF_CLUSTERS=4
set HOST_SINK=localhost
set FILE_PATH=src/main/ressources/coordinates.txt
java -jar target\TSP-TaskVent.jar
```

Linux:
```bash
export NUMBER_OF_CLUSTERS=4
export HOST_SINK=localhost
export FILE_PATH=src/main/ressources/coordinates.txt
java -jar target/TSP-TaskVent.jar
```

### TaskSink
Windows:
```bash
java -jar target\TSP-TaskSink.jar
```

Linux:
```bash
java -jar target/TSP-TaskSink.jar
```