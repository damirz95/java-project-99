FROM gradle:8.8.0-jdk21

WORKDIR /

COPY / .

RUN ./gradlew installDist

CMD ./gradlew bootRun --args='--spring.profiles.active=production'