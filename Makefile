setup:
	npm install
	./gradlew wrapper --gradle-version 8.7
	./gradlew build

frontend:
	make -C frontend start

app:
	./gradlew bootRun --args='--spring.profiles.active=dev'

app2:
	./gradlew bootRun --args='--spring.profiles.active=dev2'

clean:
	./gradlew clean

build:
	./gradlew clean build

dev:
	heroku local

reload-classes:
	./gradlew -t classes

start-prod:
	./gradlew bootRun --args='--spring.profiles.active=production'

install:
	./gradlew installDist

# start-dist:
# 	./build/install/app/bin/app

lint:
	./gradlew checkstyleMain checkstyleTest

test:
	./gradlew test

report:
	./gradlew jacocoTestReport

update-js-deps:
	npx ncu -u

check-java-deps:
	./gradlew dependencyUpdates -Drevision=release

# generate-migrations:
# 	gradle diffChangeLog

# db-migrate:
# 	./gradlew update


.PHONY: build frontend