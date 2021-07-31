FROM gradle:6.8.0-jdk8-hotspot AS build
WORKDIR /home/gradle/src

# Only copy dependency-related files
COPY --chown=gradle:gradle build.gradle settings.gradle /home/gradle/src/


# Only download dependencies
# Eat the expected build failure since no source code has been copied yet
RUN gradle clean build --no-daemon > /dev/null 2>&1 || true

COPY --chown=gradle:gradle ./ /home/gradle/src
RUN gradle clean build --no-daemon

FROM openjdk:8-jre-alpine

RUN mkdir /app

COPY --from=build /home/gradle/src/build/libs/*.jar /app/spring-boot-application.jar

ENTRYPOINT ["java","-jar","/app/spring-boot-application.jar"]