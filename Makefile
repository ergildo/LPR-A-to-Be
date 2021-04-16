build-lpr-api-gateway-image:
	(cd ./lpr-api-gateway ; sh mvnw spring-boot:build-image -DskipTests)

build-lpr-service-discover-image:
	(cd ./lpr-service-discover ; sh mvnw spring-boot:build-image -DskipTests)

build-lpr-api-image:
	(cd ./lpr-api ; sh mvnw spring-boot:build-image -DskipTests)

build-lpr-events-producer-image:
	(cd ./lpr-events-producer ; sh mvnw spring-boot:build-image -DskipTests)

build-lpr-events-consumer-image:
	(cd ./lpr-events-consumer ; sh mvnw spring-boot:build-image -DskipTests)

build: build-lpr-api-gateway-image build-lpr-service-discover-image build-lpr-api-image build-lpr-events-producer-image build-lpr-events-consumer-image

clean-lpr-api-gateway:
	(cd ./lpr-api-gateway ; sh mvnw clean)

clean-lpr-service-discover:
	(cd ./lpr-service-discover ; sh mvnw clean)

clean-lpr-api:
	(cd ./lpr-api ; sh mvnw clean)

clean-lpr-events-producer:
	(cd ./lpr-events-producer ; sh mvnw clean)

clean-lpr-events-consumer:
	(cd ./lpr-events-consumer ; sh mvnw clean)

clean: clean-lpr-api-gateway clean-lpr-service-discover clean-lpr-events-producer clean-lpr-events-consumer clean-lpr-api

test-lpr-api-gateway:
	(cd ./lpr-api-gateway ; sh mvnw test)

test-lpr-service-discover:
	(cd ./lpr-service-discover ; sh mvnw test)

test-lpr-api:
	(cd ./lpr-api ; sh mvnw test)

test-lpr-events-producer:
	(cd ./lpr-events-producer ; sh mvnw test)
	
test-lpr-events-consumer:
	(cd ./lpr-events-consumer ; sh mvnw test)

test: test-lpr-api-gateway test-lpr-service-discover test-lpr-events-producer test-lpr-events-consumer test-lpr-api
	

start: 
	docker-compose up -d

stop:
	docker-compose down -v --remove-orphans

status:
	docker-compose ps

run: clean build start status
