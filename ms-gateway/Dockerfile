FROM openjdk:11-jdk-slim-buster as builder

WORKDIR /app/ms-gateway

COPY ./pom.xml /app
COPY ./ms-gateway/.mvn ./.mvn
COPY ./ms-gateway/mvnw .
COPY ./ms-gateway/pom.xml .

#RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/
RUN ./mvnw dependency:go-offline
COPY ./ms-gateway/src ./src

RUN ./mvnw clean package -DskipTests

FROM openjdk:11-jdk-slim-buster

WORKDIR /app

RUN mkdir ./logs

COPY --from=builder /app/ms-gateway/target/ms-gateway-0.0.1-SNAPSHOT.jar .

EXPOSE 8090

CMD ["java", "-jar", "ms-gateway-0.0.1-SNAPSHOT.jar"]