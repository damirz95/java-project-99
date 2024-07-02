FROM eclipse-temurin:21-jdk

ARG GRADLE_VERSION=8.7

RUN apt-get update && apt-get install -yq make unzip

WORKDIR .

COPY . .

RUN ./gradlew installDist

CMD make start-prod

EXPOSE 8080
