FROM openjdk:17-oracle
WORKDIR /usr/src/app
COPY patients/build/libs/patients-0.0.1-SNAPSHOT.jar .
CMD ["java", "-jar", "patients-0.0.1-SNAPSHOT.jar"]