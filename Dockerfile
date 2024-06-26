FROM eclipse-temurin:21-jdk

ARG GRADLE_VERSION=8.7

RUN apt-get update && apt-get install -yq make unzip

WORKDIR .

COPY . .

RUN ./gradlew installDist

CMD ./build/install/app/bin/app

EXPOSE 8080

docker pull swaggerapi/swagger-ui
docker run -p 80:8080 swaggerapi/swagger-ui


