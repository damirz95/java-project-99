FROM eclipse-temurin:21-jdk

ARG GRADLE_VERSION=8.7

RUN apt-get update && apt-get install -yq unzip

WORKDIR /app

COPY /app .

RUN gradle installDist

CMD ./build/install/app/bin/app