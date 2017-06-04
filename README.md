# TSP

## Build
```bash
mvn package
```

## Starting Order
1. TaskSink
2. Amount of TaskWork
3. TaskVent

## Run with Doccker-Compose
```bash
docker-compose up
```

## Run with Docker

```bash
NUMBER_OF_CLUSTERS=4
FILE_PATH=coordinates.txt

docker network create tsp

# TaskSink
docker run --rm -d --name sink -h sink --network tsp vengima/tsp java -jar TSP-TaskSink.jar

# TaskVent
docker run --rm -d --name vent -h vent --network tsp -e HOST_SINK=sink -e FILE_PATH=$FILE_PATH vengima/tsp /bin/sh -c 'sleep 10; java -jar TSP-TaskVent.jar'

# TaskWork
for i in $(seq 1 $NUMBER_OF_CLUSTERS) 
do
   docker run --rm -d --network tsp -e HOST_SINK=sink -e HOST_MASTER=vent vengima/tsp java -jar TSP-TaskWork.jar 
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
