build:
	./gradlew shadowJar

run: build
	java -jar app/build/libs/app-all.jar

docker-build-dev: build
	docker build -f docker/Dockerfile -t myapi-backend-java:dev .

docker-run-dev: docker-build-dev
	docker run -it --rm -p 4567:4567  myapi-backend-java:dev

docker-compose-dev: docker-build-dev
	docker-compose -f docker-compose-dev.yml up --build --remove-orphans

docker-build-prod: build
	docker build -f docker/Dockerfile -t myapi-backend-java:prod .

docker-run-prod: docker-build-prod
	docker run -it --rm -p 4567:4567  myapi-backend-java:prod

docker-compose-prod: docker-build-prod
	docker-compose -f docker-compose-prod.yml up --build --remove-orphans
