FROM gradle:jdk17 AS GRADLE_BUILD
COPY --chown=gradle:gradle . /home/benford/
WORKDIR /home/benford/
RUN gradle installBootDist --no-daemon

FROM azul/zulu-openjdk:17.0.0
EXPOSE 8080
COPY --from=GRADLE_BUILD /home/benford/build/libs/*.jar application.jar
RUN mkdir "/files/"
ENTRYPOINT ["java", "-jar", "application.jar"]