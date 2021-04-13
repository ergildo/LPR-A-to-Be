# Problem Description
In this challenge the purpose is to develop a License Plate Review (LPR) backend service for allowing authorized users to review and label poorly recognized photos with the correct license plate number. It should be also possible to monitor the review process, i.e. check a specific review or filter review processes accordingly the review status. The system should consume photos from a message broker of your choice. When testing you should publish photos in the respective topic and verify that it is correctly consumed.
# Solution Architecture
![solution architecture diagram](A-To-Be-LPR-Solution.png "Solution Architecture Diagram")

## 1. Api Gateway (Spring Cloud Api Gateway)
Provide externall communication to the publics endpoints. The clients will request to Api Gateway and it will redirect all the requests to the needed microservice.

## 2. Service Discover (Eureka)
Service where all microservice instances will register themselves and the client or API gateway will connect for identifying the location of the service instance and routing the request to it.

## 3. Message Broker (Kafka)
Message broker where the photos will be published.

## 4. Events Producer (Spring boot/Kafka)
Microservice where clients will connect to publish photos to the Message Broker

## 5. Events Consumer (Spring Boot/Kafka)
Microservice which will consume photos to the Message Broker. 

## 6. Rest API Service(Spring Boot)
Microservices which provides LPR´s APIs. 

## 7. Database (PostgreSQL)
Database where the datas will be stored.

# Dependencies
Java 11<br/>
Maven 3.6.3<br/>
Docker 20.10.5<br/>
Docker Compose 1.28.5<br/>
GNU Make 3.81<br/>

# Technologies
Java, Spring boot, JPA/Hibernate, JUnit<br/>
Kafka, Eureka, Spring Cloud Gateway, PostgreSQL<br/>
Elasticseach, Kibana, Logstash<br/>
Docker, Maven<br/>

# How to Run
``` 
make run 

```
It will build all spring boot applications and create a docker image and then uses the docker compose to run all needed services.

Make sure you have all the dependencies installed.

### Notes:
The makeFile was made to run on Mac OS / Linux. If you use Windows you might have some problems.

# How to use

I provided a file **requests/request-colletions** on project´s root path which is a postman´s request collections with all endpoint requests. Import it on Postman and test the application solution.

# Documentation
Unfortunatly, I did not configure the swagger documentation on the API Gateway. So, it means if want to access the endpoints documentation you have to do it individually for each microserves.

### Events producer
http://localhost:8081/swagger-ui.html

### Rest API Service
http://localhost:8080/swagger-ui.html

# Monitoring and Metrics

### Kibana

# Contacts
#### If you have any problems or questions, please contact me

**e-mail:** ergildo@gmail<br/>

**whatsapp:** +55 98488-4825<br/>
