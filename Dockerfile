FROM openjdk:17-oracle
WORKDIR /usr/src/app
COPY patients/build/libs/patients-0.0.1-SNAPSHOT.jar .
COPY patients/src/main/resources/application.properties .
RUN sed -i 's/localhost/host.docker.internal/g' application.properties
CMD ["java", "-jar", "patients-0.0.1-SNAPSHOT.jar"]
EXPOSE 8000