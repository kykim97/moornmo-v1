## How to run

- Run axon server and mysql firstly

```
cd infra
docker-compose up
```

## Build common API & Run each service

- Build common API
```
cd common-api
mvn clean install
```

- Run each service
```
# new terminal
cd basic
mvn clean spring-boot:run

# new terminal
cd sales
mvn clean spring-boot:run

```

- Run API gateway
```
cd gateway
mvn clean spring-boot:run
```

- Run frontend server
```
cd frontend
npm i
npm run serve

```

## Test By UI
Head to http://localhost:8088 with a web browser

## Test Rest APIs
- basic
```
 http :8088/employeeManagements id="id" userName="userName" userId="userId" department="department" position="position" phoneNo="phoneNo" enteringDate="enteringDate" isManager="isManager" 
```
- sales
```
 http :8088/orderManagements id="id" accountDate="accountDate" type="type" number="number" client="client" orderDate="orderDate" deliveryDate="deliveryDate" manager="manager" registerName="registerName" orderItem="orderItem" orderCount="orderCount" cost="cost" memo="memo" 
```

## Test RSocket APIs

- Download RSocket client
```
wget -O rsc.jar https://github.com/making/rsc/releases/download/0.4.2/rsc-0.4.2.jar
```
- Subscribe the stream
```
java -jar rsc.jar --stream  --route basics.all ws://localhost:8088/rsocket/basics

java -jar rsc.jar --stream  --route sales.all ws://localhost:8088/rsocket/sales

```
