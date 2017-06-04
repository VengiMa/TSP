# TSP

## Build
```bash
mvn package
```

## Run

###Starting Order
```bash
Starting in following order:
1. TaskSink
2. Amount of TaskWork
3. TaskVent
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

### TaskWork
Windows:
```bash
java -jar target\TSP-TaskWork.jar
```

Linux:
```bash
java -jar target/TSP-TaskWork.jar
```

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

