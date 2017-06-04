# TSP

## Build
```bash
mvn package
```

## Run with Doccker-Compose
```bash
docker-compose up
```

### Starting Order
1. TaskSink
2. Amount of TaskWork
3. TaskVent

## Run with Docker

```bash
docker create network tsp
```

### TaskSink
```bash
docker run --rm -it --name sink -h sink --network tsp vengima/tsp java -jar TSP-TaskSink.jar
```

### TaskVent
```bash
docker run --rm -it --name vent -h vent --network tsp -e HOST_SINK=sink -e FILE_PATH=coordinates.txt vengima/tsp java -jar TSP-TaskVent.jar
```

### TaskWork
Starte gewünschte Anzahl an Worker

```bash
for i in 1 2 3
do
   docker run --rm --network tsp -e HOST_SINK=sink -e HOST_MASTER=vent vengima/tsp java -jar TSP-TaskWork.jar
done
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

### TaskWork
Windows:
```bash
java -jar target\TSP-TaskWork.jar
```

Linux:
```bash
java -jar target/TSP-TaskWork.jar
```
