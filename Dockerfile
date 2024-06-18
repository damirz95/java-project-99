FROM eclipse-temurin:21-jdk

ARG GRADLE_VERSION=8.7

RUN apt-get update && apt-get install -yq unzip

RUN wget -q https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip \
    && unzip gradle-${GRADLE_VERSION}-bin.zip \
    && rm gradle-${GRADLE_VERSION}-bin.zip

ENV GRADLE_HOME=/opt/gradle

RUN mv gradle-${GRADLE_VERSION} ${GRADLE_HOME}

ENV PATH=$PATH:$GRADLE_HOME/bin

WORKDIR /app

COPY ./ .

RUN gradle clean build

ENV JAVA_OPTS "-Xmx512M -Xms512M"
EXPOSE 8080

CMD java -jar build/libs/src-0.0.1-SNAPSHOT.jar