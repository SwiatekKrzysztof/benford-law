FROM gradle:jdk17 AS GRADLE_BUILD
COPY --chown=gradle:gradle . /home/benford/src
WORKDIR /home/benford/src
RUN gradle installBootDist --no-daemon

FROM azul/zulu-openjdk:17.0.0
EXPOSE 8080
COPY --from=GRADLE_BUILD /home/benford/src/build/libs/*.jar application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]